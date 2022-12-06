package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class CauseListPojo implements Serializable {

    private String CaseFixedFor;
    private String CaseNo;
    private String caseTitle;
    private String CaseYear;
    private String CauseList;
    private String Court;
    private String HearingAddress;
    private String HearingDate;
    private String StatusMessage;

    public String getCaseFixedFor() {
        return CaseFixedFor;
    }

    public void setCaseFixedFor(String caseFixedFor) {
        CaseFixedFor = caseFixedFor;
    }

    public String getCaseNo() {
        return CaseNo;
    }

    public void setCaseNo(String caseNo) {
        CaseNo = caseNo;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getCaseYear() {
        return CaseYear;
    }

    public void setCaseYear(String caseYear) {
        CaseYear = caseYear;
    }

    public String getCauseList() {
        return CauseList;
    }

    public void setCauseList(String causeList) {
        CauseList = causeList;
    }

    public String getCourt() {
        return Court;
    }

    public void setCourt(String court) {
        Court = court;
    }

    public String getHearingAddress() {
        return HearingAddress;
    }

    public void setHearingAddress(String hearingAddress) {
        HearingAddress = hearingAddress;
    }

    public String getHearingDate() {
        return HearingDate;
    }

    public void setHearingDate(String hearingDate) {
        HearingDate = hearingDate;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "CauseListPojo{" +
                "CaseFixedFor='" + CaseFixedFor + '\'' +
                ", CaseNo='" + CaseNo + '\'' +
                ", caseTitle='" + caseTitle + '\'' +
                ", CaseYear='" + CaseYear + '\'' +
                ", CauseList='" + CauseList + '\'' +
                ", Court='" + Court + '\'' +
                ", HearingAddress='" + HearingAddress + '\'' +
                ", HearingDate='" + HearingDate + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                '}';
    }
}
