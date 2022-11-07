package com.aojiaodage.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @Override
    public void save(Integer skuId, Integer count) {
        ProductSku sku = pmsProductSkuService.getDetail(skuId);
        CartItem cartItem = new CartItem();
        cartItem.setSkuId(skuId);
        cartItem.setCount(count);
        cartItem.setTitle(sku.getName());
        cartItem.setPrice(sku.getPrice());

        BoundHashOperations<String, Object, Object> cartHash = getCartHash();

        cartHash.put(skuId, JSON.toJSONString(cartItem));
    }

    private BoundHashOperations<String, Object, Object> getCartHash() {
        Member member = PayloadUtil.get();
        String cartKey = RedisKeyUtil.getCartKey(member.getId());
        return redisTemplate.boundHashOps(cartKey);
    }

    @Override
    public Cart get() {
        Cart cart = new Cart();
        BoundHashOperations<String, Object, Object> cartHash = getCartHash();
        List<Object> values = cartHash.values();

        if (values != null && values.size() > 0) {
            List<CartItem> cartItems = values
                    .stream()
                    .map(item -> JSON.parseObject((String) item, CartItem.class))
                    .collect(Collectors.toList());
            cart.setItems(cartItems);
        }
        return cart;
    }

    @Override
    public void del(Integer skuId) {
        BoundHashOperations<String, Object, Object> cartHash = getCartHash();
        cartHash.delete(skuId);
    }

    @Override
    public void clear() {
        Member member = PayloadUtil.get();
        String cartKey = RedisKeyUtil.getCartKey(member.getId());
        redisTemplate.delete(cartKey);
    }

    @Override
    public void updateCount(Integer skuId, Integer count) {

        BoundHashOperations<String, Object, Object> cartHash = getCartHash();
        String json = (String) cartHash.get(skuId);

        CartItem cartItem = JSON.parseObject(json, CartItem.class);

        assert cartItem != null;
        cartItem.setCount(count);

        cartHash.put(skuId, JSON.toJSONString(cartItem));
    }
}
