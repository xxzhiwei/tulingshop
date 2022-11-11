package com.aojiaodage.portal.dto;

import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.aojiaodage.common.validator.interfaces.UpdateField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class DeliveryForm {
    @NotNull(message = "id不能为空", groups = {Update.class, Del.class, UpdateField.class})
    private Integer id;

    @NotBlank(message = "name不能为空", groups = {Update.class, Save.class})
    private String name;

    @NotBlank(message = "phoneNumber不能为空", groups = {Update.class, Save.class})
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phoneNumber;

    private String postCode;
    private String province;
    private String city;
    private String region;
    private String detailAddress;
}
