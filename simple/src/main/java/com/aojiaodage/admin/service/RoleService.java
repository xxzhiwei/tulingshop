package com.aojiaodage.admin.service;

import com.aojiaodage.admin.dto.RoleForm;
import com.aojiaodage.admin.entity.Role;
import com.aojiaodage.common.dto.Query;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService extends IService<Role> {
    /**
     * 获取用户角色id
     * @param id 用户id
     * @return List<Integer> 角色id列表
     */
    List<Integer> getRoleIdsByUserId(Integer id);

    Page<Role> getPagination(Query query);

    void save(RoleForm form);
    void del(RoleForm form);
    void update(RoleForm form);

    List<Role> getList();

    List<Role> getListByUserId(Integer userId);
}
