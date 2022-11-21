package com.aojiaodage.portal.service.impl;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.exception.TokenExpired;
import com.aojiaodage.common.exception.WrongUsernameOrPwdException;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.util.Md5Util;
import com.aojiaodage.portal.dao.MemberDao;
import com.aojiaodage.portal.dto.LoginForm;
import com.aojiaodage.portal.dto.RefreshForm;
import com.aojiaodage.portal.dto.RegisterForm;
import com.aojiaodage.portal.entity.Member;
import com.aojiaodage.portal.service.MemberService;
import com.aojiaodage.portal.util.PayloadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Autowired
    PortalTokenService tokenService;

    @Override
    public TokenDetail<Member> login(LoginForm form) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getUsername, form.getUsername());
        wrapper.eq(Member::getPassword, Md5Util.encode(form.getPassword()));

        Member member = getOne(wrapper);

        // 找不到用户时，返回与密码错误时相同的提示信息
        if (member == null) {
            throw new WrongUsernameOrPwdException();
        }

        if (member.getStatus() == 0) {
            throw new CustomException("该账号已经被禁用，请联系客服询问原因");
        }

        member.setPassword(null);
        return tokenService.generate(member);
    }

    @Override
    public void logout() {
        Integer id = PayloadUtil.get().getId();
        long r = tokenService.clear(id.toString());
        if (r != 1) {
            throw new CustomException("登出失败");
        }
    }

    @Override
    public TokenDetail<Member> refresh(RefreshForm form) {
        try {
            return tokenService.refresh(form.getRefreshToken());
        }
        catch (TokenExpired exception) {
            throw exception;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new TokenExpired();
        }
    }

    @Override
    public void register(RegisterForm form) {
        String username = form.getUsername();
        String phone = form.getPhone();

        LambdaQueryWrapper<Member> memberLambdaQueryWrapper = new LambdaQueryWrapper<>();
        memberLambdaQueryWrapper.eq(Member::getUsername, username)
                .or()
                .eq(Member::getPhone, phone);
        Member existed = getOne(memberLambdaQueryWrapper);

        if (existed != null) {
            if (username.equals(existed.getUsername())) {
                throw new CustomException("用户名已经存在");
            }
            if (phone.equals(existed.getPhone())) {
                throw new CustomException("手机号已经存在");
            }
        }

        form.setPassword(Md5Util.encode(form.getPassword()));
        Member member = new Member();

        BeanUtils.copyProperties(form, member);
        if (!StringUtils.hasText(member.getNickname())) {
            member.setNickname(member.getUsername());
        }
        member.setStatus(1);
        member.setCreateTime(new Date());
        member.setSourceType(1);
        member.setMemberLevelId(1);

        save(member);
    }
}
