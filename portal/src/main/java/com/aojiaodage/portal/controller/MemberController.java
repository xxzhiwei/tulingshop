package com.aojiaodage.portal.controller;

import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.LoginForm;
import com.aojiaodage.portal.dto.RefreshForm;
import com.aojiaodage.portal.dto.RegisterForm;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/login")
    public R<?> login(@Validated @RequestBody LoginForm form) {
        TokenDetail<Member> tokenDetail = memberService.login(form);
        return R.ok(tokenDetail);
    }

    @PostMapping("/logout")
    public R<?> logout() {
        memberService.logout();
        return R.ok();
    }

    @PostMapping("/refresh")
    public R<?> refresh(@Validated @RequestBody RefreshForm form) {
        TokenDetail<Member> tokenDetail = memberService.refresh(form);
        return R.ok(tokenDetail);
    }

    @PostMapping("/register")
    public R<?> register(@Validated @RequestBody RegisterForm form) {
        memberService.register(form);
        return R.ok();
    }
}
