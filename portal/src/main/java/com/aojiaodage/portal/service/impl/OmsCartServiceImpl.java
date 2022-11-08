package com.aojiaodage.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.aojiaodage.portal.dto.CartItemForm;
import com.aojiaodage.portal.entity.Cart;
import com.aojiaodage.portal.entity.CartItem;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.entity.ProductSku;
import com.aojiaodage.portal.service.OmsCartService;
import com.aojiaodage.portal.service.PmsProductSkuService;
import com.aojiaodage.portal.util.PayloadUtil;
import com.aojiaodage.portal.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OmsCartServiceImpl implements OmsCartService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    PmsProductSkuService pmsProductSkuService;

    private BoundHashOperations<String, String, String> getCartHash() {
        Member member = PayloadUtil.get();
        String cartKey = RedisKeyUtil.getCartKey(member.getId());
        return redisTemplate.boundHashOps(cartKey);
    }

    @Override
    public Cart get() {
        Cart cart = new Cart();
        BoundHashOperations<String, String, String> cartHash = getCartHash();
        List<String> values = cartHash.values();

        if (values != null && values.size() > 0) {
            List<CartItem> cartItems = values
                    .stream()
                    .map(item -> JSON.parseObject(item, CartItem.class))
                    .collect(Collectors.toList());
            cart.setItems(cartItems);
        }
        return cart;
    }

    @Override
    public void clear() {
        Member member = PayloadUtil.get();
        String cartKey = RedisKeyUtil.getCartKey(member.getId());
        redisTemplate.delete(cartKey);
    }

    @Override
    public void add(CartItemForm form) {
        Integer skuId = form.getSkuId();
        Integer count = form.getCount();

        ProductSku sku = pmsProductSkuService.getDetail(skuId);
        BoundHashOperations<String, String, String> cartHash = getCartHash();
        String json = null;
        CartItem cartItem;
        // 已存在购物车的商品
        if ((json = cartHash.get(skuId.toString())) != null) {
            cartItem = JSON.parseObject(json, CartItem.class);
            cartItem.setCount(cartItem.getCount() + count);
        }
        else {
            cartItem = new CartItem();
            cartItem.setSkuId(skuId);
            cartItem.setCount(count);
            cartItem.setTitle(sku.getName());
            cartItem.setPrice(sku.getPrice());
        }

        // 更新库存
        cartItem.setStock(sku.getStock());
        cartHash.put(skuId.toString(), JSON.toJSONString(cartItem));
    }

    @Override
    public void remove(CartItemForm form) {
        Integer skuId = form.getSkuId();
        BoundHashOperations<String, String, String> cartHash = getCartHash();
        cartHash.delete(skuId);
    }

    @Override
    public void updateCount(CartItemForm form) {

        Integer skuId = form.getSkuId();
        Integer count = form.getCount();
        BoundHashOperations<String, String, String> cartHash = getCartHash();
        String json = cartHash.get(skuId);

        CartItem cartItem = JSON.parseObject(json, CartItem.class);

        assert cartItem != null;
        cartItem.setCount(count);

        cartHash.put(skuId.toString(), JSON.toJSONString(cartItem));
    }
}
