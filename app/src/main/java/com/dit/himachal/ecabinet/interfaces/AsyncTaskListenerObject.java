package com.dit.himachal.ecabinet.interfaces;

import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.modal.ResponsObject;

import org.json.JSONException;

public interface AsyncTaskListenerObject {
    void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException;
}
