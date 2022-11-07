package com.aojiaodage.portal.entity;

import java.math.BigDecimal;
import java.util.List;

public class CartItem {
    private Integer skuId;
    private String title;
    private BigDecimal price;
    private Integer count;

    private List<String> attrs; // 用于前端展示

    public BigDecimal getAmount() {
        return price.multiply(new BigDecimal(count));
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<String> attrs) {
        this.attrs = attrs;
    }
}
