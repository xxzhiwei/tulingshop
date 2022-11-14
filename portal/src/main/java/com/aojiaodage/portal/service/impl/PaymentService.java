package com.aojiaodage.portal.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.portal.config.AlipayConfig;
import com.aojiaodage.portal.dto.AlipayForm;
import com.aojiaodage.portal.entity.OmsOrder;
import com.aojiaodage.portal.service.OmsOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    OmsOrderService orderService;

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
            // 商品描述，可空
            String body = "谢谢";

            alipayRequest.setBizContent("{\"out_trade_no\":\""+ orderSn +"\","
                    + "\"total_amount\":\""+ totalAmount +"\","
                    + "\"subject\":\""+ subject +"\","
                    + "\"body\":\""+ body +"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            // 若想给BizContent增加其他可选请求参数，可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节
            AlipayTradePagePayResponse resp = alipayClient.pageExecute(alipayRequest);
            return resp.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new CustomException("请求支付失败");
        }
    }
}
