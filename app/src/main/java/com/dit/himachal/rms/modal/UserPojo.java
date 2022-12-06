package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class UserPojo implements Serializable {

   private String name;
   private String designation;
   private String mobileNumber;
   private String Userid;
   private String Photo;

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }



}

