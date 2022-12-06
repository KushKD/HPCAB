package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class DepartmentsPojo implements Serializable {

    private String deptName;
    private String deptId;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return deptName;
    }
}

