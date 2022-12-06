package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class ListConsiderationPoints implements Serializable {


    private String CabinetMemoID;
    private String ConsiderationPointID;
    private String ConsiderationPtFinalRemarks;
    private String ConsiderationPtFinalStatus;
    private String ConsiderationPtStatus;
    private String PointNumber;
    private String Status;
    private String Title;

    public String getCabinetMemoID() {
        return CabinetMemoID;
    }

    public void setCabinetMemoID(String cabinetMemoID) {
        CabinetMemoID = cabinetMemoID;
    }

    public String getConsiderationPointID() {
        return ConsiderationPointID;
    }

    public void setConsiderationPointID(String considerationPointID) {
        ConsiderationPointID = considerationPointID;
    }

    public String getConsiderationPtFinalRemarks() {
        return ConsiderationPtFinalRemarks;
    }

    public void setConsiderationPtFinalRemarks(String considerationPtFinalRemarks) {
        ConsiderationPtFinalRemarks = considerationPtFinalRemarks;
    }

    public String getConsiderationPtFinalStatus() {
        return ConsiderationPtFinalStatus;
    }

    public void setConsiderationPtFinalStatus(String considerationPtFinalStatus) {
        ConsiderationPtFinalStatus = considerationPtFinalStatus;
    }

    public String getConsiderationPtStatus() {
        return ConsiderationPtStatus;
    }

    public void setConsiderationPtStatus(String considerationPtStatus) {
        ConsiderationPtStatus = considerationPtStatus;
    }

    public String getPointNumber() {
        return PointNumber;
    }

    public void setPointNumber(String pointNumber) {
        PointNumber = pointNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return "ListConsiderationPoints{" +
                "CabinetMemoID='" + CabinetMemoID + '\'' +
                ", ConsiderationPointID='" + ConsiderationPointID + '\'' +
                ", ConsiderationPtFinalRemarks='" + ConsiderationPtFinalRemarks + '\'' +
                ", ConsiderationPtFinalStatus='" + ConsiderationPtFinalStatus + '\'' +
                ", ConsiderationPtStatus='" + ConsiderationPtStatus + '\'' +
                ", PointNumber='" + PointNumber + '\'' +
                ", Status='" + Status + '\'' +
                ", Title='" + Title + '\'' +
                '}';
    }
}
