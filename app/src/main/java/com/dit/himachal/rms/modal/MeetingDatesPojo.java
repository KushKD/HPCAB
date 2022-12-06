package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class MeetingDatesPojo implements Serializable {

    private String FileNo;
    private String MeetingDate;
    private String MeetingDateTime;
    private String MeetinID;
    private String MeetingStatus;
    private String MeetingTime;
    private String VenueID;
    private String VenueName;

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

    public String getMeetinID() {
        return MeetinID;
    }

    public void setMeetinID(String meetinID) {
        MeetinID = meetinID;
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

    @Override
    public String toString() {
        return MeetingDate;
    }
}
