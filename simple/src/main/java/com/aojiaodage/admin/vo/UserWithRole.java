package com.aojiaodage.admin.vo;

import com.aojiaodage.admin.entity.User;

import java.util.List;

public class UserWithRole {
    private User user;
    private List<RoleWithAssigned> roles;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<RoleWithAssigned> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleWithAssigned> roles) {
        this.roles = roles;
    }
}
