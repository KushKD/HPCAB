package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class SendToList implements Serializable {

    private String Pendingwithroleid;
    private String Name;
    private String StatusCode;
    private String StatusMessage;

    public String getPendingwithroleid() {
        return Pendingwithroleid;
    }

    public void setPendingwithroleid(String pendingwithroleid) {
        Pendingwithroleid = pendingwithroleid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return Name;
    }
}
