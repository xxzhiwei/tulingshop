package com.aojiaodage.portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AlipayForm {
    @NotBlank(message = "orderSn不能为空")
    private String orderSn;
}
