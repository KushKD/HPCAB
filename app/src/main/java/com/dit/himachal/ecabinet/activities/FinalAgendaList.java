package com.dit.himachal.ecabinet.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.CabinetMemosAdapter;
import com.dit.himachal.ecabinet.adapter.DepartmentsAdapter;
import com.dit.himachal.ecabinet.adapter.MeetingDatesAdapter;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.CabinetMemoPojo;
import com.dit.himachal.ecabinet.modal.DepartmentsPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.MeetingDatesPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.dit.himachal.ecabinet.utilities.Preferences;
import com.dit.himachal.ecabinet.utilities.PreventScreenshot;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class FinalAgendaList extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    private EditText edit_text_search;
    private ListView list;
    CustomDialog CD = new CustomDialog();
    String deptId, param = null;
    List<CabinetMemoPojo> cabinetMemoPojoList = null;
    MeetingDatesAdapter cabinetMemosAdapter = null;
    CabinetMemosAdapter adapter;
    SwipeRefreshLayout pullToRefresh;
    SearchableSpinner dates;
    List<MeetingDatesPojo> meetingDates;
    DepartmentsAdapter departmentsAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_agenda_list);
        PreventScreenshot.on(FinalAgendaList.this);


        list = findViewById(R.id.list);
        edit_text_search = findViewById(R.id.edit_text_search);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        dates = findViewById(R.id.dates);

        try {
            Intent intent = getIntent();
            deptId = intent.getStringExtra("department_id");

            Log.e("deptId name", deptId);

            //Get Departments departments
            if (AppStatus.getInstance(FinalAgendaList.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordPublishedMeetingDatesListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordPublishedMeetingDatesListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PUBLISHED_MEETING_ID_BY_ROLE);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(Preferences.getInstance().role_id);
                object.setParameters(parameters);
                new Generic_Async_Get(
                        FinalAgendaList.this,
                        FinalAgendaList.this,
                        TaskType.GET_PUBLISHED_MEETING_ID_BY_ROLE).
                        execute(object);


            } else {
                CD.showDialog(FinalAgendaList.this, "Please connect to Internet and try again.");
            }

            dates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                    try {
                        MeetingDatesPojo roles = cabinetMemosAdapter.getItem(position);

                        Log.e("Meeting ID", roles.getMeetinID());

                        if (AppStatus.getInstance(FinalAgendaList.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordFinalMeetingAgendaList);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordFinalMeetingAgendaListToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.FINAL_MEETING_AGENDA_LIST);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add(roles.getMeetinID());
                            parameters.add(Preferences.getInstance().role_id);

                            object.setParameters(parameters);

                            //  Log.e("Departments", Preferences.getInstance().mapped_departments);

                            new Generic_Async_Get(
                                    FinalAgendaList.this,
                                    FinalAgendaList.this,
                                    TaskType.FINAL_MEETING_AGENDA_LIST).
                                    execute(object);
                        } else {
                            CD.showDialog(FinalAgendaList.this, "Please connect to Internet and tr again.");
                        }


                    } catch (Exception ex) {
                        CD.showDialog(FinalAgendaList.this, ex.getLocalizedMessage());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapter) {
                }

            });

            list.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int topRowVerticalPosition = (list == null || list.getChildCount() == 0) ? 0 : list.getChildAt(0).getTop();
                    pullToRefresh.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
                }
            });

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    CabinetMemoPojo cabinet_memo_pojo = (CabinetMemoPojo) parent.getItemAtPosition(position);
                    Intent i = new Intent(FinalAgendaList.this, CabinetMemoDetailsActivity.class);
                    i.putExtra("EVENTS_DETAILS", cabinet_memo_pojo);

                        i.putExtra("param", "final");

                    startActivity(i);

                }
            });


        } catch (Exception ex) {
            Log.e("deptId name", ex.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreventScreenshot.on(FinalAgendaList.this);

    }




    @Override
    protected void onStop() {
        PreventScreenshot.on(FinalAgendaList.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        PreventScreenshot.on(FinalAgendaList.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        PreventScreenshot.on(FinalAgendaList.this);
        super.onPause();

    }

    @Override
    public void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.GET_PUBLISHED_MEETING_ID_BY_ROLE) {

            Log.e("Result == ", result.getResponse());
            Object json = new JSONTokener(result.getResponse()).nextValue();
            if (json instanceof JSONObject) {
                Log.e("Json Object", "Object");
            } else if (json instanceof JSONArray) {
                Log.e("Json Object", "Object");
                JSONArray arrayReports = new JSONArray(result.getResponse());
                Log.e("arrayReports", arrayReports.toString());
                if (arrayReports.length() > 0) {
                    //ReportsModelPojo
                    JSONObject object = arrayReports.getJSONObject(0);
                    //  if (!Econstants.decodeBase64(object.optString("StatusMessage")).equalsIgnoreCase("Incorrect OTP, please enter correct OTP!!.")) {
                    meetingDates = new ArrayList<>();

                    for (int i = 0; i < arrayReports.length(); i++) {
                        MeetingDatesPojo memoPojo = new MeetingDatesPojo();
                        JSONObject objectx = arrayReports.getJSONObject(i);


                        memoPojo.setFileNo(Econstants.decodeBase64(objectx.optString("FileNo")));
                        memoPojo.setMeetingDate(Econstants.decodeBase64(objectx.optString("MeetingDate")));
                        memoPojo.setMeetingDateTime(Econstants.decodeBase64(objectx.optString("MeetingDateTime")));
                        memoPojo.setMeetinID(Econstants.decodeBase64(objectx.optString("MeetingID")));
                        memoPojo.setMeetingStatus(Econstants.decodeBase64(objectx.optString("MeetingStatus")));
                        memoPojo.setMeetingTime(Econstants.decodeBase64(objectx.optString("MeetingTime")));
                        memoPojo.setVenueID(Econstants.decodeBase64(objectx.optString("VenueID")));
                        memoPojo.setVenueName(Econstants.decodeBase64(objectx.optString("VenueName")));

                        // if (!memoPojo.getStatusMessage().equalsIgnoreCase("No Record Found")) {
                        meetingDates.add(memoPojo);
                        //}


                    }

                    if (meetingDates.size() > 0) {
                        cabinetMemosAdapter = new MeetingDatesAdapter(FinalAgendaList.this, android.R.layout.simple_spinner_item, meetingDates);
                        dates.setAdapter(cabinetMemosAdapter);
                        // list.setTextFilterEnabled(true);


                        Log.e("DAta", meetingDates.toString());
                    } else {
                        CD.showDialog(FinalAgendaList.this, "The Request was Successful. No Records Found.");
                        cabinetMemosAdapter = null;
                        dates.setAdapter(cabinetMemosAdapter);
                    }


                } else {
                    CD.showDialog(FinalAgendaList.this, "The Request was Successful. No Records Found.");
                    cabinetMemosAdapter = null;
                    dates.setAdapter(cabinetMemosAdapter);
                }
            }


        } else if (taskType == TaskType.FINAL_MEETING_AGENDA_LIST) {

            Log.e("Result == ", result.getResponse());
            Object json = new JSONTokener(result.getResponse()).nextValue();
            if (json instanceof JSONObject) {
                Log.e("Json Object", "Object");
            } else if (json instanceof JSONArray) {
                Log.e("Json Object", "Object");
                JSONArray arrayReports = new JSONArray(result.getResponse());
                Log.e("arrayReports", arrayReports.toString());
                if (arrayReports.length() > 0) {
                    //ReportsModelPojo
                    JSONObject object = arrayReports.getJSONObject(0);
                    //  if (!Econstants.decodeBase64(object.optString("StatusMessage")).equalsIgnoreCase("Incorrect OTP, please enter correct OTP!!.")) {
                    cabinetMemoPojoList = new ArrayList<>();

                    for (int i = 0; i < arrayReports.length(); i++) {
                        CabinetMemoPojo memoPojo = new CabinetMemoPojo();
                        JSONObject objectx = arrayReports.getJSONObject(i);


                        memoPojo.setAdditionalInformation(Econstants.decodeBase64(objectx.optString("AdditionalInformation")));
                        memoPojo.setAgendaItemNo(Econstants.decodeBase64(objectx.optString("AgendaItemNo")));
                        memoPojo.setAgendaItemType(Econstants.decodeBase64(objectx.optString("AgendaItemType")));
                        memoPojo.setApprovalStatus(Econstants.decodeBase64(objectx.optString("ApprovalStatus")));
                        memoPojo.setCabinetMemoID(Econstants.decodeBase64(objectx.optString("CabinetMemoID")));
                        memoPojo.setDeptName(Econstants.decodeBase64(objectx.optString("DeptName")));
                        memoPojo.setDeptid(Econstants.decodeBase64(objectx.optString("Deptid")));
                        memoPojo.setFileNo(Econstants.decodeBase64(objectx.optString("FileNo")));
                        memoPojo.setListAdvisoryDepartments(Econstants.decodeBase64(objectx.optString("ListAdvisoryDepartments")));
                        //memoPojo.setListAnnexures(Econstants.decodeBase64(objectx.optString("ListAnnexures")));
                        memoPojo.setMeetingdate(Econstants.decodeBase64(objectx.optString("Meetingdate")));
                        memoPojo.setMemoStatus(Econstants.decodeBase64(objectx.optString("MemoStatus")));
                        memoPojo.setMinisterIncharge(Econstants.decodeBase64(objectx.optString("MinisterIncharge")));
                        memoPojo.setProposalDetails(Econstants.decodeBase64(objectx.optString("ProposalDetails")));
                        memoPojo.setSecIncharge(Econstants.decodeBase64(objectx.optString("SecIncharge")));
                        memoPojo.setSubject(Econstants.decodeBase64(objectx.optString("Subject")));
                        memoPojo.setStatusMessage(Econstants.decodeBase64(objectx.optString("StatusMessage")));

                        if (!memoPojo.getStatusMessage().equalsIgnoreCase("No Record Found")) {
                            cabinetMemoPojoList.add(memoPojo);
                        }


                    }

                    if (cabinetMemoPojoList.size() > 0) {
                        adapter = new CabinetMemosAdapter(FinalAgendaList.this, cabinetMemoPojoList, "final");
                        list.setAdapter(adapter);
                        // list.setTextFilterEnabled(true);


                        Log.e("DAta", cabinetMemoPojoList.toString());
                    } else {
                        CD.showDialog(FinalAgendaList.this, "The Request was Successful. No Records Found.");
                        adapter = null;
                        list.setAdapter(adapter);
                    }


                } else {
                    CD.showDialog(FinalAgendaList.this, "The Request was Successful. No Records Found.");
                    cabinetMemosAdapter = null;
                    list.setAdapter(cabinetMemosAdapter);
                }
            }
        }
    }
}