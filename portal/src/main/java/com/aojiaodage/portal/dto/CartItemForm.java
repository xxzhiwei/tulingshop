package com.aojiaodage.portal.dto;

import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemForm {
    @NotNull(message = "skuId不能为空", groups = {Save.class, Update.class, Del.class})
    private Integer skuId;

    @NotNull(message = "count不能为空", groups = {Save.class, Update.class})
    private Integer count;
}
