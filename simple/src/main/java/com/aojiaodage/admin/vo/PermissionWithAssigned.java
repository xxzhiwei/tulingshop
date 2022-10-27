package com.aojiaodage.admin.vo;

import com.aojiaodage.admin.entity.Permission;

public class PermissionWithAssigned extends Permission {

    private Integer assigned;

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }
}
