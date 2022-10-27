package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.LoginForm;
import com.aojiaodage.admin.dto.RefreshForm;
import com.aojiaodage.admin.dto.UserForm;
import com.aojiaodage.admin.entity.User;
import com.aojiaodage.common.dto.Query;
import com.aojiaodage.common.pojo.TokenDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface UserService {
    TokenDetail<User> login(LoginForm loginForm);
    void logout();
    // 刷新token
    TokenDetail<User> refresh(RefreshForm refreshForm);

    Page<User> getPagination(Query query);

    void update(UserForm form);

    void save(UserForm form);

    void del(UserForm form);
}
