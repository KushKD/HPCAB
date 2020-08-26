package com.dit.himachal.ecabinet.modal;

import java.io.Serializable;

public class CabinetMemoPojo implements Serializable {


    private String AdditionalInformation;
    private String AgendaItemNo;
    private String AgendaItemType;
    private String ApprovalStatus;
    private String CabinetMemoID;
    private String DeptName;
    private String Deptid;
    private String FileNo;
    private String ListAdvisoryDepartments;
    private String ListAnnexures;
    private String ListCabinetMemoTrackingHistoryLists;
    private String ListConsiderationPoints;
    private String Meetingdate;
    private String MemoStatus;
    private String MinisterIncharge;
    private String ProposalDetails;
    private String SecIncharge;
    private String Subject;

    public String getAdditionalInformation() {
        return AdditionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        AdditionalInformation = additionalInformation;
    }

    public String getAgendaItemNo() {
        return AgendaItemNo;
    }

    public void setAgendaItemNo(String agendaItemNo) {
        AgendaItemNo = agendaItemNo;
    }

    public String getAgendaItemType() {
        return AgendaItemType;
    }

    public void setAgendaItemType(String agendaItemType) {
        AgendaItemType = agendaItemType;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getCabinetMemoID() {
        return CabinetMemoID;
    }

    public void setCabinetMemoID(String cabinetMemoID) {
        CabinetMemoID = cabinetMemoID;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getDeptid() {
        return Deptid;
    }

    public void setDeptid(String deptid) {
        Deptid = deptid;
    }

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public String getListAdvisoryDepartments() {
        return ListAdvisoryDepartments;
    }

    public void setListAdvisoryDepartments(String listAdvisoryDepartments) {
        ListAdvisoryDepartments = listAdvisoryDepartments;
    }

    public String getListAnnexures() {
        return ListAnnexures;
    }

    public void setListAnnexures(String listAnnexures) {
        ListAnnexures = listAnnexures;
    }

    public String getListCabinetMemoTrackingHistoryLists() {
        return ListCabinetMemoTrackingHistoryLists;
    }

    public void setListCabinetMemoTrackingHistoryLists(String listCabinetMemoTrackingHistoryLists) {
        ListCabinetMemoTrackingHistoryLists = listCabinetMemoTrackingHistoryLists;
    }

    public String getListConsiderationPoints() {
        return ListConsiderationPoints;
    }

    public void setListConsiderationPoints(String listConsiderationPoints) {
        ListConsiderationPoints = listConsiderationPoints;
    }

    public String getMeetingdate() {
        return Meetingdate;
    }

    public void setMeetingdate(String meetingdate) {
        Meetingdate = meetingdate;
    }

    public String getMemoStatus() {
        return MemoStatus;
    }

    public void setMemoStatus(String memoStatus) {
        MemoStatus = memoStatus;
    }

    public String getMinisterIncharge() {
        return MinisterIncharge;
    }

    public void setMinisterIncharge(String ministerIncharge) {
        MinisterIncharge = ministerIncharge;
    }

    public String getProposalDetails() {
        return ProposalDetails;
    }

    public void setProposalDetails(String proposalDetails) {
        ProposalDetails = proposalDetails;
    }

    public String getSecIncharge() {
        return SecIncharge;
    }

    public void setSecIncharge(String secIncharge) {
        SecIncharge = secIncharge;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    @Override
    public String toString() {
        return "CabinetMemoPojo{" +
                "AdditionalInformation='" + AdditionalInformation + '\'' +
                ", AgendaItemNo='" + AgendaItemNo + '\'' +
                ", AgendaItemType='" + AgendaItemType + '\'' +
                ", ApprovalStatus='" + ApprovalStatus + '\'' +
                ", CabinetMemoID='" + CabinetMemoID + '\'' +
                ", DeptName='" + DeptName + '\'' +
                ", Deptid='" + Deptid + '\'' +
                ", FileNo='" + FileNo + '\'' +
                ", ListAdvisoryDepartments='" + ListAdvisoryDepartments + '\'' +
                ", ListAnnexures='" + ListAnnexures + '\'' +
                ", ListCabinetMemoTrackingHistoryLists='" + ListCabinetMemoTrackingHistoryLists + '\'' +
                ", ListConsiderationPoints='" + ListConsiderationPoints + '\'' +
                ", Meetingdate='" + Meetingdate + '\'' +
                ", MemoStatus='" + MemoStatus + '\'' +
                ", MinisterIncharge='" + MinisterIncharge + '\'' +
                ", ProposalDetails='" + ProposalDetails + '\'' +
                ", SecIncharge='" + SecIncharge + '\'' +
                ", Subject='" + Subject + '\'' +
                '}';
    }
}
