package com.aojiaodage.portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class ConfirmForm {
    @NotEmpty(message = "skuIds不能为空")
    private List<Integer> skuIds;
}
