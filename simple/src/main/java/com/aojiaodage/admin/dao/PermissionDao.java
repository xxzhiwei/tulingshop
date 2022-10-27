package com.aojiaodage.admin.dao;

import com.aojiaodage.admin.entity.Permission;
import com.aojiaodage.admin.vo.RoleWithCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface PermissionDao extends BaseMapper<Permission> {

    List<Permission> selectPermissionListByRoleId(Integer id);

    List<RoleWithCode> selectRoleListByUserId(Integer id);

    List<Permission> selectMenuListByUserId(Integer id);
    List<Permission> selectPermissionListByUserId(Integer id);
}
