package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.PaginationUtil;
import com.aojiaodage.portal.dao.OmsOrderDao;
import com.aojiaodage.portal.dto.ConfirmForm;
import com.aojiaodage.portal.dto.DeliveryForm;
import com.aojiaodage.portal.dto.OrderForm;
import com.aojiaodage.portal.dto.OrderQuery;
import com.aojiaodage.portal.entity.*;
import com.aojiaodage.portal.interfaces.StockHandler;
import com.aojiaodage.portal.service.OmsCartService;
import com.aojiaodage.portal.service.OmsOrderItemService;
import com.aojiaodage.portal.service.OmsOrderService;
import com.aojiaodage.portal.service.PmsProductSkuService;
import com.aojiaodage.portal.util.PayloadUtil;
import com.aojiaodage.portal.vo.OrderConfirmation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    @Autowired
    PmsProductSkuService skuService;

    // 减库存【适用于「支付后，订单过期」的情况】
    StockHandler stockHandler = (sku, orderItem) -> {
        if (sku.getStock() < orderItem.getProductQuantity()) {
            throw new CustomException("库存不足，skuId：" + sku.getId());
        }

        sku.setStock(sku.getStock() - orderItem.getProductQuantity());
    };

    // 减锁定库存【正常支付情况
    StockHandler stockLockedHandler = (sku, orderItem) -> sku.setStockLocked(sku.getStockLocked() - orderItem.getProductQuantity());

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
    public String create(OrderForm form) {
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
            throw new CustomException("sku与数据库不同步");
        }

        // 检查库存
        checked.sort(Comparator.comparingInt(CartItem::getSkuId));
        skus.sort(Comparator.comparingInt(ProductSku::getId));

        for (int i=0; i<skus.size(); i++) {
            ProductSku sku = skus.get(i);
            CartItem cartItem = checked.get(i);
            // 同步价格
            if (!cartItem.getPrice().equals(sku.getPrice())) {
                cartItem.setPrice(sku.getPrice());
            }

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
        order.setModifyTime(date);
        order.setPayType(0); // 未选择支付方式
        order.setSourceType(0);
        order.setStatus(0); // 未支付状态
        order.setOrderType(0);

        // 订单金额
        BigDecimal amount = Cart.getAmount(checked);
        order.setTotalAmount(amount);
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

            // 锁定库存
            sku.setStockLocked(sku.getStockLocked() + orderItem.getProductQuantity());
            sku.setStock(sku.getStock() - orderItem.getProductQuantity());

            orderItems.add(orderItem);
        }

        cartService.remove(skuIds);
        orderItemService.saveBatch(orderItems);
        skuService.updateBatchById(skus);
        return order.getOrderSn();
    }

    @Override
    public OmsOrder getByOrderSn(String orderSn, boolean includingItems) {
        LambdaQueryWrapper<OmsOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(OmsOrder::getOrderSn, orderSn);
        OmsOrder order = getOne(orderLambdaQueryWrapper);

        if (order == null) {
            throw new CustomException("数据不存在");
        }

        if (includingItems) {
            LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderItemLambdaQueryWrapper.eq(OmsOrderItem::getOrderId, order.getId());
            List<OmsOrderItem> orderItems = orderItemService.list(orderItemLambdaQueryWrapper);
            order.setItems(orderItems);
        }
        return order;
    }

    @Override
    public OmsOrder getByOrderSn(String orderSn) {
        return getByOrderSn(orderSn, true);
    }

    @Transactional
    @Override
    public List<OmsOrder> cancelByTask() {
        List<OmsOrder> orders = baseMapper.selectOutOfDate();
        if (orders.size() > 0) {
            List<Integer> orderIds = new ArrayList<>();
            for (OmsOrder order : orders) {
                order.setStatus(4);
                order.setModifyTime(new Date());
                orderIds.add(order.getId());
            }

            // 更新订单状态
            updateBatchById(orders);

            // 归还库存
            LambdaQueryWrapper<OmsOrderItem> orderItemLambdaQueryWrapper = new LambdaQueryWrapper<>();
            orderItemLambdaQueryWrapper.in(OmsOrderItem::getOrderId, orderIds);
            List<OmsOrderItem> orderItems = orderItemService.list(orderItemLambdaQueryWrapper);

            List<Integer> skuIds = orderItems.stream().map(OmsOrderItem::getProductSkuId).collect(Collectors.toList());
            List<ProductSku> skus = skuService.listByIds(skuIds);

            // 正常情况下，skus与orderItems一一对应
            skus.sort(Comparator.comparingInt(ProductSku::getId));
            orderItems.sort(Comparator.comparingInt(OmsOrderItem::getProductSkuId));

            for (int i=0; i<skus.size(); i++) {
                ProductSku sku = skus.get(i);
                OmsOrderItem orderItem = orderItems.get(i);
                sku.setStock(sku.getStock() + orderItem.getProductQuantity());
                sku.setStockLocked(sku.getStockLocked() - orderItem.getProductQuantity());
            }

            skuService.updateBatchById(skus);
        }
        return orders;
    }

    @Override
    public Page<OmsOrder> getPagination(OrderQuery query) {
        PaginationUtil.setPaginationIfNecessary(query);

        Integer memberId = PayloadUtil.get().getId();
        long current = query.getCurrent();
        long size = query.getSize();
        long count = baseMapper.selectCount(memberId, query);
        Page<OmsOrder> page = new Page<>(current, size, count);

        if (count > 0) {
            Long offset = PaginationUtil.getPaginationOffset(current, size);
            List<OmsOrder> orders = baseMapper.selectPagination(offset, size, memberId, query);
            page.setRecords(orders);
        }
        return page;
    }

    @Override
    public void updateStockByOrderSn(String orderSn, StockHandler handler) {
        OmsOrder order = getByOrderSn(orderSn);

        List<OmsOrderItem> items = order.getItems();
        List<Integer> skuIds = items.stream().map(OmsOrderItem::getProductSkuId).collect(Collectors.toList());
        List<ProductSku> skus = skuService.listByIds(skuIds);

        items.sort(Comparator.comparingInt(OmsOrderItem::getProductSkuId));
        skus.sort(Comparator.comparingInt(ProductSku::getId));

        for (int i=0; i<skus.size(); i++) {
            ProductSku sku = skus.get(i);
            OmsOrderItem orderItem = items.get(i);
            handler.handle(sku, orderItem);
        }

        skuService.updateBatchById(skus);
    }
}
