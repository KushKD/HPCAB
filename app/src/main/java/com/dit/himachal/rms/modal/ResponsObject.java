package com.dit.himachal.rms.modal;

import java.io.Serializable;

/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 21, 08 , 2020
 */
public class ResponsObject implements Serializable {

    public String successFailure;
    public Integer responseCode;
    public String respnse;
    public String dept_id;

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public String getSuccessFailure() {
        return successFailure;
    }

    public void setSuccessFailure(String successFailure) {
        this.successFailure = successFailure;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getRespnse() {
        return respnse;
    }

    public void setRespnse(String respnse) {
        this.respnse = respnse;
    }

    @Override
    public String toString() {
        return "ResponsObject{" +
                "successFailure='" + successFailure + '\'' +
                ", responseCode=" + responseCode +
                ", respnse='" + respnse + '\'' +
                ", dept_id='" + dept_id + '\'' +
                '}';
    }
}
