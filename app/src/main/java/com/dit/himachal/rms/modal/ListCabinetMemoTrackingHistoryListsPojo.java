package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class ListCabinetMemoTrackingHistoryListsPojo implements Serializable {



    private String Action;
    private String Date;
    private String Remarks;
    private String Status;
    private String Title;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
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
        return "ListCabinetMemoTrackingHistoryListsPojo{" +
                "Action='" + Action + '\'' +
                ", Date='" + Date + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", Status='" + Status + '\'' +
                ", Title='" + Title + '\'' +
                '}';
    }
}
