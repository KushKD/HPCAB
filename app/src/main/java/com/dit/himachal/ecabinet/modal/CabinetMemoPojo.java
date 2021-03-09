package com.dit.himachal.ecabinet.modal;

import java.io.Serializable;
import java.util.List;

public class CabinetMemoPojo implements Serializable {


    private String AdditionalInformation;
    private String AgendaItemNo;
    private String AgendaItemType;
    private String ApprovalStatus;
    private String CabinetMemoID;
    private String DeptName;
    private String BranchId;
    private String Deptid;
    private String FileNo;
    private String ListAdvisoryDepartments;
    private List<ListAnnexures> ListAnnexures_;
    private List<ListCabinetMemoTrackingHistoryListsPojo> ListCabinetMemoTrackingHistoryLists;
    private List<ListConsiderationPoints> ListConsiderationPoints;
    private String Meetingdate;
    private String MemoStatus;
    private String MinisterIncharge;
    private String ProposalDetails;
    private String SecIncharge;
    private String Subject;
    private String Date;
    private String StatusMessage;
    private String Currentlywith;


    public String getCurrentlywith() {
        return Currentlywith;
    }

    public void setCurrentlywith(String currentlywith) {
        Currentlywith = currentlywith;
    }

    public String getBranchId() {
        return BranchId;
    }

    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

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



    public List<ListCabinetMemoTrackingHistoryListsPojo> getListCabinetMemoTrackingHistoryLists() {
        return ListCabinetMemoTrackingHistoryLists;
    }

    public void setListCabinetMemoTrackingHistoryLists(List<ListCabinetMemoTrackingHistoryListsPojo> listCabinetMemoTrackingHistoryLists) {
        ListCabinetMemoTrackingHistoryLists = listCabinetMemoTrackingHistoryLists;
    }

    public List<com.dit.himachal.ecabinet.modal.ListConsiderationPoints> getListConsiderationPoints() {
        return ListConsiderationPoints;
    }

    public void setListConsiderationPoints(List<com.dit.himachal.ecabinet.modal.ListConsiderationPoints> listConsiderationPoints) {
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


    public List<ListAnnexures> getListAnnexures_() {
        return ListAnnexures_;
    }

    public void setListAnnexures_(List<ListAnnexures> listAnnexures_) {
        ListAnnexures_ = listAnnexures_;
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
                ", BranchId='" + BranchId + '\'' +
                ", Deptid='" + Deptid + '\'' +
                ", FileNo='" + FileNo + '\'' +
                ", ListAdvisoryDepartments='" + ListAdvisoryDepartments + '\'' +
                ", ListAnnexures_=" + ListAnnexures_ +
                ", ListCabinetMemoTrackingHistoryLists=" + ListCabinetMemoTrackingHistoryLists +
                ", ListConsiderationPoints=" + ListConsiderationPoints +
                ", Meetingdate='" + Meetingdate + '\'' +
                ", MemoStatus='" + MemoStatus + '\'' +
                ", MinisterIncharge='" + MinisterIncharge + '\'' +
                ", ProposalDetails='" + ProposalDetails + '\'' +
                ", SecIncharge='" + SecIncharge + '\'' +
                ", Subject='" + Subject + '\'' +
                ", Date='" + Date + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                ", Currentlywith='" + Currentlywith + '\'' +
                '}';
    }
}
