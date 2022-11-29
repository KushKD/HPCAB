package com.dit.himachal.ecabinet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.DatestMemosAdapter;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.Dates;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
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

public class CabinetDecisions extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    private EditText edit_text_search;
    private ListView list;
    CustomDialog CD = new CustomDialog();
    String deptId, param = null;
    List<Dates> dates = null;
    DatestMemosAdapter cabinetMemosAdapter = null;
    SwipeRefreshLayout pullToRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cabinet_decisions);

      //  PreventScreenshot.on(CabinetDecisions.this);

        list = findViewById(R.id.list);
        edit_text_search = findViewById(R.id.edit_text_search);
        pullToRefresh = findViewById(R.id.pullToRefresh);

        try {
            Intent intent = getIntent();
            deptId = intent.getStringExtra("department_id");
            param = intent.getStringExtra("param");
            Log.e("deptId name", deptId);
            Log.e("param name", param);

        } catch (Exception ex) {
            Log.e("deptId name", ex.toString());
        }

        if (param.equalsIgnoreCase("Cabinet_Decisions")) {
            if (AppStatus.getInstance(CabinetDecisions.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.GetCabinetDecisionbyMeetingDates);
                object.setMethordHash(Econstants.encodeBase64(Econstants.GetCabinetDecisionbyMeetingDatesToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_CABINET_DECISIONS_COUNT);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(Preferences.getInstance().user_id);

                parameters.add(Preferences.getInstance().role_id);



                object.setParameters(parameters);


                new Generic_Async_Get(
                        CabinetDecisions.this,
                        CabinetDecisions.this,
                        TaskType.GET_CABINET_DECISIONS_COUNT).
                        execute(object);
            } else {
                CD.showDialog(CabinetDecisions.this, "Please connect to Internet and tr again.");
            }
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dates cabinet_memo_pojo = (Dates) parent.getItemAtPosition(position);
                Intent i = new Intent(CabinetDecisions.this, CabinetMemoListByRoleActivity.class);

                    i.putExtra("department_id", cabinet_memo_pojo.getDepartmentID());
                i.putExtra("param", param);
                Log.e("Param", param);
                i.putExtra("meetingid",cabinet_memo_pojo.getMeetingID());


                startActivity(i);

            }
        });



    }

    @Override
    protected void onStop() {
        // PreventScreenshot.on(CabinetDecisions.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // PreventScreenshot.on(CabinetDecisions.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        // PreventScreenshot.on(CabinetDecisions.this);
        super.onPause();

    }

    @Override
    protected void onResume() {
        //PreventScreenshot.on(CabinetDecisions.this);
        super.onResume();
    }

    @Override
    public void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException {

        Log.e("Result == ", result.getResponse());
        Object json = new JSONTokener(result.getResponse()).nextValue();
        if (json instanceof JSONObject) {
            Log.e("Json Object", "Object");
        } else if (json instanceof JSONArray) {
            Log.e("Json Object", "Object");
            JSONArray arrayReports = new JSONArray(result.getResponse());
            Log.e("arrayReports", arrayReports.toString());
//No Record Found  StatusMessage
            if (arrayReports.length() > 0) {
                //ReportsModelPojo
                JSONObject object = arrayReports.getJSONObject(0);
                //  if (!Econstants.decodeBase64(object.optString("StatusMessage")).equalsIgnoreCase("Incorrect OTP, please enter correct OTP!!.")) {
                dates = new ArrayList<>();

                for (int i = 0; i < arrayReports.length(); i++) {
                    Dates memoPojo = new Dates();
                    JSONObject objectx = arrayReports.getJSONObject(i);


                    memoPojo.setFileNo(Econstants.decodeBase64(objectx.optString("FileNo")));
                    memoPojo.setMeetingDate(Econstants.decodeBase64(objectx.optString("MeetingDate")));
                    memoPojo.setMeetingDateTime(Econstants.decodeBase64(objectx.optString("MeetingDateTime")));
                    memoPojo.setMeetingID(Econstants.decodeBase64(objectx.optString("MeetingID")));
                    memoPojo.setMeetingStatus(Econstants.decodeBase64(objectx.optString("MeetingStatus")));
                    memoPojo.setMeetingTime(Econstants.decodeBase64(objectx.optString("MeetingTime")));
                    memoPojo.setStatus(Econstants.decodeBase64(objectx.optString("Status")));
                    memoPojo.setStatusCode(Econstants.decodeBase64(objectx.optString("StatusCode")));
                    memoPojo.setStatusMessage(Econstants.decodeBase64(objectx.optString("StatusMessage")));
                    memoPojo.setVenueID(Econstants.decodeBase64(objectx.optString("VenueID")));
                    memoPojo.setVenueName(Econstants.decodeBase64(objectx.optString("VenueName")));
                    memoPojo.setTotalCabinetMemos(Econstants.decodeBase64(objectx.optString("TotalCabinetMemos")));
                    memoPojo.setDepartmentID(Econstants.decodeBase64(objectx.optString("DepartmentID")));
                    memoPojo.setDepartmentName(Econstants.decodeBase64(objectx.optString("DepartmentName")));
                    //  memoPojo.setbr(Econstants.decodeBase64(objectx.optString("DepartmentName")));


                    if (!memoPojo.getStatusMessage().equalsIgnoreCase("No Record Found")) {
                        dates.add(memoPojo);
                    }


                }

                if (dates.size() > 0) {
                    cabinetMemosAdapter = new DatestMemosAdapter(CabinetDecisions.this, dates, param);
                    list.setAdapter(cabinetMemosAdapter);
                    list.setTextFilterEnabled(true);
                    edit_text_search.setVisibility(View.VISIBLE);

                    Log.e("DAta", dates.toString());
                } else {
                    CD.showDialogCloseActivity(CabinetDecisions.this, "The Request was Successful. No Records Found.");
                }


//                    } else {
//                        CD.showDialog(CabinetMemoListByRoleActivity.this, Econstants.decodeBase64(object.optString("StatusMessage")));
//                    }


            } else {
                CD.showDialogCloseActivity(CabinetDecisions.this, "The Request was Successful. No Records Found.");
            }
        } else {
            CD.showDialogCloseActivity(CabinetDecisions.this, "Something bad happened. Please connect to the ADMIN team.");
        }


    }
}
