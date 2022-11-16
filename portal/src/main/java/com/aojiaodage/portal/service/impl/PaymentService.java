package com.aojiaodage.portal.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.portal.config.AlipayConfig;
import com.aojiaodage.portal.dto.AlipayForm;
import com.aojiaodage.portal.dto.AlipayNotifyForm;
import com.aojiaodage.portal.entity.Bill;
import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.service.BillService;
import com.aojiaodage.portal.service.OmsOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    OmsOrderService orderService;

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    private static final String timeoutExpress = "60m";

    @Autowired
    BillService billService;

    // 生成支付宝页面
    public String generateAlipay(AlipayForm form) {
        String orderSn = form.getOrderSn();

        LambdaQueryWrapper<OmsOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(OmsOrder::getOrderSn, orderSn);

        OmsOrder order = orderService.getOne(orderLambdaQueryWrapper);

        if (order == null) {
            throw new CustomException("订单不存在");
        }
        if (order.getPayType() != 0) {
            throw new CustomException("此订单已经支付");
        }

        try {
            // 获得初始化的AlipayClient
            AlipayClient alipayClient = new DefaultAlipayClient(
                    AlipayConfig.gatewayUrl,
                    AlipayConfig.appId,
                    AlipayConfig.merchantPrivateKey,
                    "json",
                    AlipayConfig.charset,
                    AlipayConfig.alipayPublicKey,
                    AlipayConfig.signType
            );

            // 设置请求参数
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            alipayRequest.setReturnUrl(AlipayConfig.returnUrl);
            alipayRequest.setNotifyUrl(AlipayConfig.notifyUrl);

            // 商户订单号，商户网站订单系统中唯一订单号，必填
            // 付款金额，必填
            String totalAmount = order.getPayAmount().toString();
            // 订单名称，必填
            String subject = "tulingshop订单";

            alipayRequest.setBizContent("{\"out_trade_no\":\"" + orderSn + "\","
                    + "\"total_amount\":\"" + totalAmount + "\","
                    + "\"subject\":\"" + subject + "\","
                    + "\"timeout_express\":\"" + timeoutExpress + "\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            // 若想给BizContent增加其他可选请求参数，可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
            AlipayTradePagePayResponse resp = alipayClient.pageExecute(alipayRequest);
            return resp.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new CustomException("请求支付失败");
        }
    }

    /**
     * 有几种特殊情况要处理
     * 1、假设支付过后，刚好订单过期【涉及到库存解锁问题，再极端的情况是，订单因过期解锁库存后，刚好被下一个用户购买，而本来库存就见底了，这种情况下，有可能出问题】
     * 2、假设订单过期后，再支付【timeout_express可处理】
     * @param parameterMap 支付宝通知参数
     * @return String
     */
    @Transactional
    public String handleAlipayNotify(Map<String, String[]> parameterMap) {

        log.info("开始处理支付宝通知----");
        Set<String> keySet = parameterMap.keySet();

        Map<String, String> map = new HashMap<>();

        for (String key : keySet) {
            String[] values = parameterMap.get(key);
            map.put(key, String.join(",", values));
        }

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(map, AlipayConfig.alipayPublicKey, AlipayConfig.charset, AlipayConfig.signType); // 调用SDK验证签名
            if (!signVerified) {
                return FAIL;
            }

            Date date = new Date();
            AlipayNotifyForm form = AlipayNotifyForm.convert(map);
            String outTradeNo = form.getOutTradeNo(); // 实际上是当前系统的orderSn
            String tradeStatus = form.getTradeStatus();
            // 表示支付成功/完成【其他状态不会通知，但还是加个条件判断】
            if (tradeStatus.equals("TRADE_SUCCESS") || tradeStatus.equals("TRADE_FINISHED")) {
                Bill bill = new Bill();
                bill.setTradeNo(form.getTradeNo());
                bill.setCallbackTime(form.getNotifyTime());
                bill.setOrderSn(outTradeNo);
                bill.setPaymentStatus(tradeStatus);
                bill.setTotalAmount(form.getTotalAmount());
                bill.setSubject(form.getSubject());
                bill.setCreateTime(date);

                // 保存交易账单
                billService.save(bill);

                LambdaQueryWrapper<OmsOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
                orderLambdaQueryWrapper.eq(OmsOrder::getOrderSn, outTradeNo);
                OmsOrder order = orderService.getOne(orderLambdaQueryWrapper);

                // 检查订单是否为关闭状态（情况1）
                if (order.getStatus().equals(4)) {
                    orderService.updateStockByOrderSn(outTradeNo, ((OmsOrderServiceImpl) orderService).stockHandler);
                }
                else if (order.getStatus().equals(0)) {
                    orderService.updateStockByOrderSn(outTradeNo, ((OmsOrderServiceImpl) orderService).stockLockedHandler);
                }

                // 更新订单状态
                order.setStatus(1);
                order.setPayType(1);
                order.setPaymentTime(date);
                order.setPayAmount(form.getTotalAmount());

                orderService.updateById(order);
            }
            log.info("处理完成：orderSn：" + outTradeNo + "，tradeStatus：" + tradeStatus);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理支付宝通知回调失败，" + e.getMessage());
            return FAIL;
        }

        return SUCCESS;
    }
}
