package com.dit.himachal.rms.generic;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dit.himachal.rms.enums.TaskType;
import com.dit.himachal.rms.interfaces.AsyncTaskListenerObjectGetWidget;
import com.dit.himachal.rms.modal.GetDataPojo;
import com.dit.himachal.rms.modal.OfflineDataModel;
import com.dit.himachal.rms.network.HttpManager;

import org.json.JSONException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 05, 07 , 2020
 */
public class Generic_Async_Get_Widget extends AsyncTask<GetDataPojo,Void , OfflineDataModel> {


    String outputStr;
    Context context;
    AsyncTaskListenerObjectGetWidget taskListener;
    TaskType taskType;

    public Generic_Async_Get_Widget(Context context, AsyncTaskListenerObjectGetWidget taskListener, TaskType taskType) {
        this.context = context;
        this.taskListener = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        //dialog.setCancelable(false);
    }

    @Override
    protected OfflineDataModel doInBackground(GetDataPojo... getDataPojo) {
        OfflineDataModel Data_From_Server = null;
        HttpManager http_manager = null;
        try {
            http_manager = new HttpManager();
              if(getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.CABINET_MEETING_STATUS.toString())){
                Log.e("We Here", getDataPojo[0].getMethord());
                Data_From_Server = http_manager.GetData(getDataPojo[0]);
                return Data_From_Server;
            }




        } catch (Exception e) {
            Log.e("Value Saved",e.getLocalizedMessage().toString());
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
       // dialog.dismiss();
    }
}