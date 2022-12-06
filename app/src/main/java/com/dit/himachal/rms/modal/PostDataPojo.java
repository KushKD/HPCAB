package com.dit.himachal.rms.modal;

import com.dit.himachal.rms.enums.TaskType;

import java.io.Serializable;

public class PostDataPojo implements Serializable {
    private String url;
    private String methord;
    private PostObject parameters;
    private TaskType taskType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethord() {
        return methord;
    }

    public void setMethord(String methord) {
        this.methord = methord;
    }

    public PostObject getParameters() {
        return parameters;
    }

    public void setParameters(PostObject parameters) {
        this.parameters = parameters;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "PostDataPojo{" +
                "url='" + url + '\'' +
                ", methord='" + methord + '\'' +
                ", parameters=" + parameters +
                ", taskType=" + taskType +
                '}';
    }
}
