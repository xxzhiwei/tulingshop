package com.aojiaodage.admin.dto;

import com.aojiaodage.common.dto.Query;
import com.aojiaodage.common.validator.interfaces.Get;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AttrQuery extends Query {
    private Integer type2;
    @NotNull(message = "categoryId不能为空", groups = {Get.class})
    private Integer categoryId;
}
