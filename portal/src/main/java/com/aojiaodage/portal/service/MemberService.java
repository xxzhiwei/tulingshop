package com.aojiaodage.portal.service;

import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.portal.dto.LoginForm;
import com.aojiaodage.portal.dto.RefreshForm;
import com.aojiaodage.portal.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MemberService extends IService<Member> {
    TokenDetail<Member> login(LoginForm loginForm);
    void logout();
    TokenDetail<Member> refresh(RefreshForm refreshForm);
}
