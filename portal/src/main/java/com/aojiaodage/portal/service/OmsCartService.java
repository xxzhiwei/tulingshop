package com.aojiaodage.portal.service;

import com.aojiaodage.portal.entity.Cart;

public interface OmsCartService {
    // 保存商品
    void save(Integer skuId, Integer count);
    // 获取购物车
    Cart get();
    // 删除商品
    void del(Integer skuId);
    // 清空购物车
    void clear();
    // 修改数量
    void updateCount(Integer skuId, Integer count);
}
