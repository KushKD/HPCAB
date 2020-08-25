package com.dit.himachal.ecabinet.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.activities.MainActivity;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get_Widget;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGetWidget;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.network.HttpManager;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;

import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

public class MeetingStatus extends androidx.appcompat.widget.AppCompatTextView {


    CustomDialog CD = new CustomDialog();
    Context context_;
    private GetAvailability currentTask = null;

    public MeetingStatus(Context context) {
        super(context);
        SetUP_TextView(context);

    }

    public MeetingStatus(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetUP_TextView(context);

    }

    public MeetingStatus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetUP_TextView(context);

    }


    public void SetUP_TextView(Context context) {

        this.context_ = context;
//        this.setText("//[{\"AgendaItemNo\":null,\"AgendaType\":null,\"FileNo\":null,\"StatusCode\":401,\"StatusMessage\":\"VG9rZW4gaGFzIGV4cGlyZWQ=\",\"Title\":null,\"Token\":null}]\n" +
//                "          ");


    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        callAsynchronousTask();

    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (currentTask != null) {
            currentTask.cancel(true);
            currentTask = null;
        }


    }


    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {

                            if (AppStatus.getInstance(getContext()).isOnline()) {
                                GetDataPojo object = new GetDataPojo();
                                object.setUrl(Econstants.url);
                                object.setMethord(Econstants.methordGetOnlineCabinetIDMeetingStatus);
                                object.setMethordHash(Econstants.encodeBase64(Econstants.methordGetOnlineCabinetIDMeetingToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                                object.setTaskType(TaskType.CABINET_MEETING_STATUS);
                                object.setTimeStamp(CommonUtils.getTimeStamp());


                                currentTask = new GetAvailability();
                                currentTask.execute(object);

                            } else {
                                CD.showDialog((Activity) context_, "Please connect to Internet and try again.");
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 300000); //executes in every 5 minutes

        //super.setText("sdsdsdsdsdsdsds + Please connect to Internet and try again");
    }

    class GetAvailability extends AsyncTask<GetDataPojo, String, ResponsObject> {

        private ProgressDialog progressDialog;
        private String Server_Value = null;
        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //setText("Total Cars In Parking: " + "N/A");
        }

        @Override
        protected ResponsObject doInBackground(GetDataPojo... getDataPojo) {
            ResponsObject Data_From_Server = null;
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
        protected void onPostExecute(ResponsObject s) {
            super.onPostExecute(s);
            Log.e("Porn","Porn");
             setText(s.respnse);




        }

    }



}