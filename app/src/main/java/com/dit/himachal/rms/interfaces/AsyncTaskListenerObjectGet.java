package com.dit.himachal.rms.interfaces;

import com.dit.himachal.rms.enums.TaskType;
import com.dit.himachal.rms.modal.OfflineDataModel;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerObjectGet {
    void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException;
}