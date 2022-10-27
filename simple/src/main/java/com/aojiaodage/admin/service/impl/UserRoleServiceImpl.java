package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.UserRoleDao;
import com.aojiaodage.admin.entity.UserRole;
import com.aojiaodage.admin.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
}
