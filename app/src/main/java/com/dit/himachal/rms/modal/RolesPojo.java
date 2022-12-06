package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class RolesPojo implements Serializable {

    private String roleName;
    private String roleId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return roleName;
    }
}

