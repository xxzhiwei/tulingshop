package com.aojiaodage.portal.entity;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public BigDecimal getAmount() {
        return getAmount(items);
    }

    public static BigDecimal getAmount(List<CartItem> items) {
        BigDecimal amount = new BigDecimal("0");

        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                amount = amount.add(item.getAmount());
            }
        }
        return amount;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCount() {
        return getCount(items);
    }
    public static Integer getCount(List<CartItem> items) {
        int count = 0;

        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                count += item.getCount();
            }
        }
        return count;
    }
}
