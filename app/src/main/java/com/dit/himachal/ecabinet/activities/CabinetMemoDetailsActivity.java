package com.dit.himachal.ecabinet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.CabinetMemosAdapter;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.GenericAsyncPostObject;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObject;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.CabinetMemoPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.ListAnnexures;
import com.dit.himachal.ecabinet.modal.ListCabinetMemoTrackingHistoryListsPojo;
import com.dit.himachal.ecabinet.modal.ListConsiderationPoints;
import com.dit.himachal.ecabinet.modal.PostDataPojo;
import com.dit.himachal.ecabinet.modal.PostObject;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.dit.himachal.ecabinet.utilities.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class CabinetMemoDetailsActivity extends AppCompatActivity implements AsyncTaskListenerObjectGet, AsyncTaskListenerObject {

    CustomDialog CD = new CustomDialog();
    CabinetMemoPojo cabinetMemoPojo = null;
    CabinetMemoPojo cabinetMemoPojoDetails = null;
    public TextView departmentname,
            approvalchannel,
            filenumber,
            top_head,
            ministerincharge,
            secretaryincharge,
            subjet,
            proposal,
            advisory_department_approval,
            additional_information,
            pointsconsideration, phone;
    private List<ListConsiderationPoints> listConsiderationPoints = null;
    private List<ListAnnexures> listAnnexures = null;
    private List<ListCabinetMemoTrackingHistoryListsPojo> listCabinetMemoTrackingHistoryListsPojos = null;

    EditText otp, remarks;

    Button approve, back, allow, proceed, cancel,attachments, history;
    LinearLayout otp_proceed, proceed_cancel, buttons, remarkslay;
    private String buttonName = null;
    private String param = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_memo_details);

        Intent cabinetData = getIntent();
        cabinetMemoPojo = (CabinetMemoPojo) cabinetData.getSerializableExtra("EVENTS_DETAILS");
        param = cabinetData.getStringExtra("param");
        Log.e("EVENTS_DETAILS", cabinetMemoPojo.toString());
        init();

//TODO
        if(param.equalsIgnoreCase("Forwarded")){
          buttons.setVisibility(View.GONE);
            remarkslay.setVisibility(View.GONE);
        }else if(param.equalsIgnoreCase("Backwarded")){
           buttons.setVisibility(View.GONE);
            remarkslay.setVisibility(View.GONE);
        }else if(param.equalsIgnoreCase("allowedCabinetMemos") || param.equalsIgnoreCase("final")){
            buttons.setVisibility(View.GONE);
            remarkslay.setVisibility(View.GONE);
        }else{
            buttons.setVisibility(View.VISIBLE);
            remarkslay.setVisibility(View.VISIBLE);
        }

        if (Preferences.getInstance().role_id.equalsIgnoreCase("7")) {
            allow.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
            approve.setVisibility(View.GONE);
        } else {
            allow.setVisibility(View.GONE);
            back.setVisibility(View.VISIBLE);
            approve.setVisibility(View.VISIBLE);
        }

        phone.setText(Preferences.getInstance().phone_number);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttons.setVisibility(View.VISIBLE);
                proceed_cancel.setVisibility(View.GONE);
                otp_proceed.setVisibility(View.GONE);
                otp.setText("");

            }
        });
