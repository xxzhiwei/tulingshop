package com.aojiaodage.portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderForm {
    @NotNull(message = "skuIds不能为空")
    @NotEmpty(message = "skuIds不能为空")
    List<Integer> skuIds;
    @NotNull(message = "delivery不能为空")
    DeliveryForm delivery; // 配送信息
}
