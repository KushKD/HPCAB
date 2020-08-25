package com.dit.himachal.ecabinet.interfaces;

import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.modal.ResponsObject;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerObjectGetWidget {
    void onTaskCompleted(ResponsObject result, TaskType taskType) throws JSONException;
}