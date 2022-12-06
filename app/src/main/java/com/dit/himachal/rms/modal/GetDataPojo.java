package com.dit.himachal.rms.modal;

import com.dit.himachal.rms.enums.TaskType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 21, 08 , 2020
 */
public class GetDataPojo implements Serializable {

    private String url;
    private String methord;
    private String methordHash;
    private List<String> parameters;
    private String timeStamp;
    private TaskType taskType;
    private String DepartmentId ;
    private String bifurcation;

    public String getBifurcation() {
        return bifurcation;
    }

    public void setBifurcation(String bifurcation) {
        this.bifurcation = bifurcation;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

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

    public String getMethordHash() {
        return methordHash;
    }

    public void setMethordHash(String methordHash) {
        this.methordHash = methordHash;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "GetDataPojo{" +
                "url='" + url + '\'' +
                ", methord='" + methord + '\'' +
                ", methordHash='" + methordHash + '\'' +
                ", parameters=" + parameters +
                ", timeStamp='" + timeStamp + '\'' +
                ", taskType=" + taskType +
                ", DepartmentId='" + DepartmentId + '\'' +
                ", bifurcation='" + bifurcation + '\'' +
                '}';
    }
}
