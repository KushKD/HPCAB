package com.dit.himachal.ecabinet.interfaces;

import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.modal.ResponsObject;

import org.json.JSONException;

public interface AsyncTaskListenerObject {
    void onTaskCompleted(ResponsObject result, TaskType taskType) throws JSONException;
}
