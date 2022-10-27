package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.RolePermissionDao;
import com.aojiaodage.admin.entity.RolePermission;
import com.aojiaodage.admin.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {
}
