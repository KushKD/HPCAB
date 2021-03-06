package com.dit.himachal.ecabinet.presentation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.activities.MainActivity2;
import com.dit.himachal.ecabinet.databases.DatabaseHandler;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.interfaces.LengthAgenda;
import com.dit.himachal.ecabinet.modal.AgendaPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.network.HttpManager;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.dit.himachal.ecabinet.utilities.Preferences;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Timer;
import java.util.TimerTask;

public class MeetingStatus extends LinearLayout implements AsyncTaskListenerObjectGet {


    CustomDialog CD = new CustomDialog();
    MainActivity2 MS = new MainActivity2();

    LinearLayout layout = null;
    TextView agendanumberTextView = null;
    TextView ajendanameTextView = null;
    TextView designationTextView = null;

    private LengthAgenda lengthAgendaListener;
    //If set to false, the children are clickable. If set to true, they are not.
    private boolean mDisableChildrenTouchEvents;

    Context context_;


    private GetAvailability currentTask = null;

    public MeetingStatus(Context context, LengthAgenda lengthAgenda) {
        super(context);
        this.context_ = context;
        this.lengthAgendaListener = lengthAgenda;

    }

    public MeetingStatus(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context_ = context;
        SetUP_TextView(attrs, context);
        mDisableChildrenTouchEvents = false;


    }

