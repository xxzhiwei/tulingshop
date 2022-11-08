package com.aojiaodage.portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshForm {
    @NotBlank(message = "refreshToken不能为空")
    private String refreshToken;
}
