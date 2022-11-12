package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.portal.dao.OmsOrderDao;
import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.DeliveryForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.entity.*;
import com.aojiaodage.portal.service.OmsCartService;
import com.aojiaodage.portal.service.OmsOrderItemService;
import com.aojiaodage.portal.service.OmsOrderService;
import com.aojiaodage.portal.service.PmsProductSkuService;
import com.aojiaodage.portal.util.PayloadUtil;
import com.aojiaodage.portal.vo.OrderConfirmation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderDao, OmsOrder> implements OmsOrderService {

    @Autowired
    PmsProductSkuService pmsProductSkuService;

    @Autowired
    OmsOrderItemService orderItemService;

    @Autowired
    OmsCartService cartService;

    @Override
    public OrderConfirmation confirm(ConfirmForm form) {
        List<Integer> skuIds = form.getSkuIds(); // 假设id不重复，应该要保证skuIds.size()等于查询出来的记录条数

        OrderConfirmation orderConfirmation = new OrderConfirmation();
        Cart cart = cartService.get();
        // 选择支付的商品
        List<CartItem> cartItems = cart.getItems()
                .stream()
                .filter(item -> skuIds.contains(item.getSkuId()))
                .collect(Collectors.toList());
        orderConfirmation.setItems(cartItems);

        BigDecimal amount = Cart.getAmount(cartItems);
        Integer count = Cart.getCount(cartItems);

        // 目前而已，总价和支付价格相同
        orderConfirmation.setTotal(amount);
        orderConfirmation.setAmount(amount);
        orderConfirmation.setCount(count);
        return orderConfirmation;
    }

    @Transactional
    @Override
    public void create(OrderForm form) {
        OmsOrder order = new OmsOrder();
        List<Integer> skuIds = form.getSkuIds();

        // 先检查skuId是否是包含在用户的购物车中
        Cart cart = cartService.get();
        List<CartItem> checked = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (skuIds.contains(item.getSkuId())) {
                checked.add(item);
            }
            else {
                throw new CustomException("非法的数据；skuId：" + item.getSkuId() + "不包含在购物车中");
            }
        }

        // 再检查skuId是否都存在数据库中
        List<ProductSku> skus = pmsProductSkuService.listByIds(skuIds);
        if (skus.size() != skuIds.size()) {
            throw new CustomException("skuId与数据库不同步");
        }

        // 检查库存
        checked.sort((Comparator.comparingInt(CartItem::getSkuId)));
        skus.sort((Comparator.comparingInt(ProductSku::getId)));

        for (int i=0; i<skus.size(); i++) {
            ProductSku sku = skus.get(i);
            CartItem cartItem = checked.get(i);

            if (sku.getStock() < cartItem.getCount()) {
                throw new CustomException(sku.getName() + "库存不足，剩余量", 20000); // 此时，前端应该根据错误提示用户后，重新获取sku库存
            }
        }

        Member member = PayloadUtil.get();
        order.setMemberId(member.getId());
        order.setMemberUsername(member.getUsername());

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(date);
        order.setOrderSn(dateStr + System.currentTimeMillis());
        order.setCreateTime(date);
        order.setPayType(0); // 未选择支付方式
        order.setSourceType(0);
        order.setStatus(0); // 未支付状态
        order.setOrderType(0);

        // 订单金额
        BigDecimal amount = Cart.getSkuAmount(skus);
        order.setTotalAmount(amount);
        order.setPayAmount(amount);
        order.setFreightAmount(new BigDecimal("10"));

        // 收货人信息
        DeliveryForm delivery = form.getDelivery();
        order.setReceiverName(delivery.getName());
        order.setReceiverPhone(delivery.getPhoneNumber());
        order.setReceiverProvince(delivery.getProvince());
        order.setReceiverCity(delivery.getCity());
        order.setReceiverRegion(delivery.getRegion());
        order.setReceiverPostCode(delivery.getPostCode());
        order.setReceiverDetailAddress(delivery.getDetailAddress());

        save(order);

        List<OmsOrderItem> orderItems = new ArrayList<>();

        for (int i=0; i<skus.size(); i++) {
            ProductSku sku = skus.get(i);
            CartItem cartItem = checked.get(i);
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
            orderItem.setProductId(sku.getSpuId());
            orderItem.setProductName(sku.getName());
            orderItem.setProductPrice(sku.getPrice());
            orderItem.setProductSkuId(sku.getId());
            orderItem.setProductQuantity(cartItem.getCount());

            orderItems.add(orderItem);
        }

        cartService.remove(skuIds);
        orderItemService.saveBatch(orderItems);
    }

    @Override
    public OmsOrder getDetail(Integer id) {
        OmsOrder order = getById(id);

        if (order == null) {
            throw new CustomException("数据不存在");
        }
        LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderItemLambdaQueryWrapper.eq(OmsOrderItem::getOrderId, id);
        List<OmsOrderItem> orderItems = orderItemService.list(orderItemLambdaQueryWrapper);
        order.setItems(orderItems);
        return order;
    }
}