    public MeetingStatus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context_ = context;
        mDisableChildrenTouchEvents = false;


    }


    public void SetUP_TextView(AttributeSet attrs, Context context) {

        this.context_ = context;
        mDisableChildrenTouchEvents = false;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeetingStatus);
        String agendanumber = a.getString(R.styleable.MeetingStatus_agendanumber);
        String ajendaname = a.getString(R.styleable.MeetingStatus_ajendaname);
        String designation = a.getString(R.styleable.MeetingStatus_designation);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER | Gravity.CENTER_HORIZONTAL;

        agendanumber = agendanumber == null ? "" : agendanumber;
        ajendaname = ajendaname == null ? "" : ajendaname;
        designation = designation == null ? "" : designation;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.user_dashboard, this, true);

        agendanumberTextView = layout.findViewById(R.id.agendanumber);
        ajendanameTextView = layout.findViewById(R.id.ajendaname);
        designationTextView = layout.findViewById(R.id.designation);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(2000); //You can manage the blinking time with this parameter
        anim.setStartOffset(10);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        agendanumberTextView.startAnimation(anim);

        ajendanameTextView.setLayoutParams(params);
        agendanumberTextView.setLayoutParams(params);

        agendanumberTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordGetOnlineCabinetIDMeetingStatus);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordGetOnlineCabinetIDMeetingToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.CABINET_MEETING_STATUS);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                object.setBifurcation("CABINET_MEETING_STATUS");

                new Generic_Async_Get(
                        context_,
                        (AsyncTaskListenerObjectGet) context_,
                        TaskType.CABINET_MEETING_STATUS).
                        execute(object);

            }
        });


        //SetText

        a.recycle();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDisableChildrenTouchEvents;
    }

    public void setDisableChildrenTouchEvents(boolean flag) {
        mDisableChildrenTouchEvents = flag;
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
                                object.setBifurcation("CABINET_MEETING_STATUS");


                                currentTask = new GetAvailability();
                                currentTask.execute(object);

                            } else {
                                DatabaseHandler DB = new DatabaseHandler(context_);
                                Log.e("GET_DEPARTMENTS_VIA", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.CABINET_MEETING_STATUS.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "CABINET_MEETING_STATUS").size()));
                                if (DB.GetAllOfflineDataViaFunction(TaskType.CABINET_MEETING_STATUS.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "CABINET_MEETING_STATUS").size() > 0) {
                                    //Show Events
                                    try {

                                        showCabinetAgenda(DB.GetAllOfflineDataViaFunction(TaskType.CABINET_MEETING_STATUS.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "CABINET_MEETING_STATUS").get(0));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {
                                    CD.showDialogCloseActivity((Activity) context_, Econstants.NO_DATA);
                                }
                            }

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000); //300000   10000
    }

    @Override
    public void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.CABINET_MEETING_STATUS) {

            showCabinetAgendaAlert(result);


        }


    }

    private void showCabinetAgendaAlert(OfflineDataModel result) {


        if (result.getFunctionName().equalsIgnoreCase(TaskType.CABINET_MEETING_STATUS.toString())) {
            AgendaPojo agendaPojo = null;

            Log.e("Result == ", result.getResponse());
            Object json = null;
            try {
                json = new JSONTokener(result.getResponse()).nextValue();
            } catch (JSONException e) {
                Log.e("==Error", e.getLocalizedMessage());
            }
            if (json instanceof JSONObject) {
                try {
                    Log.e("Json Object", "Object");
                    JSONObject object = new JSONObject(result.getResponse());
                    agendaPojo = new AgendaPojo();
                    agendaPojo.setAgendaItemNo(Econstants.decodeBase64(object.optString("AgendaItemNo")));
                    agendaPojo.setAgendaItemType(Econstants.decodeBase64(object.optString("AgendaItemType")));
                    agendaPojo.setDeptName(Econstants.decodeBase64(object.optString("DeptName")));
                    agendaPojo.setFileNo(Econstants.decodeBase64(object.optString("FileNo")));
                    agendaPojo.setSubject(Econstants.decodeBase64(object.optString("Subject")));


                    if (agendaPojo.getAgendaItemType().length() > 0) {
                        Log.e("Agenda", agendaPojo.toString());
                        CD.showDialogActiveAjenda((Activity) context_, agendaPojo);
                        //TODO Agenda Pop Up
                    } else {
                        Log.e("Agenda", agendaPojo.toString());
                        CD.showDialogSuccess((Activity) context_, "No Active Agenda Item Available.");
                    }

                } catch (Exception ex) {
                    Log.e("arrayReports", ex.toString());
                }


            }


        } else {
            CD.showDialog((Activity) context_, result.getResponse());

        }


    }


    class GetAvailability extends AsyncTask<GetDataPojo, String, OfflineDataModel> {

        private ProgressDialog progressDialog;
        private String Server_Value = null;
        String url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //setText("Total Cars In Parking: " + "N/A");
        }

        @Override
        protected OfflineDataModel doInBackground(GetDataPojo... getDataPojo) {


            OfflineDataModel Data_From_Server = null;
            HttpManager http_manager = null;
            try {
                http_manager = new HttpManager();
                if (getDataPojo[0].getTaskType().toString().equalsIgnoreCase(TaskType.CABINET_MEETING_STATUS.toString())) {
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

            if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                //Save the rsult to Database
                DatabaseHandler DH = new DatabaseHandler(context_);
                //Check weather the Hash is Present in the DB or not
                Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.CABINET_MEETING_STATUS.toString(), "CABINET_MEETING_STATUS")));
                if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.CABINET_MEETING_STATUS.toString(), "CABINET_MEETING_STATUS") == 1) {
                    //Update the Earlier Record
                    DH.updateData(result);
                    Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.CABINET_MEETING_STATUS.toString(), "CABINET_MEETING_STATUS") == 0) {
                    DH.addOfflineAccess(result);
                    Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                } else {
                    //DELETE ALL THE RECORDS
                    DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.CABINET_MEETING_STATUS.toString(), "CABINET_MEETING_STATUS");
                    Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.CABINET_MEETING_STATUS.toString(), "CABINET_MEETING_STATUS")));
                    //Add the Latest Record
                    DH.addOfflineAccess(result);
                }

                try {
                    showCabinetAgenda(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
               // CD.showDialogCloseActivity((Activity)context_, "No fddfdfdf");
            }


        }


    }

    private void showCabinetAgenda(OfflineDataModel result) throws JSONException {

        if (result.getFunctionName().equalsIgnoreCase(TaskType.CABINET_MEETING_STATUS.toString())) {
            AgendaPojo agendaPojo = null;

            Log.e("Result == ", result.getResponse());
            Object json = null;
            try {
                json = new JSONTokener(result.getResponse()).nextValue();
            } catch (JSONException e) {
                Log.e("==Error", e.getLocalizedMessage());
            }
            if (json instanceof JSONObject) {
                try {
                    Log.e("Json Object", "Object");
                    JSONObject object = new JSONObject(result.getResponse());
                    agendaPojo = new AgendaPojo();
                    agendaPojo.setAgendaItemNo(Econstants.decodeBase64(object.optString("AgendaItemNo")));
                    agendaPojo.setAgendaItemType(Econstants.decodeBase64(object.optString("AgendaItemType")));
                    agendaPojo.setDeptName(Econstants.decodeBase64(object.optString("DeptName")));
                    agendaPojo.setFileNo(Econstants.decodeBase64(object.optString("FileNo")));
                    agendaPojo.setSubject(Econstants.decodeBase64(object.optString("Subject")));


                    if (agendaPojo.getAgendaItemType().length() > 0) {
                        Log.e("Agenda", agendaPojo.toString());
                        agendanumberTextView.setText("   " + agendaPojo.getAgendaItemNo());
                        ajendanameTextView.setText(agendaPojo.getSubject());
                        designationTextView.setText(agendaPojo.getDeptName());
                        layout.setVisibility(View.VISIBLE);

                        String global_deptId = ((MainActivity2) getContext()).Global_deptId;
                        ((MainActivity2) getContext()).sliderView.setVisibility(View.GONE);
                        Log.e("GlobalDept Id", global_deptId);

//                        if (lengthAgendaListener != null)
//                            lengthAgendaListener.onLengthChanged(this, agendaPojo.getAgendaItemType().length());


                        // setText("Agenda Number:- " + agendaPojo.getAgendaItemNo() + "Agenda Type:- " + agendaPojo.getAgendaItemType() + "Title:- " + agendaPojo.getSubject() + "File No:- " + agendaPojo.getFileNo() + "Subject :- " + agendaPojo.getSubject() + "Department Name :- " + agendaPojo.getDeptName());

                    } else {
                        Log.e("Agenda", agendaPojo.toString());
                        layout.setVisibility(View.INVISIBLE);
                        ((MainActivity2) getContext()).sliderView.setVisibility(View.VISIBLE);

                    }

                } catch (Exception ex) {
                    Log.e("arrayReports", ex.toString());
                    layout.setVisibility(View.INVISIBLE);

                }


            }





        } else {
            CD.showDialog((Activity)context_, result.getResponse());

        }
    }
}