package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class AgendaPojo implements Serializable {

    private String AgendaItemNo;
    private String AgendaItemType;
    private String DeptName;
    private String FileNo;
    private String Subject;

    public String getAgendaItemNo() {
        return AgendaItemNo;
    }

    public void setAgendaItemNo(String agendaItemNo) {
        AgendaItemNo = agendaItemNo;
    }

    public String getAgendaItemType() {
        return AgendaItemType;
    }

    public void setAgendaItemType(String agendaItemType) {
        AgendaItemType = agendaItemType;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    @Override
    public String toString() {
        return "AgendaPojo{" +
                "AgendaItemNo='" + AgendaItemNo + '\'' +
                ", AgendaItemType='" + AgendaItemType + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", FileNo='" + FileNo + '\'' +
                ", Subject='" + Subject + '\'' +
                '}';
    }
}
