package com.dit.himachal.ecabinet.interfaces;

import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.modal.ResponsObject;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerObjectGet {
    void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException;
}