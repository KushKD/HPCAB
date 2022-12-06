package com.dit.himachal.rms.interfaces;

import com.dit.himachal.rms.enums.TaskType;
import com.dit.himachal.rms.modal.OfflineDataModel;

import org.json.JSONException;

public interface AsyncTaskListenerObject {
    void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException;
}
