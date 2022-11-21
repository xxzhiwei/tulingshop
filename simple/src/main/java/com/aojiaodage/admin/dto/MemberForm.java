package com.aojiaodage.admin.dto;

import com.aojiaodage.common.validator.interfaces.Update;
import com.aojiaodage.common.validator.interfaces.UpdateField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MemberForm {
    @NotNull(message = "id不能为空", groups = {Update.class, UpdateField.class})
    private Integer id;

    @NotNull(message = "status不能为空", groups = UpdateField.class)
    @Size(message = "status只能是0,1", max = 1)
    private Integer status;
}
