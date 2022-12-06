package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class UserAdvocate implements Serializable {

    private String AdvocateName; //:	UmFrZXNoIFRoYWt1cg==
    private String Loginuserinfo; //	:	UmFrZXNoIFRoYWt1cnxBZHZvY2F0ZQ==
    private String MobileNo; //	:	ODA5MTAyMjMzNA==
    private String RoleId; //	:	MQ==
    private String Userid; //	:	Mg==
    private String Username; //	:	ODA5MTAyMjMzNA==

    public String getAdvocateName() {
        return AdvocateName;
    }

    public void setAdvocateName(String advocateName) {
        AdvocateName = advocateName;
    }

    public String getLoginuserinfo() {
        return Loginuserinfo;
    }

    public void setLoginuserinfo(String loginuserinfo) {
        Loginuserinfo = loginuserinfo;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public String toString() {
        return "UserAdvocate{" +
                "AdvocateName='" + AdvocateName + '\'' +
                ", Loginuserinfo='" + Loginuserinfo + '\'' +
                ", MobileNo='" + MobileNo + '\'' +
                ", RoleId='" + RoleId + '\'' +
                ", Userid='" + Userid + '\'' +
                ", Username='" + Username + '\'' +
                '}';
    }
}
