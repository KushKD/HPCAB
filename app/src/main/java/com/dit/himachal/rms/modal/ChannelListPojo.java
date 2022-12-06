package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class ChannelListPojo implements Serializable {

    private String ChannelID;
    private String Channel;
    private String StatusCode;
    private String StatusMessage;


    public String getChannelID() {
        return ChannelID;
    }

    public void setChannelID(String channelID) {
        ChannelID = channelID;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
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

    @Override
    public String toString() {
        return getStatusCode();
    }
}
