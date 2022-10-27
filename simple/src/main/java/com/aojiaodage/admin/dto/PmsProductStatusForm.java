package com.aojiaodage.admin.dto;

import com.aojiaodage.common.validator.interfaces.UpdateField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class PmsProductStatusForm {

    @NotEmpty(message = "ids不能为空", groups = {UpdateField.class})
    private List<Integer> ids;
}
