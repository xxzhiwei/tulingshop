package com.aojiaodage.portal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class RegisterForm {
    @NotBlank(message = "username不能为空")
    private String username;
    @NotBlank(message = "password不能为空")
    private String password;
    private String nickname;
    @NotBlank(message = "phone不能为空")
    private String phone;
    private String avatar;
    private Integer gender;
    private Date birthday;
}
