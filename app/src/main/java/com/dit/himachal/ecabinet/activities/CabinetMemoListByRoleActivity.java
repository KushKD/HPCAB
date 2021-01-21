package com.dit.himachal.ecabinet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.CabinetMemosAdapter;
import com.dit.himachal.ecabinet.databases.DatabaseHandler;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.CabinetMemoPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.dit.himachal.ecabinet.utilities.Preferences;
import com.dit.himachal.ecabinet.utilities.PreventScreenshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class CabinetMemoListByRoleActivity extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    private EditText edit_text_search;
    private ListView list;
    CustomDialog CD = new CustomDialog();
    String deptId, param, meetingId = null;
    List<CabinetMemoPojo> cabinetMemoPojoList = null ;
    CabinetMemosAdapter cabinetMemosAdapter = null;
    SwipeRefreshLayout pullToRefresh;
    TextView  header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_memo_list_by_role);

        PreventScreenshot.on(CabinetMemoListByRoleActivity.this);

        list = findViewById(R.id.list);
        edit_text_search = findViewById(R.id.edit_text_search);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        header = findViewById(R.id.heading);


        try {
            Intent intent = getIntent();
            deptId = intent.getStringExtra("department_id");
            param = intent.getStringExtra("param");
            Log.e("deptId name", deptId);
            Log.e("param name", param);

        } catch (Exception ex) {
            Log.e("deptId name", ex.toString());
        }

        if (param.equalsIgnoreCase("Forwarded")) {
            header.setText("Forwarded Cabinet Memos");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordForwardedCabinetMemoListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordForwardedCabinetMemoListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);
                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();

            }
        } else  if (param.equalsIgnoreCase("Cabinet_Decisions")) {
            header.setText("Cabinet Decisions");
            Intent intent = getIntent();
            meetingId = intent.getStringExtra("meetingid");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.CabinetDecisionlists);
                object.setMethordHash(Econstants.encodeBase64(Econstants.CabinetDecisionlistsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
               // parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);

                parameters.add(meetingId);
                parameters.add(Preferences.getInstance().branched_mapped);

                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }
        }



        else if (param.equalsIgnoreCase("Backwarded")) {
            header.setText("Sent Back Cabinet Memos");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordSentBackCabinetMemoListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordSentBackCabinetMemoListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);

                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }


        }
        else if (param.equalsIgnoreCase("PlacedInCabinet")) {
            header.setText("Cabinet Memos Placed in Cabinet");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.PlaceinCabinetagendalists);
                object.setMethordHash(Econstants.encodeBase64(Econstants.PlaceinCabinetagendalistsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);
                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }

        }



        else if (param.equalsIgnoreCase("allowedCabinetMemos")) {
            header.setText("Cabinet Memos Allowed");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordAllowedCabinetMemo);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordAllowedCabinetMemoToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_ALLOWED_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                // parameters.add(Preferences.getInstance().mapped_departments);

                object.setParameters(parameters);
                object.setBifurcation(param);

                //  Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_ALLOWED_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }
        } else {
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordCabinetMemoListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordCabinetMemoListByToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);
                object.setParameters(parameters);
                object.setBifurcation(param);
                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }
        }


        edit_text_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cabinetMemosAdapter.getFilter().filter(s.toString(), new Filter.FilterListener() {
                    public void onFilterComplete(int count) {
                        if (count == 0) {
                            list.setVisibility(View.GONE);
                        } else {
                            list.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                // PSDashboardDetailsManufacturing.this.adapter.getFilter().filter(s);


            }
        });


        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (param.equalsIgnoreCase("Forwarded")) {
                    header.setText("Forwarded Cabinet Memos");
                    if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordForwardedCabinetMemoListByRole);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordForwardedCabinetMemoListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(deptId);
                        parameters.add(Preferences.getInstance().user_id);
                        parameters.add(Preferences.getInstance().role_id);
                        parameters.add(Preferences.getInstance().mapped_departments);
                        parameters.add(Preferences.getInstance().branched_mapped);
                        object.setParameters(parameters);
                        object.setBifurcation(param);

                        Log.e("Departments", Preferences.getInstance().mapped_departments);

                        new Generic_Async_Get(
                                CabinetMemoListByRoleActivity.this,
                                CabinetMemoListByRoleActivity.this,
                                TaskType.GET_PENDING_MEMO_LIST_CABINET).
                                execute(object);
                    } else {
                        DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                            //Show Events
                            try {

                                showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();

                    }
                } else  if (param.equalsIgnoreCase("Cabinet_Decisions")) {
                    header.setText("Cabinet Decisions");
                    Intent intent = getIntent();
                    meetingId = intent.getStringExtra("meetingid");
                    if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.CabinetDecisionlists);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.CabinetDecisionlistsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        // parameters.add(deptId);
                        parameters.add(Preferences.getInstance().user_id);
                        parameters.add(Preferences.getInstance().role_id);
                        parameters.add(Preferences.getInstance().mapped_departments);

                        parameters.add(meetingId);
                        parameters.add(Preferences.getInstance().branched_mapped);

                        object.setParameters(parameters);
                        object.setBifurcation(param);

                        Log.e("Departments", Preferences.getInstance().mapped_departments);

                        new Generic_Async_Get(
                                CabinetMemoListByRoleActivity.this,
                                CabinetMemoListByRoleActivity.this,
                                TaskType.GET_PENDING_MEMO_LIST_CABINET).
                                execute(object);
                    } else {
                        DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                            //Show Events
                            try {

                                showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
                    }
                }



                else if (param.equalsIgnoreCase("Backwarded")) {
                    header.setText("Sent Back Cabinet Memos");
                    if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordSentBackCabinetMemoListByRole);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordSentBackCabinetMemoListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(deptId);
                        parameters.add(Preferences.getInstance().user_id);
                        parameters.add(Preferences.getInstance().role_id);
                        parameters.add(Preferences.getInstance().mapped_departments);
                        parameters.add(Preferences.getInstance().branched_mapped);

                        object.setParameters(parameters);
                        object.setBifurcation(param);

                        Log.e("Departments", Preferences.getInstance().mapped_departments);

                        new Generic_Async_Get(
                                CabinetMemoListByRoleActivity.this,
                                CabinetMemoListByRoleActivity.this,
                                TaskType.GET_PENDING_MEMO_LIST_CABINET).
                                execute(object);
                    } else {
                        DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                            //Show Events
                            try {

                                showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
                    }


                }
                else if (param.equalsIgnoreCase("PlacedInCabinet")) {
                    header.setText("Cabinet Memos Placed in Cabinet");
                    if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.PlaceinCabinetagendalists);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.PlaceinCabinetagendalistsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(deptId);
                        parameters.add(Preferences.getInstance().user_id);
                        parameters.add(Preferences.getInstance().role_id);
                        parameters.add(Preferences.getInstance().mapped_departments);
                        parameters.add(Preferences.getInstance().branched_mapped);
                        object.setParameters(parameters);
                        object.setBifurcation(param);

                        Log.e("Departments", Preferences.getInstance().mapped_departments);

                        new Generic_Async_Get(
                                CabinetMemoListByRoleActivity.this,
                                CabinetMemoListByRoleActivity.this,
                                TaskType.GET_PENDING_MEMO_LIST_CABINET).
                                execute(object);
                    } else {
                        DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                            //Show Events
                            try {

                                showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
                    }

                }



                else if (param.equalsIgnoreCase("allowedCabinetMemos")) {
                    header.setText("Cabinet Memos Allowed");
                    if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordAllowedCabinetMemo);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordAllowedCabinetMemoToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_ALLOWED_MEMO_LIST_CABINET);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(deptId);
                        parameters.add(Preferences.getInstance().user_id);
                        parameters.add(Preferences.getInstance().role_id);
                        // parameters.add(Preferences.getInstance().mapped_departments);

                        object.setParameters(parameters);
                        object.setBifurcation(param);

                        //  Log.e("Departments", Preferences.getInstance().mapped_departments);

                        new Generic_Async_Get(
                                CabinetMemoListByRoleActivity.this,
                                CabinetMemoListByRoleActivity.this,
                                TaskType.GET_ALLOWED_MEMO_LIST_CABINET).
                                execute(object);
                    } else {
                        DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                            //Show Events
                            try {

                                showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordCabinetMemoListByRole);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordCabinetMemoListByToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(deptId);
                        parameters.add(Preferences.getInstance().user_id);
                        parameters.add(Preferences.getInstance().role_id);
                        parameters.add(Preferences.getInstance().mapped_departments);
                        parameters.add(Preferences.getInstance().branched_mapped);
                        object.setParameters(parameters);
                        object.setBifurcation(param);
                        Log.e("Departments", Preferences.getInstance().mapped_departments);

                        new Generic_Async_Get(
                                CabinetMemoListByRoleActivity.this,
                                CabinetMemoListByRoleActivity.this,
                                TaskType.GET_PENDING_MEMO_LIST_CABINET).
                                execute(object);
                    } else {
                        DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                            //Show Events
                            try {

                                showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
                    }
                }


                pullToRefresh.setRefreshing(false);

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
                Intent i = new Intent(CabinetMemoListByRoleActivity.this, CabinetMemoDetailsActivity.class);
                i.putExtra("EVENTS_DETAILS", cabinet_memo_pojo);
                if (param.equalsIgnoreCase("Forwarded")) {
                    i.putExtra("param", param);
                } else if (param.equalsIgnoreCase("Backwarded")) {
                    i.putExtra("param", param);
                } else if (param.equalsIgnoreCase("allowedCabinetMemos")) {
                    i.putExtra("param", param);
                }else if (param.equalsIgnoreCase("PlacedInCabinet")) {
                    i.putExtra("param", param);
                } else if (param.equalsIgnoreCase("Cabinet_Decisions")) {
                    i.putExtra("param", param);
                } else {
                    i.putExtra("param", param);
                }
                startActivity(i);

            }
        });
    }

    private void showData(OfflineDataModel forwarded) throws JSONException {

        if (forwarded.getFunctionName().equalsIgnoreCase(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString())) {

            Log.e("Result == ", forwarded.getResponse());
            Object json = new JSONTokener(forwarded.getResponse()).nextValue();
            if (json instanceof JSONObject) {
                Log.e("Json Object", "Object");
            } else if (json instanceof JSONArray) {
                Log.e("Json Object", "Object");
                JSONArray arrayReports = new JSONArray(forwarded.getResponse());
                Log.e("arrayReports", arrayReports.toString());
                //No Record Found  StatusMessage
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
                        memoPojo.setBranchId(Econstants.decodeBase64(objectx.optString("BranchID")));
                        Log.e("BranchID:-", memoPojo.getBranchId());

                        if (!memoPojo.getStatusMessage().equalsIgnoreCase("No Record Found")) {
                            cabinetMemoPojoList.add(memoPojo);
                        }


                    }

                    if (cabinetMemoPojoList.size() > 0) {
                        cabinetMemosAdapter = new CabinetMemosAdapter(CabinetMemoListByRoleActivity.this, cabinetMemoPojoList, param);
                        list.setAdapter(cabinetMemosAdapter);
                        list.setTextFilterEnabled(true);
                        edit_text_search.setVisibility(View.VISIBLE);

                        Log.e("Data", cabinetMemoPojoList.toString());
                    } else {
                        CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, "The Request was Successful. No Records Found.");
                    }


                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, "The Request was Successful. No Records Found.");
                }
            }


        }
    }

    @Override
    protected void onResume() {
        PreventScreenshot.on(CabinetMemoListByRoleActivity.this);
        super.onResume();

        if (param.equalsIgnoreCase("Forwarded")) {
            header.setText("Forwarded Cabinet Memos");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordForwardedCabinetMemoListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordForwardedCabinetMemoListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);
                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();

            }
        } else  if (param.equalsIgnoreCase("Cabinet_Decisions")) {
            header.setText("Cabinet Decisions");
            Intent intent = getIntent();
            meetingId = intent.getStringExtra("meetingid");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.CabinetDecisionlists);
                object.setMethordHash(Econstants.encodeBase64(Econstants.CabinetDecisionlistsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                // parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);

                parameters.add(meetingId);
                parameters.add(Preferences.getInstance().branched_mapped);

                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }
        }



        else if (param.equalsIgnoreCase("Backwarded")) {
            header.setText("Sent Back Cabinet Memos");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordSentBackCabinetMemoListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordSentBackCabinetMemoListByRoleToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);

                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }


        }
        else if (param.equalsIgnoreCase("PlacedInCabinet")) {
            header.setText("Cabinet Memos Placed in Cabinet");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.PlaceinCabinetagendalists);
                object.setMethordHash(Econstants.encodeBase64(Econstants.PlaceinCabinetagendalistsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);
                object.setParameters(parameters);
                object.setBifurcation(param);

                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }

        }



        else if (param.equalsIgnoreCase("allowedCabinetMemos")) {
            header.setText("Cabinet Memos Allowed");
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordAllowedCabinetMemo);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordAllowedCabinetMemoToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_ALLOWED_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                // parameters.add(Preferences.getInstance().mapped_departments);

                object.setParameters(parameters);
                object.setBifurcation(param);

                //  Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_ALLOWED_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }
        } else {
            if (AppStatus.getInstance(CabinetMemoListByRoleActivity.this).isOnline()) {
                GetDataPojo object = new GetDataPojo();
                object.setUrl(Econstants.url);
                object.setMethord(Econstants.methordCabinetMemoListByRole);
                object.setMethordHash(Econstants.encodeBase64(Econstants.methordCabinetMemoListByToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                object.setTaskType(TaskType.GET_PENDING_MEMO_LIST_CABINET);
                object.setTimeStamp(CommonUtils.getTimeStamp());
                List<String> parameters = new ArrayList<>();
                parameters.add(deptId);
                parameters.add(Preferences.getInstance().user_id);
                parameters.add(Preferences.getInstance().role_id);
                parameters.add(Preferences.getInstance().mapped_departments);
                parameters.add(Preferences.getInstance().branched_mapped);
                object.setParameters(parameters);
                object.setBifurcation(param);
                Log.e("Departments", Preferences.getInstance().mapped_departments);

                new Generic_Async_Get(
                        CabinetMemoListByRoleActivity.this,
                        CabinetMemoListByRoleActivity.this,
                        TaskType.GET_PENDING_MEMO_LIST_CABINET).
                        execute(object);
            } else {
                DatabaseHandler DB = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size()));
                if (DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).size() > 0) {
                    //Show Events
                    try {

                        showData(DB.GetAllOfflineDataViaFunction(TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id,   param).get(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, Econstants.NO_DATA);
                }
                Toast.makeText(getApplicationContext(),"Application running in Offline Mode.",Toast.LENGTH_LONG).show();
            }
        }

    }





    @Override
    protected void onStop() {
        PreventScreenshot.on(CabinetMemoListByRoleActivity.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        PreventScreenshot.on(CabinetMemoListByRoleActivity.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        PreventScreenshot.on(CabinetMemoListByRoleActivity.this);
        super.onPause();

    }

    @Override
    public void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException {


        if (taskType == TaskType.GET_PENDING_MEMO_LIST_CABINET) {

            if(result.getBifurcation().equalsIgnoreCase("Forwarded")){
                if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                    //Save the rsult to Database
                    DatabaseHandler DH = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                    //Check weather the Hash is Present in the DB or not
                    Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Forwarded")));
                    if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Forwarded") == 1) {
                        //Update the Earlier Record
                        DH.updateData(result);
                        Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                    } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Forwarded") == 0) {
                        DH.addOfflineAccess(result);
                        Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                    } else {
                        //DELETE ALL THE RECORDS
                        DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Forwarded");
                        Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Forwarded")));
                        //Add the Latest Record
                        DH.addOfflineAccess(result);
                    }

                    showData(result);
                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, result.getResponse());
                }

            } else if(result.getBifurcation().equalsIgnoreCase("Cabinet_Decisions")){
                if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                    //Save the rsult to Database
                    DatabaseHandler DH = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                    //Check weather the Hash is Present in the DB or not
                    Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Cabinet_Decisions")));
                    if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Cabinet_Decisions") == 1) {
                        //Update the Earlier Record
                        DH.updateData(result);
                        Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                    } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Cabinet_Decisions") == 0) {
                        DH.addOfflineAccess(result);
                        Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                    } else {
                        //DELETE ALL THE RECORDS
                        DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Cabinet_Decisions");
                        Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Cabinet_Decisions")));
                        //Add the Latest Record
                        DH.addOfflineAccess(result);
                    }

                    showData(result);
                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, result.getResponse());
                }

            }  else if(result.getBifurcation().equalsIgnoreCase("Backwarded")){
                if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                    //Save the rsult to Database
                    DatabaseHandler DH = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                    //Check weather the Hash is Present in the DB or not
                    Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Backwarded")));
                    if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Backwarded") == 1) {
                        //Update the Earlier Record
                        DH.updateData(result);
                        Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                    } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Backwarded") == 0) {
                        DH.addOfflineAccess(result);
                        Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                    } else {
                        //DELETE ALL THE RECORDS
                        DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Backwarded");
                        Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "Backwarded")));
                        //Add the Latest Record
                        DH.addOfflineAccess(result);
                    }

                    showData(result);
                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, result.getResponse());
                }

            }else if(result.getBifurcation().equalsIgnoreCase("PlacedInCabinet")){
                if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                    //Save the rsult to Database
                    DatabaseHandler DH = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                    //Check weather the Hash is Present in the DB or not
                    Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "PlacedInCabinet")));
                    if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "PlacedInCabinet") == 1) {
                        //Update the Earlier Record
                        DH.updateData(result);
                        Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                    } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "PlacedInCabinet") == 0) {
                        DH.addOfflineAccess(result);
                        Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                    } else {
                        //DELETE ALL THE RECORDS
                        DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "PlacedInCabinet");
                        Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "PlacedInCabinet")));
                        //Add the Latest Record
                        DH.addOfflineAccess(result);
                    }

                    showData(result);
                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, result.getResponse());
                }

            }else if(result.getBifurcation().equalsIgnoreCase("allowedCabinetMemos")){
                if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                    //Save the rsult to Database
                    DatabaseHandler DH = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                    //Check weather the Hash is Present in the DB or not
                    Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "allowedCabinetMemos")));
                    if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "allowedCabinetMemos") == 1) {
                        //Update the Earlier Record
                        DH.updateData(result);
                        Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                    } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "allowedCabinetMemos") == 0) {
                        DH.addOfflineAccess(result);
                        Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                    } else {
                        //DELETE ALL THE RECORDS
                        DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "allowedCabinetMemos");
                        Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "allowedCabinetMemos")));
                        //Add the Latest Record
                        DH.addOfflineAccess(result);
                    }

                    showData(result);
                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, result.getResponse());
                }

            }else {
                if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                    //Save the rsult to Database
                    DatabaseHandler DH = new DatabaseHandler(CabinetMemoListByRoleActivity.this);
                    //Check weather the Hash is Present in the DB or not
                    Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "else")));
                    if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "else") == 1) {
                        //Update the Earlier Record
                        DH.updateData(result);
                        Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                    } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "else") == 0) {
                        DH.addOfflineAccess(result);
                        Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                    } else {
                        //DELETE ALL THE RECORDS
                        DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "else");
                        Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_PENDING_MEMO_LIST_CABINET.toString(), "else")));
                        //Add the Latest Record
                        DH.addOfflineAccess(result);
                    }

                    showData(result);
                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, result.getResponse());
                }
            }
        } else if (taskType == TaskType.GET_ALLOWED_MEMO_LIST_CABINET) {

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
                        cabinetMemosAdapter = new CabinetMemosAdapter(CabinetMemoListByRoleActivity.this, cabinetMemoPojoList, param);
                        list.setAdapter(cabinetMemosAdapter);
                        list.setTextFilterEnabled(true);
                        edit_text_search.setVisibility(View.VISIBLE);

                        Log.e("DAta", cabinetMemoPojoList.toString());
                    } else {
                        CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, "The Request was Successful. No Records Found.");
                    }


//                    } else {
//                        CD.showDialog(CabinetMemoListByRoleActivity.this, Econstants.decodeBase64(object.optString("StatusMessage")));
//                    }


                } else {
                    CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, "The Request was Successful. No Records Found.");
                }
            }


        } else {
            CD.showDialogCloseActivity(CabinetMemoListByRoleActivity.this, "Something bad happened. Please connect to the ADMIN team.");
        }
    }

}

