package com.aojiaodage.admin.service.impl;

import com.aojiaodage.admin.dao.PermissionDao;
import com.aojiaodage.admin.dto.PermissionForm;
import com.aojiaodage.admin.entity.Permission;
import com.aojiaodage.admin.entity.RolePermission;
import com.aojiaodage.admin.enums.PermissionType;
import com.aojiaodage.admin.service.PermissionService;
import com.aojiaodage.admin.service.RolePermissionService;
import com.aojiaodage.admin.vo.RoleWithCode;
import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.interfaces.ChildPredicate;
import com.aojiaodage.common.util.TreeMaker;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Autowired
    RolePermissionService rolePermissionService;

    Predicate<Permission> rootPredicate = item -> item.getParentId() != null && item.getParentId().equals(-1);

    ChildPredicate<Permission> childPredicate = (Permission o1, Permission o2) -> o1.getParentId().equals(o2.getId());

    @Cacheable(cacheNames = "roleToCode", key = "#id")
    @Override
    public Map<String, List<String>> getRoleToCodeMapByUserId(Integer id) {
        List<RoleWithCode> roleWithCodeList = baseMapper.selectRoleListByUserId(id);

        Map<String, List<String>> map = new HashMap<>();

        for (RoleWithCode roleWithCode : roleWithCodeList) {
            map.put(roleWithCode.getRoleId().toString(), roleWithCode.getCodes());
        }
        return map;
    }

    @Override
    public List<Permission> getList() {
        List<Permission> list = list();
        return TreeMaker.make(list, rootPredicate, childPredicate);
    }

    @Override
    public void save(PermissionForm form) {
        boolean included = PermissionType.in(form.getType());
        if (!included) {
            throw new CustomException("字段type输入错误");
        }
        Permission permission = new Permission();
        BeanUtils.copyProperties(form, permission);

        // 根节点
        if (permission.getParentId() == null) {
            permission.setParentId(-1);
        }

        if (permission.getParentId() == -1) {
            if (PermissionType.Menu.getValue() != permission.getType()) {
                throw new CustomException("根权限必须为「菜单」类型");
            }
        }

        //「操作」权限只允许在根节点下添加
        save(permission);
    }

    @Transactional
    @Override
    public void del(PermissionForm form) {
        Integer id = form.getId();
        Permission old = getById(id);

        if (old == null) {
            throw new RuntimeException("指定的记录不存在");
        }

        // 若存在子权限时，不允许删除
        LambdaQueryWrapper<Permission> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Permission::getParentId, id);
        Integer count = baseMapper.selectCount(lambdaQueryWrapper);

        if (count > 0) {
            throw new CustomException("操作失败，当前权限存在子权限");
        }

        // 删除角色&权限关系、权限
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rolePermissionLambdaQueryWrapper.eq(RolePermission::getPermissionId, id);
        rolePermissionService.remove(rolePermissionLambdaQueryWrapper);
        removeById(id);
    }

    @Override
    public void update(PermissionForm form) {
        Permission old = getById(form.getId());

        if (old == null) {
            throw new RuntimeException("指定的记录不存在");
        }

        Permission permission = new Permission();
        permission.setId(form.getId());
        permission.setExtra(form.getExtra());
        permission.setOrder(form.getOrder());
        permission.setPath(form.getPath());
        permission.setName(form.getName());
        permission.setValue(form.getValue());
        updateById(permission);
    }

    @Cacheable(cacheNames = "user:menu", key = "#id")
    @Override
    public List<Permission> getMenuListByUserId(Integer id) {
        return baseMapper.selectMenuListByUserId(id);
    }

    // 这种管理员使用的接口，其实不需要缓存，使用频率不高
    @Override
    public List<Permission> getPermissionListByRoleId(Integer id) {
        return baseMapper.selectPermissionListByRoleId(id);
    }
}
