package com.dit.himachal.rms.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dit.himachal.rms.enums.TaskType;
import com.dit.himachal.rms.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.rms.modal.GetDataPojo;
import com.dit.himachal.rms.modal.OfflineDataModel;
import com.dit.himachal.rms.network.HttpManager;

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
              if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.GET_OTP_VIA_MOBILE.toString())){
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
            }
            else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.Get_Registered_Advocates_List.toString())) {
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.Get_Archive_Cases.toString())) {
                  Log.e("We Here", getDataPojo[0].getMethord());
                  Data_From_Server = http_manager.GetData(getDataPojo[0]);
                  return Data_From_Server;
              }else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.Get_Advocate_Subscribed_Cause_List.toString())) {
                  Log.e("We Here", getDataPojo[0].getMethord());
                  Data_From_Server = http_manager.GetData(getDataPojo[0]);
                  return Data_From_Server;
              }else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.Get_Case_Notices_By_Advocate.toString())) {
                  Log.e("We Here", getDataPojo[0].getMethord());
                  Data_From_Server = http_manager.GetData(getDataPojo[0]);
                  return Data_From_Server;
              }
              else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.Get_Subscribed_Cases.toString())) {
                  Log.e("We Here", getDataPojo[0].getMethord());
                  Data_From_Server = http_manager.GetData(getDataPojo[0]);
                  return Data_From_Server;
              } else if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.Get_Zimni_Orders_Advocate.toString())) {
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