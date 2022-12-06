package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class Dates implements Serializable {

    private String  FileNo;
    private String  MeetingDate;
    private String  MeetingDateTime;
    private String  MeetingID;
    private String  MeetingStatus;
    private String  MeetingTime;
    private String  Status;
    private String  StatusCode;
    private String  StatusMessage;
    private String  VenueID;
    private String  VenueName;
    private String  TotalCabinetMemos;
    private String  DepartmentID;
    private String  DepartmentName;

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public String getMeetingDate() {
        return MeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        MeetingDate = meetingDate;
    }

    public String getMeetingDateTime() {
        return MeetingDateTime;
    }

    public void setMeetingDateTime(String meetingDateTime) {
        MeetingDateTime = meetingDateTime;
    }

    public String getMeetingID() {
        return MeetingID;
    }

    public void setMeetingID(String meetingID) {
        MeetingID = meetingID;
    }

    public String getMeetingStatus() {
        return MeetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        MeetingStatus = meetingStatus;
    }

    public String getMeetingTime() {
        return MeetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        MeetingTime = meetingTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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

    public String getVenueID() {
        return VenueID;
    }

    public void setVenueID(String venueID) {
        VenueID = venueID;
    }

    public String getVenueName() {
        return VenueName;
    }

    public void setVenueName(String venueName) {
        VenueName = venueName;
    }

    public String getTotalCabinetMemos() {
        return TotalCabinetMemos;
    }

    public void setTotalCabinetMemos(String totalCabinetMemos) {
        TotalCabinetMemos = totalCabinetMemos;
    }

    public String getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(String departmentID) {
        DepartmentID = departmentID;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "FileNo='" + FileNo + '\'' +
                ", MeetingDate='" + MeetingDate + '\'' +
                ", MeetingDateTime='" + MeetingDateTime + '\'' +
                ", MeetingID='" + MeetingID + '\'' +
                ", MeetingStatus='" + MeetingStatus + '\'' +
                ", MeetingTime='" + MeetingTime + '\'' +
                ", Status='" + Status + '\'' +
                ", StatusCode='" + StatusCode + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                ", VenueID='" + VenueID + '\'' +
                ", VenueName='" + VenueName + '\'' +
                ", TotalCabinetMemos='" + TotalCabinetMemos + '\'' +
                ", DepartmentID='" + DepartmentID + '\'' +
                ", DepartmentName='" + DepartmentName + '\'' +
                '}';
    }
}
