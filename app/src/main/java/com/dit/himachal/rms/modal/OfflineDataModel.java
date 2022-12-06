package com.dit.himachal.rms.modal;

import java.io.Serializable;

public class OfflineDataModel implements Serializable {

    private String id;
    private String url;
    private String params;
    private String response;
    private String httpFlag;
    private String functionName;
    private String userId;
    private String roleId;
    private String bifurcation;


    public String getBifurcation() {
        return bifurcation;
    }

    public void setBifurcation(String bifurcation) {
        this.bifurcation = bifurcation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getHttpFlag() {
        return httpFlag;
    }

    public void setHttpFlag(String httpFlag) {
        this.httpFlag = httpFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    @Override
    public String toString() {
        return "OfflineDataModel{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                ", httpFlag='" + httpFlag + '\'' +
                ", functionName='" + functionName + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", bifurcation='" + bifurcation + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
