package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class AdvocateListPojo implements Serializable {


    private String AdvocateName;
    private String BarCouncilName;
    private String DateofRegistration;
    private String PassportPhoto;
    private String RegistrationNo;
    private String Token;
    private String StatusMessage;

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public String getAdvocateName() {
        return AdvocateName;
    }

    public void setAdvocateName(String advocateName) {
        AdvocateName = advocateName;
    }

    public String getBarCouncilName() {
        return BarCouncilName;
    }

    public void setBarCouncilName(String barCouncilName) {
        BarCouncilName = barCouncilName;
    }

    public String getDateofRegistration() {
        return DateofRegistration;
    }

    public void setDateofRegistration(String dateofRegistration) {
        DateofRegistration = dateofRegistration;
    }

    public String getPassportPhoto() {
        return PassportPhoto;
    }

    public void setPassportPhoto(String passportPhoto) {
        PassportPhoto = passportPhoto;
    }

    public String getRegistrationNo() {
        return RegistrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        RegistrationNo = registrationNo;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "CabinetMemoPojo{" +
                "AdvocateName='" + AdvocateName + '\'' +
                ", BarCouncilName='" + BarCouncilName + '\'' +
                ", DateofRegistration='" + DateofRegistration + '\'' +
                ", PassportPhoto='" + PassportPhoto + '\'' +
                ", RegistrationNo='" + RegistrationNo + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
