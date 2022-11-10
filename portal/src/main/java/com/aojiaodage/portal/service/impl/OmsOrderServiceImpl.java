package com.aojiaodage.portal.service.impl;

import com.aojiaodage.portal.dao.OmsOrderDao;
import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.entity.*;
import com.aojiaodage.portal.service.OmsCartService;
import com.aojiaodage.portal.service.OmsOrderService;
import com.aojiaodage.portal.service.PmsProductSkuService;
import com.aojiaodage.portal.util.PayloadUtil;
import com.aojiaodage.portal.vo.OrderConfirmation;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderDao, OmsOrder> implements OmsOrderService {

    @Autowired
    PmsProductSkuService pmsProductSkuService;

    @Autowired
    OmsCartService omsCartService;

    @Override
    public OrderConfirmation confirm(ConfirmForm form) {
        List<Integer> skuIds = form.getSkuIds(); // 假设id不重复，应该要保证skuIds.size()等于查询出来的记录条数

        OrderConfirmation orderConfirmation = new OrderConfirmation();
        Cart cart = omsCartService.get();
        // 选择支付的商品
        List<CartItem> cartItems = cart.getItems()
                .stream()
                .filter(item -> skuIds.contains(item.getSkuId()))
                .collect(Collectors.toList());
        orderConfirmation.setItems(cartItems);
        calculate(orderConfirmation);
        return orderConfirmation;
    }

    @Override
    public void create(OrderForm form) {
        OmsOrder order = new OmsOrder();
        Member member = PayloadUtil.get();
        order.setMemberId(member.getId());

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(date);
        order.setOrderSn(dateStr + System.currentTimeMillis());
        order.setCreateTime(date);
        order.setMemberUsername(member.getUsername());
        order.setPayType(0);
        order.setSourceType(0);
        order.setStatus(0); // 未支付状态
        order.setOrderType(0);
    }

    private void calculate(OrderConfirmation orderConfirmation) {
        BigDecimal total = new BigDecimal("0"); // 目前total和amount是一样的
        int count = 0;
        for (CartItem cartItem : orderConfirmation.getItems()) {
            total = total.add(cartItem.getAmount());
            count += cartItem.getCount();
        }
        orderConfirmation.setTotal(total);
        orderConfirmation.setAmount(total);
        orderConfirmation.setCount(count);
    }
}
