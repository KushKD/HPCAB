package com.dit.himachal.rms.modal;

import com.dit.himachal.rms.enums.TaskType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class PostObject implements Serializable {

    private String userid;
    private String cabinetMemoId;
    private String roleid;
    private String deptId;
    private String remarks;
    private String token;
    private String phone;
    private String otp;
    private TaskType taskType;
    private String PendingWithRoleID;
    private String UserName;
    private String MinisterinCharge;
    private String SecinIncharge;

    public String getMinisterinCharge() {
        return MinisterinCharge;
    }

    public void setMinisterinCharge(String ministerinCharge) {
        MinisterinCharge = ministerinCharge;
    }

    public String getSecinIncharge() {
        return SecinIncharge;
    }

    public void setSecinIncharge(String secinIncharge) {
        SecinIncharge = secinIncharge;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPendingWithRoleID() {
        return PendingWithRoleID;
    }

    public void setPendingWithRoleID(String pendingWithRoleID) {
        PendingWithRoleID = pendingWithRoleID;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCabinetMemoId() {
        return cabinetMemoId;
    }

    public void setCabinetMemoId(String cabinetMemoId) {
        this.cabinetMemoId = cabinetMemoId;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "PostObject{" +
                "userid='" + userid + '\'' +
                ", cabinetMemoId='" + cabinetMemoId + '\'' +
                ", roleid='" + roleid + '\'' +
                ", deptId='" + deptId + '\'' +
                ", remarks='" + remarks + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", otp='" + otp + '\'' +
                ", taskType=" + taskType +
                '}';
    }

    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        JSONObject  ownderDetails = new JSONObject();
        JSONObject barrier = new JSONObject();

        try {
            jsonObject.put("UserId", getUserid());
            jsonObject.put("CabinetMemoID", getCabinetMemoId());
            jsonObject.put("DeptId", getDeptId());
            jsonObject.put("RoleID", getRoleid());
            jsonObject.put("Token", getToken());
            jsonObject.put("remarks", getRemarks());
            jsonObject.put("OTP", getOtp());
            jsonObject.put("MobileNO", getPhone());
            jsonObject.put("PendingWithRoleID", getPendingWithRoleID());
            jsonObject.put("LoginUsername", UserName);
            jsonObject.put("MinisterinCharge", getMinisterinCharge());
            jsonObject.put("SecinIncharge", getSecinIncharge());


            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
