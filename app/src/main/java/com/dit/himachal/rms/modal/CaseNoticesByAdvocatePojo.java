package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class CaseNoticesByAdvocatePojo implements Serializable {


    private String CaseNo;
    private String caseTitle;
    private String CaseYear;
    private String HearingDate;
    private String IssuedDate;
    private String Notice;
    private String NoticeCopy;
    private String StatusMessage;

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

    public String getHearingDate() {
        return HearingDate;
    }

    public void setHearingDate(String hearingDate) {
        HearingDate = hearingDate;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String issuedDate) {
        IssuedDate = issuedDate;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String notice) {
        Notice = notice;
    }

    public String getNoticeCopy() {
        return NoticeCopy;
    }

    public void setNoticeCopy(String noticeCopy) {
        NoticeCopy = noticeCopy;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "CaseNoticesByAdvocatePojo{" +
                "CaseNo='" + CaseNo + '\'' +
                ", caseTitle='" + caseTitle + '\'' +
                ", CaseYear='" + CaseYear + '\'' +
                ", HearingDate='" + HearingDate + '\'' +
                ", IssuedDate='" + IssuedDate + '\'' +
                ", Notice='" + Notice + '\'' +
                ", NoticeCopy='" + NoticeCopy + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                '}';
    }
}
