package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class ZimniOrderPojo implements Serializable {

    private String CaseNo;
    private String CaseTitle;
    private String CaseYear;
    private String HearingDate;
    private String PublishedDate;
    private String StatusMessage;
    private String ZimniPdf;

    public String getZimniPdf() {
        return ZimniPdf;
    }

    public void setZimniPdf(String zimniPdf) {
        ZimniPdf = zimniPdf;
    }

    public String getCaseNo() {
        return CaseNo;
    }

    public void setCaseNo(String caseNo) {
        CaseNo = caseNo;
    }

    public String getCaseTitle() {
        return CaseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        CaseTitle = caseTitle;
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

    public String getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        PublishedDate = publishedDate;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "ZimniOrderPojo{" +
                "CaseNo='" + CaseNo + '\'' +
                ", CaseTitle='" + CaseTitle + '\'' +
                ", CaseYear='" + CaseYear + '\'' +
                ", HearingDate='" + HearingDate + '\'' +
                ", PublishedDate='" + PublishedDate + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                ", ZimniPdf='" + ZimniPdf + '\'' +
                '}';
    }
}
