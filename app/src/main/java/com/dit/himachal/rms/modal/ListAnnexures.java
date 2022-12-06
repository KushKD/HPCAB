package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class ListAnnexures implements Serializable {



    private  String  AnnexureID;
    private  String  AnnexureName;
    private  String  Attachment;
    private  String  CabinetMemoID;
    private  String  Title;

    public String getAnnexureID() {
        return AnnexureID;
    }

    public void setAnnexureID(String annexureID) {
        AnnexureID = annexureID;
    }

    public String getAnnexureName() {
        return AnnexureName;
    }

    public void setAnnexureName(String annexureName) {
        AnnexureName = annexureName;
    }

    public String getAttachment() {
        return Attachment;
    }

    public void setAttachment(String attachment) {
        Attachment = attachment;
    }

    public String getCabinetMemoID() {
        return CabinetMemoID;
    }

    public void setCabinetMemoID(String cabinetMemoID) {
        CabinetMemoID = cabinetMemoID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return "ListAnnexures{" +
                "AnnexureID='" + AnnexureID + '\'' +
                ", AnnexureName='" + AnnexureName + '\'' +
                ", Attachment='" + Attachment + '\'' +
                ", CabinetMemoID='" + CabinetMemoID + '\'' +
                ", Title='" + Title + '\'' +
                '}';
    }
}
