package com.aojiaodage.portal.entity;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public BigDecimal getAmount() {
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
        int count = 0;

        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                count += item.getCount();
            }
        }
        return count;
    }
}
