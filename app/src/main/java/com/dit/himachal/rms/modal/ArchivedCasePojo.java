package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class ArchivedCasePojo implements Serializable {

    private String CaseId;
    private String CaseNo;
    private String CaseSubType;
    private String CaseTitle;
    private String CaseType;
    private String CaseYear;
    private String CourtId;
    private String InstitutionDate;
    private String StatusMessage;

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String getCaseNo() {
        return CaseNo;
    }

    public void setCaseNo(String caseNo) {
        CaseNo = caseNo;
    }

    public String getCaseSubType() {
        return CaseSubType;
    }

    public void setCaseSubType(String caseSubType) {
        CaseSubType = caseSubType;
    }

    public String getCaseTitle() {
        return CaseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        CaseTitle = caseTitle;
    }

    public String getCaseType() {
        return CaseType;
    }

    public void setCaseType(String caseType) {
        CaseType = caseType;
    }

    public String getCaseYear() {
        return CaseYear;
    }

    public void setCaseYear(String caseYear) {
        CaseYear = caseYear;
    }

    public String getCourtId() {
        return CourtId;
    }

    public void setCourtId(String courtId) {
        CourtId = courtId;
    }

    public String getInstitutionDate() {
        return InstitutionDate;
    }

    public void setInstitutionDate(String institutionDate) {
        InstitutionDate = institutionDate;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "ArchivedCasePojo{" +
                "CaseId='" + CaseId + '\'' +
                ", CaseNo='" + CaseNo + '\'' +
                ", CaseSubType='" + CaseSubType + '\'' +
                ", CaseTitle='" + CaseTitle + '\'' +
                ", CaseType='" + CaseType + '\'' +
                ", CaseYear='" + CaseYear + '\'' +
                ", CourtId='" + CourtId + '\'' +
                ", InstitutionDate='" + InstitutionDate + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                '}';
    }
}
