package com.aojiaodage.admin.dto;

import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class RoleForm {
    @NotNull(groups = {Update.class, Del.class})
    private Integer id;

    @NotBlank(groups = {Update.class, Save.class})
    private String name;
    private String description;

    private List<Integer> removing;
    private List<Integer> assigning;
}
