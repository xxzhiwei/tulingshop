package com.aojiaodage.admin.controller;

import com.aojiaodage.admin.annotation.Required;
import com.aojiaodage.admin.dto.LoginForm;
import com.aojiaodage.admin.dto.RefreshForm;
import com.aojiaodage.admin.dto.UserForm;
import com.aojiaodage.admin.entity.User;
import com.aojiaodage.admin.service.UserService;
import com.aojiaodage.common.dto.Query;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.util.R;
import com.aojiaodage.common.validator.interfaces.Del;
import com.aojiaodage.common.validator.interfaces.Save;
import com.aojiaodage.common.validator.interfaces.Update;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<?> login(@Validated @RequestBody LoginForm loginForm) {
        TokenDetail<User> tokenDetail = userService.login(loginForm);
        return R.ok(tokenDetail);
    }

    @PostMapping("/logout")
    public R<?> logout() {
        userService.logout();
        return R.ok();
    }

    @PostMapping("/refresh")
    public R<?> refresh(@Validated @RequestBody RefreshForm refreshForm) {
        TokenDetail<User> tokenDetail = userService.refresh(refreshForm);
        return R.ok(tokenDetail);
    }

    @GetMapping("/pagination")
    public R<?> getList(Query query) {
        Page<User> pagination = userService.getPagination(query);
        return R.ok(pagination);
    }

    @Required("user:update")
    @PostMapping("/update")
    public R<?> update(@Validated(value = Update.class) @RequestBody UserForm form) {
        userService.update(form);
        return R.ok();
    }

    @Required("user:save")
    @PostMapping("/save")
    public R<?> save(@Validated(value = Save.class) @RequestBody UserForm form) {
        userService.save(form);
        return R.ok();
    }

    @Required("user:del")
    @PostMapping("/del")
    public R<?> del(@Validated(value = Del.class) @RequestBody UserForm form) {
        userService.del(form);
        return R.ok();
    }
}