//TODO OTP CHECK USER BASE
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (buttonName.equalsIgnoreCase("forward")) {
                    try {
                        PostObject postObject = new PostObject();
                        postObject.setUserid(Preferences.getInstance().user_id);
                        postObject.setCabinetMemoId(cabinetMemoPojoDetails.getCabinetMemoID());
                        postObject.setRoleid(Preferences.getInstance().role_id);
                        postObject.setDeptId(cabinetMemoPojoDetails.getDeptid());
                        postObject.setRemarks(remarks.getText().toString());
                        postObject.setToken(Econstants.encodeBase64(Econstants.methordForwardCabinetMemoToken + Econstants.seperator + CommonUtils.getTimeStamp()));
                        postObject.setPhone(phone.getText().toString());
                        postObject.setOtp(otp.getText().toString());

                        PostDataPojo postDataPojo = new PostDataPojo();
                        postDataPojo.setParameters(postObject);
                        postDataPojo.setTaskType(TaskType.FORWARD);
                        postDataPojo.setMethord(Econstants.methordForwardCabinetMemo);
                        postDataPojo.setUrl(Econstants.url);

                        //Send Object
                        Log.e("Object", postObject.toString());

                        new GenericAsyncPostObject(
                                CabinetMemoDetailsActivity.this,
                                CabinetMemoDetailsActivity.this,
                                TaskType.FORWARD).
                                execute(postDataPojo);
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                        CD.showDialog(CabinetMemoDetailsActivity.this, "Something Bad happened. Please restart your application.");
                    }
                } else {
                    try {
                        PostObject postObject = new PostObject();
                        postObject.setUserid(Preferences.getInstance().user_id);
                        postObject.setCabinetMemoId(cabinetMemoPojoDetails.getCabinetMemoID());
                        postObject.setRoleid(Preferences.getInstance().role_id);
                        postObject.setDeptId(cabinetMemoPojoDetails.getDeptid());
                        postObject.setRemarks(remarks.getText().toString());
                        postObject.setToken(Econstants.encodeBase64(Econstants.methordsendBackCabinetMemoListsToken + Econstants.seperator + CommonUtils.getTimeStamp()));
                        postObject.setPhone(phone.getText().toString());
                        postObject.setOtp(otp.getText().toString());

                        PostDataPojo postDataPojo = new PostDataPojo();
                        postDataPojo.setParameters(postObject);
                        postDataPojo.setTaskType(TaskType.SEND_BACK);
                        postDataPojo.setMethord(Econstants.methordsendBackCabinetMemoLists);
                        postDataPojo.setUrl(Econstants.url);

                        //Send Object
                        Log.e("Object", postObject.toString());

                        new GenericAsyncPostObject(
                                CabinetMemoDetailsActivity.this,
                                CabinetMemoDetailsActivity.this,
                                TaskType.SEND_BACK).
                                execute(postDataPojo);
                    } catch (Exception ex) {
                        Log.e("Error", ex.toString());
                        CD.showDialog(CabinetMemoDetailsActivity.this, "Something Bad happened. Please restart your application.");
                    }
                }


            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Send Back
                buttonName = "forward";
                if (Preferences.getInstance().role_id.equalsIgnoreCase("4") || Preferences.getInstance().role_id.equalsIgnoreCase("11")) {
                    //check Remarks
                    //  if (!remarks.getText().toString().isEmpty()) {
                    ////OTP Functionality Enable
                    buttons.setVisibility(View.GONE);
                    proceed_cancel.setVisibility(View.VISIBLE);
                    otp_proceed.setVisibility(View.VISIBLE);
                    // if(addPersons.isEmpty()){
                    if (phone.getText().toString().length() == 10) {
                        if (AppStatus.getInstance(CabinetMemoDetailsActivity.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordGetOTP);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordOTPToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.GET_OTP_VIA_MOBILE);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add(phone.getText().toString());
                            parameters.add(Preferences.getInstance().user_id);
                            parameters.add(Preferences.getInstance().role_id);
                            object.setParameters(parameters);
                            new Generic_Async_Get(
                                    CabinetMemoDetailsActivity.this,
                                    CabinetMemoDetailsActivity.this,
                                    TaskType.GET_OTP_VIA_MOBILE).
                                    execute(object);


                        } else {
                            CD.showDialog(CabinetMemoDetailsActivity.this, "Please connect to Internet and try again.");
                        }
                    } else {
                        CD.showDialog(CabinetMemoDetailsActivity.this, "Please contact the department regarding the Phone number.");
                    }

                    // } else {
                    //  CD.showDialog(CabinetMemoDetailsActivity.this, "Please enter Remarks.");
                    // buttons.setVisibility(View.VISIBLE);
                    // proceed_cancel.setVisibility(View.GONE);
                    // otp_proceed.setVisibility(View.GONE);
                    // otp.setText("");


                    //}
                } else {
                 //   if (!remarks.getText().toString().isEmpty()) {
                        try {
                            PostObject postObject = new PostObject();
                            postObject.setUserid(Preferences.getInstance().user_id);
                            postObject.setCabinetMemoId(cabinetMemoPojoDetails.getCabinetMemoID());
                            postObject.setRoleid(Preferences.getInstance().role_id);
                            postObject.setDeptId(cabinetMemoPojoDetails.getDeptid());
                            postObject.setRemarks(remarks.getText().toString());
                            postObject.setToken(Econstants.encodeBase64(Econstants.methordForwardCabinetMemoToken + Econstants.seperator + CommonUtils.getTimeStamp()));
                            postObject.setPhone("");
                            postObject.setOtp("");

                            PostDataPojo postDataPojo = new PostDataPojo();
                            postDataPojo.setParameters(postObject);
                            postDataPojo.setTaskType(TaskType.FORWARD);
                            postDataPojo.setMethord(Econstants.methordForwardCabinetMemo);
                            postDataPojo.setUrl(Econstants.url);

                            //Send Object
                            Log.e("Object", postObject.toString());

                            new GenericAsyncPostObject(
                                    CabinetMemoDetailsActivity.this,
                                    CabinetMemoDetailsActivity.this,
                                    TaskType.FORWARD).
                                    execute(postDataPojo);

                            //Send Object
                            Log.e("Object", postObject.toString());
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                            CD.showDialog(CabinetMemoDetailsActivity.this, "Something Bad happened. Please restart your application.");
                        }
                    } //else
                        {
//                        CD.showDialog(CabinetMemoDetailsActivity.this, "Please enter Remarks.");
//                    }
                }
            }
        });

        allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppStatus.getInstance(CabinetMemoDetailsActivity.this).isOnline()) {
                    GetDataPojo object = new GetDataPojo();
                    object.setUrl(Econstants.url);
                    object.setMethord(Econstants.methordAllowedCabinetMemo);
                    object.setMethordHash(Econstants.encodeBase64(Econstants.methordAllowedCabinetMemoToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                    object.setTaskType(TaskType.ALLOW);
                    object.setTimeStamp(CommonUtils.getTimeStamp());
                    List<String> parameters = new ArrayList<>();
                    parameters.add(cabinetMemoPojoDetails.getDeptid());
                    parameters.add(Preferences.getInstance().user_id);
                    parameters.add(Preferences.getInstance().role_id);
                    object.setParameters(parameters);
                    new Generic_Async_Get(
                            CabinetMemoDetailsActivity.this,
                            CabinetMemoDetailsActivity.this,
                            TaskType.ALLOW).
                            execute(object);


                } else {
                    CD.showDialog(CabinetMemoDetailsActivity.this, "Please connect to Internet and try again.");
                }

            }
        });

        attachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if( cabinetMemoPojoDetails.getListAnnexures_()!=null){
                        Log.e("Attachments", "Death");
                        Log.e("Attachments", cabinetMemoPojoDetails.getListAnnexures_().toString());
                        Intent in = new Intent(CabinetMemoDetailsActivity.this,CabinetAnnexures.class);
                        in.putExtra("data", cabinetMemoPojoDetails);
                        startActivity(in);
                    }else{
                      CD.showDialog(CabinetMemoDetailsActivity.this,"No Data Available.");
                    }

                }catch(Exception ex){
                Log.e(" Attachments", ex.getLocalizedMessage().toString());
            }
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(cabinetMemoPojoDetails.getListCabinetMemoTrackingHistoryLists()!=null){
                    Log.e("Attachments", "Death history");
                    Log.e("Memo History", cabinetMemoPojoDetails.getListCabinetMemoTrackingHistoryLists().toString());
                        Intent in = new Intent(CabinetMemoDetailsActivity.this,CabinetMemoHistory.class);
                        in.putExtra("data", cabinetMemoPojoDetails);
                        startActivity(in);
                    }else{
                        CD.showDialog(CabinetMemoDetailsActivity.this,"No Data Available.");
                    }
                }catch(Exception ex){
                    Log.e("Memo History", ex.getLocalizedMessage().toString());
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Send Back
                buttonName = "back";
                if (Preferences.getInstance().role_id.equalsIgnoreCase("4") || Preferences.getInstance().role_id.equalsIgnoreCase("11")) {
                    //check Remarks
                    if (!remarks.getText().toString().isEmpty()) {
                        ////OTP Functionality Enable
                        buttons.setVisibility(View.GONE);
                        proceed_cancel.setVisibility(View.VISIBLE);
                        otp_proceed.setVisibility(View.VISIBLE);
                        // if(addPersons.isEmpty()){
                        if (phone.getText().toString().length() == 10) {
                            if (AppStatus.getInstance(CabinetMemoDetailsActivity.this).isOnline()) {
                                GetDataPojo object = new GetDataPojo();
                                object.setUrl(Econstants.url);
                                object.setMethord(Econstants.methordGetOTP);
                                object.setMethordHash(Econstants.encodeBase64(Econstants.methordOTPToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                                object.setTaskType(TaskType.GET_OTP_VIA_MOBILE);
                                object.setTimeStamp(CommonUtils.getTimeStamp());
                                List<String> parameters = new ArrayList<>();
                                parameters.add(phone.getText().toString());
                                parameters.add(Preferences.getInstance().user_id);
                                parameters.add(Preferences.getInstance().role_id);
                                object.setParameters(parameters);
                                new Generic_Async_Get(
                                        CabinetMemoDetailsActivity.this,
                                        CabinetMemoDetailsActivity.this,
                                        TaskType.GET_OTP_VIA_MOBILE).
                                        execute(object);


                            } else {
                                CD.showDialog(CabinetMemoDetailsActivity.this, "Please connect to Internet and try again.");
                            }
                        } else {
                            CD.showDialog(CabinetMemoDetailsActivity.this, "Please contact the department regarding the Phone number.");
                        }

                    } else {
                        CD.showDialog(CabinetMemoDetailsActivity.this, "Please enter Remarks.");
                        buttons.setVisibility(View.VISIBLE);
                        proceed_cancel.setVisibility(View.GONE);
                        otp_proceed.setVisibility(View.GONE);
                        otp.setText("");


                    }
                } else {
                    if (!remarks.getText().toString().isEmpty()) {
                        try {
                            PostObject postObject = new PostObject();
                            postObject.setUserid(Preferences.getInstance().user_id);
                            postObject.setCabinetMemoId(cabinetMemoPojoDetails.getCabinetMemoID());
                            postObject.setRoleid(Preferences.getInstance().role_id);
                            postObject.setDeptId(cabinetMemoPojoDetails.getDeptid());
                            postObject.setRemarks(remarks.getText().toString());
                            postObject.setToken(Econstants.encodeBase64(Econstants.methordsendBackCabinetMemoListsToken + Econstants.seperator + CommonUtils.getTimeStamp()));
                            postObject.setPhone("");
                            postObject.setOtp("");

                            PostDataPojo postDataPojo = new PostDataPojo();
                            postDataPojo.setParameters(postObject);
                            postDataPojo.setTaskType(TaskType.SEND_BACK);
                            postDataPojo.setMethord(Econstants.methordsendBackCabinetMemoLists);
                            postDataPojo.setUrl(Econstants.url);

                            //Send Object
                            Log.e("Object", postObject.toString());

                            new GenericAsyncPostObject(
                                    CabinetMemoDetailsActivity.this,
                                    CabinetMemoDetailsActivity.this,
                                    TaskType.SEND_BACK).
                                    execute(postDataPojo);

                            //Send Object
                            Log.e("Object", postObject.toString());
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                            CD.showDialog(CabinetMemoDetailsActivity.this, "Something Bad happened. Please restart your application.");
                        }
                    } else {
                        CD.showDialog(CabinetMemoDetailsActivity.this, "Please enter Remarks.");
                    }
                }
            }
        });


       if(param.equalsIgnoreCase("Current") ){
           if (AppStatus.getInstance(CabinetMemoDetailsActivity.this).isOnline()) {
               GetDataPojo object = new GetDataPojo();
               object.setUrl(Econstants.url);
               object.setMethord(Econstants.methordCabinetMemoDetails);
               object.setMethordHash(Econstants.encodeBase64(Econstants.methordCabinetMemoDetailsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
               object.setTaskType(TaskType.CABINET_MEMOS_DETAILS);
               object.setTimeStamp(CommonUtils.getTimeStamp());
               List<String> parameters = new ArrayList<>();
               parameters.add(cabinetMemoPojo.getCabinetMemoID());
               parameters.add(cabinetMemoPojo.getDeptid());
               parameters.add(Preferences.getInstance().user_id);
               parameters.add(Preferences.getInstance().role_id);
               object.setParameters(parameters);

               Log.e("Departments", Preferences.getInstance().mapped_departments);

               new Generic_Async_Get(
                       CabinetMemoDetailsActivity.this,
                       CabinetMemoDetailsActivity.this,
                       TaskType.CABINET_MEMOS_DETAILS).
                       execute(object);
           } else {
               CD.showDialog(CabinetMemoDetailsActivity.this, "Please connect to Internet and tr again.");
           }
       }else{
           if (AppStatus.getInstance(CabinetMemoDetailsActivity.this).isOnline()) {
               GetDataPojo object = new GetDataPojo();
               object.setUrl(Econstants.url);
               object.setMethord(Econstants.methordCabinetMemoDetailsOther);
               object.setMethordHash(Econstants.encodeBase64(Econstants.methordCabinetMemoDetailsTokenOther + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
               object.setTaskType(TaskType.CABINET_MEMOS_DETAILS);
               object.setTimeStamp(CommonUtils.getTimeStamp());
               List<String> parameters = new ArrayList<>();
               parameters.add(cabinetMemoPojo.getCabinetMemoID());
               parameters.add(cabinetMemoPojo.getDeptid());
               parameters.add(Preferences.getInstance().user_id);
               parameters.add(Preferences.getInstance().role_id);
               object.setParameters(parameters);

               Log.e("Departments", Preferences.getInstance().mapped_departments);

               new Generic_Async_Get(
                       CabinetMemoDetailsActivity.this,
                       CabinetMemoDetailsActivity.this,
                       TaskType.CABINET_MEMOS_DETAILS).
                       execute(object);
           } else {
               CD.showDialog(CabinetMemoDetailsActivity.this, "Please connect to Internet and tr again.");
           }
       }
    }

    private void init() {
        departmentname = findViewById(R.id.departmentname);
        approvalchannel = findViewById(R.id.approvalchannel);
        filenumber = findViewById(R.id.filenumber);
        top_head = findViewById(R.id.top_head);
        ministerincharge = findViewById(R.id.ministerincharge);
        secretaryincharge = findViewById(R.id.secretaryincharge);
        subjet = findViewById(R.id.subjet);
        proposal = findViewById(R.id.proposal);
        advisory_department_approval = findViewById(R.id.advisory_department_approval);
        additional_information = findViewById(R.id.additional_information);
        pointsconsideration = findViewById(R.id.pointsconsideration);
        approve = findViewById(R.id.approve);
        back = findViewById(R.id.back);
        allow = findViewById(R.id.allow);
        otp_proceed = findViewById(R.id.otp_proceed);
        proceed_cancel = findViewById(R.id.proceed_cancel);
        phone = findViewById(R.id.phone);
        otp = findViewById(R.id.otp);
        proceed = findViewById(R.id.proceed);
        cancel = findViewById(R.id.cancel);
        buttons = findViewById(R.id.buttons);
        remarks = findViewById(R.id.remarks);
        remarkslay = findViewById(R.id.remarkslay);
        attachments = findViewById(R.id.attachments);
        history = findViewById(R.id.history);
    }


    @Override
    public void onTaskCompleted(ResponsObject result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.CABINET_MEMOS_DETAILS) {

            Log.e("Result == ", result.getRespnse());
            Object json = new JSONTokener(result.getRespnse()).nextValue();
            if (json instanceof JSONObject) {
                Log.e("Json Object", "Object");
                JSONObject object = new JSONObject(result.respnse);

                Log.e("arrayReports", object.toString());
                cabinetMemoPojoDetails = new CabinetMemoPojo();

                  cabinetMemoPojoDetails.setAdditionalInformation(object.optString("AdditionalInformation"));
                // cabinetMemoPojoDetails.setAgendaItemNo(Econstants.decodeBase64(object.optString("AgendaItemNo")));
                //abinetMemoPojoDetails.setAgendaItemType(Econstants.decodeBase64(object.optString("AgendaItemType")));
                cabinetMemoPojoDetails.setApprovalStatus(Econstants.decodeBase64(object.optString("ApprovalStatus")));
                cabinetMemoPojoDetails.setCabinetMemoID(Econstants.decodeBase64(object.optString("CabinetMemoID")));
                cabinetMemoPojoDetails.setDeptName(Econstants.decodeBase64(object.optString("DeptName")));
                cabinetMemoPojoDetails.setDeptid(Econstants.decodeBase64(object.optString("Deptid")));
                cabinetMemoPojoDetails.setFileNo(Econstants.decodeBase64(object.optString("FileNo")));
                cabinetMemoPojoDetails.setMeetingdate(Econstants.decodeBase64(object.optString("Meetingdate")));
                cabinetMemoPojoDetails.setMemoStatus(Econstants.decodeBase64(object.optString("MemoStatus")));
                cabinetMemoPojoDetails.setMinisterIncharge(Econstants.decodeBase64(object.optString("MinisterIncharge")));
                cabinetMemoPojoDetails.setProposalDetails(object.optString("ProposalDetails"));
                cabinetMemoPojoDetails.setSecIncharge(Econstants.decodeBase64(object.optString("SecIncharge")));
                cabinetMemoPojoDetails.setSubject(Econstants.decodeBase64(object.optString("Subject")));
                // cabinetMemoPojoDetails.setDate(Econstants.decodeBase64(object.optString("Date")));

                //extracting data array from json string
                //TODO KD
                JSONArray ja_data = object.getJSONArray("ListConsiderationPoints");
                int length = ja_data.length();
                if(length>=0){
                    listConsiderationPoints = new ArrayList<>();
                    for (int i = 0; i < ja_data.length(); i++) {
                        JSONObject objectListConsidration = ja_data.getJSONObject(i);
                        ListConsiderationPoints  listConsiderationPoints_  = new ListConsiderationPoints();
                        listConsiderationPoints_.setCabinetMemoID(Econstants.decodeBase64(objectListConsidration.optString("CabinetMemoID")));
                        listConsiderationPoints_.setConsiderationPointID(Econstants.decodeBase64(objectListConsidration.optString("ConsiderationPointID")));
                        listConsiderationPoints_.setConsiderationPtFinalRemarks(Econstants.decodeBase64(objectListConsidration.optString("ConsiderationPtFinalRemarks")));
                        listConsiderationPoints_.setConsiderationPtFinalStatus(Econstants.decodeBase64(objectListConsidration.optString("ConsiderationPtFinalStatus")));
                        listConsiderationPoints_.setConsiderationPtStatus(Econstants.decodeBase64(objectListConsidration.optString("ConsiderationPtStatus")));
                        listConsiderationPoints_.setTitle(Econstants.decodeBase64(objectListConsidration.optString("Title")));
                        listConsiderationPoints_.setPointNumber(Econstants.decodeBase64(objectListConsidration.optString("PointNumber")));
                        listConsiderationPoints_.setStatus(Econstants.decodeBase64(objectListConsidration.optString("Status")));


                        listConsiderationPoints.add(listConsiderationPoints_);
                    }
                    cabinetMemoPojoDetails.setListConsiderationPoints(listConsiderationPoints);
                    Log.e("Considration Points", cabinetMemoPojoDetails.getListConsiderationPoints().toString() );

                }

                //ListAnnexures_  listAnnexures
                JSONArray ListAnnexures_Object = object.getJSONArray("ListAnnexures");
                int length_ListAnnexures_Object = ListAnnexures_Object.length();
                if(length_ListAnnexures_Object>0){
                    listAnnexures = new ArrayList<>();
                    for (int i = 0; i < ListAnnexures_Object.length(); i++) {
                        JSONObject objectListConsidration = ListAnnexures_Object.getJSONObject(i);
                        ListAnnexures  listAnnuxtiresPojo  = new ListAnnexures();
                        listAnnuxtiresPojo.setCabinetMemoID(Econstants.decodeBase64(objectListConsidration.optString("CabinetMemoID")));
                        listAnnuxtiresPojo.setCabinetMemoID(Econstants.decodeBase64(objectListConsidration.optString("AnnexureID")));
                        listAnnuxtiresPojo.setAnnexureID(Econstants.decodeBase64(objectListConsidration.optString("AnnexureName")));
                        listAnnuxtiresPojo.setTitle(Econstants.decodeBase64(objectListConsidration.optString("Title")));
                        listAnnuxtiresPojo.setAttachment(Econstants.decodeBase64(objectListConsidration.optString("Attachment")));


                        listAnnexures.add(listAnnuxtiresPojo);
                    }
                    cabinetMemoPojoDetails.setListAnnexures_(listAnnexures);
                    Log.e("List Annuxter", cabinetMemoPojoDetails.getListAnnexures_().toString() );

                }


                JSONArray ListCabinetMemoTrackingHistoryListsObject = object.getJSONArray("ListCabinetMemoTrackingHistoryLists");
                int ListCabinetMemoTrackingHistoryListsObject_Length = ListCabinetMemoTrackingHistoryListsObject.length();
                if(ListCabinetMemoTrackingHistoryListsObject_Length>0){
                    listCabinetMemoTrackingHistoryListsPojos = new ArrayList<>();
                    for (int i = 0; i < ListCabinetMemoTrackingHistoryListsObject.length(); i++) {
                        JSONObject ox = ListCabinetMemoTrackingHistoryListsObject.getJSONObject(i);
                        ListCabinetMemoTrackingHistoryListsPojo  pojo  = new ListCabinetMemoTrackingHistoryListsPojo();
                        pojo.setAction(Econstants.decodeBase64(ox.optString("Action")));
                        pojo.setDate(Econstants.decodeBase64(ox.optString("Date")));
                        pojo.setRemarks(Econstants.decodeBase64(ox.optString("Remarks")));
//                        pojo.setTitle(Econstants.decodeBase64(objectListConsidration.optString("Title")));
//                        pojo.setAttachment(Econstants.decodeBase64(objectListConsidration.optString("Attachment")));


                        listCabinetMemoTrackingHistoryListsPojos.add(pojo);
                    }
                    cabinetMemoPojoDetails.setListCabinetMemoTrackingHistoryLists(listCabinetMemoTrackingHistoryListsPojos);
                    Log.e("List Cabinet Tracking", cabinetMemoPojoDetails.getListCabinetMemoTrackingHistoryLists().toString() );

                }



                Log.e("cabinetMemoPojoDetails", cabinetMemoPojoDetails.toString());

                departmentname.setText(cabinetMemoPojoDetails.getDeptName());
                approvalchannel.setText(cabinetMemoPojoDetails.getApprovalStatus());
                filenumber.setText(cabinetMemoPojoDetails.getFileNo());
                // top_head.setText(cabinetMemoPojoDetails);
                ministerincharge.setText(cabinetMemoPojoDetails.getMinisterIncharge());
                secretaryincharge.setText(cabinetMemoPojoDetails.getSecIncharge());
                subjet.setText(cabinetMemoPojoDetails.getSubject());
                proposal.setText(Html.fromHtml(cabinetMemoPojoDetails.getProposalDetails()));

                if(cabinetMemoPojoDetails.getListConsiderationPoints().size()>0){
                    StringBuilder SB = new StringBuilder();
                    for(int i=0; i<cabinetMemoPojoDetails.getListConsiderationPoints().size();i++){
                        SB.append(cabinetMemoPojoDetails.getListConsiderationPoints().get(i).getPointNumber()+"). ");
                        SB.append(cabinetMemoPojoDetails.getListConsiderationPoints().get(i).getTitle());
                        SB.append("\n");
                    }
                    pointsconsideration.setText(SB.toString());
                }else{
                    pointsconsideration.setText("NA");
                }


                //  advisory_department_approval.setText(cabinetMemoPojoDetails.getA);
                 additional_information.setText(Html.fromHtml(cabinetMemoPojoDetails.getAdditionalInformation()));
                // pointsconsideration.setText("");
            } else if (json instanceof JSONArray) Log.e("Json Array", "Array");


        } else if (taskType == TaskType.GET_OTP_VIA_MOBILE) {
            //Check Weather the String is Json array or Json Object
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getRespnse());
                Object json = new JSONTokener(result.getRespnse()).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                    JSONObject object = new JSONObject(result.getRespnse());
                    Log.e("arrayReports", object.toString());

                    CD.showDialog(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(object.getString("StatusMessage")));


                }


            } else {
                CD.showDialog(CabinetMemoDetailsActivity.this, result.getRespnse());
            }
        } else if (taskType == TaskType.SEND_BACK) {
            //Check Weather the String is Json array or Json Object
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getRespnse());
                CD.showDialogCloseActivity(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(result.getRespnse()));


            } else {
                CD.showDialog(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(result.getRespnse()));
            }
        } else if (taskType == TaskType.FORWARD) {
            //Check Weather the String is Json array or Json Object
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getRespnse());
                CD.showDialogCloseActivity(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(result.getRespnse()));


            } else {
                CD.showDialog(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(result.getRespnse()));
            }
        } else if(taskType == TaskType.ALLOW){
            Log.e("Result", result.getRespnse());
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getRespnse());
                CD.showDialogCloseActivity(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(result.getRespnse()));


            } else {
                CD.showDialog(CabinetMemoDetailsActivity.this, Econstants.decodeBase64(result.getRespnse()));
            }
        }


    }
}