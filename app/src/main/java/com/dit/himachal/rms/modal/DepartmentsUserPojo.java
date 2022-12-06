package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class DepartmentsUserPojo implements Serializable {

    private String Departmentname;
    private String DepartmentID;

    public String getDepartmentname() {
        return Departmentname;
    }

    public void setDepartmentname(String departmentname) {
        Departmentname = departmentname;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    @Override
    public String toString() {
        return "DepartmentsUserPojo{" +
                "Departmentname='" + Departmentname + '\'' +
                ", DepartmentID='" + DepartmentID + '\'' +
                '}';
    }
}
