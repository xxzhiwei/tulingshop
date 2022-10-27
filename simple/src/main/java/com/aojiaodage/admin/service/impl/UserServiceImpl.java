package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.UserDao;
import com.aojiaodage.admin.dto.LoginForm;
import com.aojiaodage.admin.dto.RefreshForm;
import com.aojiaodage.admin.dto.UserForm;
import com.aojiaodage.admin.entity.User;
import com.aojiaodage.admin.entity.UserRole;
import com.aojiaodage.admin.service.PermissionService;
import com.aojiaodage.admin.service.UserRoleService;
import com.aojiaodage.admin.service.UserService;
import com.aojiaodage.admin.util.PayloadUtil;
import com.aojiaodage.common.dto.Query;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.exception.TokenExpired;
import com.aojiaodage.common.exception.WrongUsernameOrPwdException;
import com.aojiaodage.common.pojo.TokenDetail;
import com.aojiaodage.common.util.Md5Util;
import com.aojiaodage.common.util.PaginationUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    AdminTokenService tokenService;

    @Override
    public TokenDetail<User> login(LoginForm loginForm) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginForm.getUsername());
        wrapper.eq(User::getPassword, Md5Util.encode(loginForm.getPassword()));

        User user = getOne(wrapper);

        // 找不到用户时，返回与密码错误时相同的提示信息
        if (user == null) {
            throw new WrongUsernameOrPwdException();
        }

        if (user.getState().equals(2)) {
            throw new CustomException("账户已被禁用");
        }

        return tokenService.generate(user);
    }

    @Override
    public void logout() {
        Integer id = PayloadUtil.get().getId();
        tokenService.clear(id.toString());
    }

    @Override
    public TokenDetail<User> refresh(RefreshForm refreshForm) {
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

    @Override
    public Page<User> getPagination(Query query) {
        Page<User> page = PaginationUtil.getPage(query);
        page(page);
        return page;
    }

    @Transactional
    @Override
    public void update(UserForm form) {
        Integer id = form.getId();

        User user = getById(id);

        if (user == null) {
            throw new CustomException("数据不存在");
        }
        user.setAvatar(form.getAvatar());
        user.setEmail(form.getEmail());
        user.setNickname(form.getNickname());
        updateById(user);

        List<Integer> assigning = form.getAssigning();
        List<Integer> removing = form.getRemoving();

        // 新增角色
        if (assigning != null && assigning.size() > 0) {
            List<UserRole> userRoles = assigning
                    .stream()
                    .map(roleId -> UserRole.builder().roleId(roleId).userId(id).build())
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }

        // 移除角色
        if (removing != null && removing.size() > 0) {
            userRoleService.removeByIds(removing);
        }
    }

    @Transactional
    @Override
    public void save(UserForm form) {
        User user = new User();
        BeanUtils.copyProperties(form, user);

        user.setPassword(Md5Util.encode(user.getPassword()));

        // 保存用户信息
        save(user);
        List<Integer> assigning = form.getAssigning();

        // 保存角色信息
        if (assigning != null && assigning.size() > 0) {
            List<UserRole> userRoles = assigning
                    .stream()
                    .map(roleId -> UserRole.builder().roleId(roleId).userId(user.getId()).build())
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }
    }

    @Transactional
    @Override
    public void del(UserForm form) {
        Integer id = form.getId();

        User old = getById(id);

        if (old == null) {
            throw new CustomException("数据不存在");
        }

        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId, id);
        userRoleService.remove(userRoleLambdaQueryWrapper);

        removeById(id);
    }
}
