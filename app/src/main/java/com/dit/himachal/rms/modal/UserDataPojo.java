package com.dit.himachal.rms.modal;

import java.io.Serializable;
import java.util.List;

public class UserDataPojo implements Serializable {

    private String Branchmapped;
    private String Desgination;
    private String IsCabinetMinister;
    private String MobileNumber;
    private String Name;
    private String UserID;
    private String RoleId;
    private List<DepartmentsUserPojo> departmentsUser;
    private String Photo;

    private String StatusMessage;

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public List<DepartmentsUserPojo> getDepartmentsUser() {
        return departmentsUser;
    }

    public void setDepartmentsUser(List<DepartmentsUserPojo> departmentsUser) {
        this.departmentsUser = departmentsUser;
    }

    public String getBranchmapped() {
        return Branchmapped;
    }

    public void setBranchmapped(String branchmapped) {
        Branchmapped = branchmapped;
    }

    public String getDesgination() {
        return Desgination;
    }

    public void setDesgination(String desgination) {
        Desgination = desgination;
    }

    public String getIsCabinetMinister() {
        return IsCabinetMinister;
    }

    public void setIsCabinetMinister(String isCabinetMinister) {
        IsCabinetMinister = isCabinetMinister;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    @Override
    public String toString() {
        return "UserDataPojo{" +
                "Branchmapped='" + Branchmapped + '\'' +
                ", Desgination='" + Desgination + '\'' +
                ", IsCabinetMinister='" + IsCabinetMinister + '\'' +
                ", MobileNumber='" + MobileNumber + '\'' +
                ", Name='" + Name + '\'' +
                ", UserID='" + UserID + '\'' +
                ", RoleId='" + RoleId + '\'' +
                ", departmentsUser=" + departmentsUser +
                ", Photo='" + Photo + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                '}';
    }
}
