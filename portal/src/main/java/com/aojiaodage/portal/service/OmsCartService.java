package com.aojiaodage.portal.service;

import com.aojiaodage.portal.dto.CartItemForm;
import com.aojiaodage.portal.entity.Cart;

import java.util.List;

public interface OmsCartService {
    // 获取购物车
    Cart get();
    // 清空购物车
    void clear();
    // 将商品加入购物车
    void add(CartItemForm form);

    // 从购物车中移除商品
    void remove(CartItemForm form);

    // 修改购物车商品数量
    void updateCount(CartItemForm form);

    void remove(List<Integer> skuIds);
}
