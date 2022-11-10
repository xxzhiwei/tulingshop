package com.aojiaodage.portal.vo;

import com.aojiaodage.portal.entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderConfirmation {
    private BigDecimal amount; // 应付
    private Integer count; // 商品件数
    private BigDecimal total; // 总价
    private List<CartItem> items;
}
