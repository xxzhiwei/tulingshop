package com.aojiaodage.admin.dto;

import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AttrGroupForm {
    @NotNull(message = "id不能为空", groups = {Del.class, Update.class})
    private Integer id;
    @NotNull(message = "id不能为空", groups = {Save.class, Update.class})
    private String name;
}
