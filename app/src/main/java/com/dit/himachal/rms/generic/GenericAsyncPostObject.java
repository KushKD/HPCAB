package com.dit.himachal.rms.generic;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.dit.himachal.rms.enums.TaskType;
import com.dit.himachal.rms.interfaces.AsyncTaskListenerObject;
import com.dit.himachal.rms.modal.OfflineDataModel;
import com.dit.himachal.rms.modal.PostDataPojo;
import com.dit.himachal.rms.network.HttpManager;

import org.json.JSONException;

public class GenericAsyncPostObject extends AsyncTask<PostDataPojo,Void , OfflineDataModel> {

    ProgressDialog dialog;
    Context context;
    AsyncTaskListenerObject taskListener_;
    TaskType taskType;
    private ProgressDialog mProgressDialog;

    public GenericAsyncPostObject(Context context, AsyncTaskListenerObject taskListener, TaskType taskType){
        this.context = context;
        this.taskListener_ = taskListener;
        this.taskType = taskType;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog.show(context, "Loading", "Connecting to Server .. Please Wait", true);
        dialog.setCancelable(false);
    }

    @Override
    protected OfflineDataModel doInBackground(PostDataPojo... uploadObjects) {
        PostDataPojo data = null;
        data = uploadObjects[0];
        HttpManager http_manager = null;
        OfflineDataModel Data_From_Server = null;
        boolean save = false;

        try{
            http_manager = new HttpManager();

            if(data.getTaskType().toString().equalsIgnoreCase(TaskType.SEND_BACK.toString())){
                Data_From_Server = http_manager.PostDataScanQRCode(data);
                Log.e("Data hhghsds",Data_From_Server.toString());
                return Data_From_Server;
            }  else if(data.getTaskType().toString().equalsIgnoreCase(TaskType.FORWARD.toString())){
                Data_From_Server = http_manager.PostDataScanQRCode(data);
                Log.e("Data hhghsds",Data_From_Server.toString());
                return Data_From_Server;
            } //ALLOW
             else if(data.getTaskType().toString().equalsIgnoreCase(TaskType.ALLOW.toString())){
            Data_From_Server = http_manager.PostDataScanQRCode(data);
            Log.e("Data hhghsds",Data_From_Server.toString());
            return Data_From_Server;
        }



    }catch(Exception e){
            return Data_From_Server;
        }


        return Data_From_Server;
    }

    @Override
    protected void onPostExecute(OfflineDataModel result) {
        super.onPostExecute(result);

        try {
            taskListener_.onTaskCompleted(result, taskType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }
}