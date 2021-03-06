package com.dit.himachal.ecabinet.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.network.HttpManager;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 07 , 2020
 */
public class Generic_Async_Get extends AsyncTask<GetDataPojo,Void , OfflineDataModel> {


    String outputStr;
    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObjectGet taskListener;
    TaskType taskType;

    public Generic_Async_Get(Context context, AsyncTaskListenerObjectGet taskListener, TaskType taskType) {
        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        dialog.setCancelable(false);
    }

    @Override
    protected OfflineDataModel doInBackground(GetDataPojo... getDataPojo) {
        OfflineDataModel Data_From_Server = null;
        HttpManager http_manager = null;
        try {
            http_manager = new HttpManager();
            if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_ROLES.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_BRANCHES.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_USERS.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_OTP_VIA_MOBILE.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.LOGIN.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_MENU_LIST.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.CABINET_MEETING_STATUS.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.CABINET_MEMOS_DETAILS.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.ALLOW.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_ALLOWED_MEMO_LIST_CABINET.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_PUBLISHED_MEETING_ID_BY_ROLE.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.FINAL_MEETING_AGENDA_LIST.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else  if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_CABINET_DECISIONS_COUNT.toString())) {
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            } else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_ACTION.toString())) {
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            } else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_SENT_BACK_TO.toString())) {
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }







        } catch (Exception e) {
            Log.e("Value Saved", e.getLocalizedMessage());
        }
        return Data_From_Server;

    }

    @Override
    protected void onPostExecute(OfflineDataModel result) {
        super.onPostExecute(result);
        try {
            taskListener.onTaskCompleted(result, taskType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dialog.dismiss();
    }
}