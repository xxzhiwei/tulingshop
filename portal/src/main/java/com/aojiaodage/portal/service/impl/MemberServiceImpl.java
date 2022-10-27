package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.TokenExpired;
import com.aojiaodage.common.exception.WrongUsernameOrPwdException;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.util.Md5Util;
import com.aojiaodage.portal.dao.MemberDao;
import com.aojiaodage.portal.dto.LoginForm;
import com.aojiaodage.portal.dto.RefreshForm;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Autowired
    PortalTokenService tokenService;

    @Override
    public TokenDetail<Member> login(LoginForm loginForm) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getUsername, loginForm.getUsername());
        wrapper.eq(Member::getPassword, Md5Util.encode(loginForm.getPassword()));

        Member member = getOne(wrapper);

        // 找不到用户时，返回与密码错误时相同的提示信息
        if (member == null) {
            throw new WrongUsernameOrPwdException();
        }

        return tokenService.generate(member);
    }

    @Override
    public void logout() {

    }

    @Override
    public TokenDetail<Member> refresh(RefreshForm refreshForm) {
        try {
            return tokenService.refresh(refreshForm.getRefreshToken());
        }
        catch (TokenExpired exception) {
            throw exception;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new TokenExpired();
        }
    }
}
