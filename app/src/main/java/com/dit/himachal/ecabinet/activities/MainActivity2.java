package com.dit.himachal.ecabinet.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.DepartmentsAdapter;
import com.dit.himachal.ecabinet.adapter.HomeGridViewAdapter;
import com.dit.himachal.ecabinet.adapter.SliderAdapter;
import com.dit.himachal.ecabinet.databases.DatabaseHandler;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.interfaces.LengthAgenda;
import com.dit.himachal.ecabinet.lazyloader.ImageLoader;
import com.dit.himachal.ecabinet.modal.AgendaPojo;
import com.dit.himachal.ecabinet.modal.DepartmentsPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.ModulesPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.presentation.MeetingStatus;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.dit.himachal.ecabinet.utilities.Preferences;
import com.doi.spinnersearchable.SearchableSpinner;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements AsyncTaskListenerObjectGet, LengthAgenda, NavigationView.OnNavigationItemSelectedListener {

    public SliderView sliderView;

    GridView home_gv;
    HomeGridViewAdapter adapter_modules;
    ActionBarDrawerToggle actionBarDrawerToggle = null;
    List<DepartmentsPojo> departments = new ArrayList<>();
    DepartmentsAdapter departmentsAdapter = null;

    CustomDialog CD = new CustomDialog();
    SearchableSpinner department;
    List<ModulesPojo> modules = null;
    LinearLayout layout_user_dashboard;

    TextView username, designation, mobile, is_cabinet;
    //ImageView imageuser;
    //SwipeRefreshLayout pullToRefresh;


    ImageLoader imageLoader = new ImageLoader(MainActivity2.this);

    MeetingStatus meetingStatus;
    public String Global_deptId;
    private BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));


        // actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.color_red_maroon));


        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.custom_home_actionbar_layout, null);
        getSupportActionBar().setCustomView(v);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top lev


        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView name = hView.findViewById(R.id.name);
        TextView mobile_number = hView.findViewById(R.id.mobilenumber);
        TextView designation = hView.findViewById(R.id.designation);
        name.setText(Preferences.getInstance().user_name);
        mobile_number.setText(Preferences.getInstance().phone_number);
        designation.setText(Preferences.getInstance().role_name);

        if (Preferences.getInstance().isLoggedIn) {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.logout);
        } else {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.login);
        }

        //  PreventScreenshot.on(MainActivity2.this);

        // pullToRefresh = findViewById(R.id.pullToRefresh);
        sliderView = findViewById(R.id.imageSlider);
        home_gv = findViewById(R.id.gv);
        department = findViewById(R.id.department);
        meetingStatus = findViewById(R.id.mstatus);
        //  meetingStatus.setDisableChildrenTouchEvents(true);

        // LinearLayout layout_user_dashboard = findViewById(R.id.layout_user);
        // username = (TextView) layout_user_dashboard.findViewById(R.id.username);
        // designation = (TextView) layout_user_dashboard.findViewById(R.id.designation);
        // meetingStatus =  meetingStatus.findViewById(R.id.user_dashboard);
        //  meetingStatus.setSelected(true);
        //  meetingStatus.setClickable(true);
        // meetingStatus.setFocusable(true);
//        imageuser = (ImageView) layout_user_dashboard.findViewById(R.id.imageuser);

        department.setTitle(" Select Department");
        department.setPrompt(" Select Department");
        // meetingStatus.seton
        // mobile = (TextView) layout_user_dashboard.findViewById(R.id.mobile);
        //  is_cabinet = (TextView) layout_user_dashboard.findViewById(R.id.is_cabinet);

//        username.setText(Preferences.getInstance().user_name);
        //      designation.setText(Preferences.getInstance().role_name);

//        Log.e("Photo==", Preferences.getInstance().photo);

        //if (!Preferences.getInstance().photo.isEmpty()) {
        //  imageLoader.DisplayCircleImage(Preferences.getInstance().photo, imageuser, null, null, false);
        //}


        /**
         *  if (AppStatus.getInstance(MainActivity2.this).isOnline()) {
         *                         GetDataPojo object = new GetDataPojo();
         *                         object.setUrl(Econstants.url);
         *                         object.setMethord(Econstants.methordGetOnlineCabinetIDMeetingStatus);
         *                         object.setMethordHash(Econstants.encodeBase64(Econstants.methordGetOnlineCabinetIDMeetingToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
         *                         object.setTaskType(TaskType.CABINET_MEETING_STATUS);
         *                         object.setTimeStamp(CommonUtils.getTimeStamp());
         *                         object.setBifurcation("CABINET_MEETING_STATUS");
         *
         *                         new Generic_Async_Get(
         *                                 MainActivity2.this,
         *                                 MainActivity2.this,
         *                                 TaskType.CABINET_MEETING_STATUS).
         *                                 execute(object);
         */

        meetingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "Running", Toast.LENGTH_SHORT).show();
            }
        });


        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                try {
                    DepartmentsPojo roles = departmentsAdapter.getItem(position);

                    Log.e("Dept Name", roles.getDeptName());
                    Global_deptId = roles.getDeptId();

                    if (AppStatus.getInstance(MainActivity2.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordMenuList);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordMenuListToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_MENU_LIST);
                        object.setDepartmentId(Global_deptId);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(Preferences.getInstance().role_id);
                        object.setParameters(parameters);
                        object.setBifurcation("Menu" + Global_deptId);

                        new Generic_Async_Get(
                                MainActivity2.this,
                                MainActivity2.this,
                                TaskType.GET_MENU_LIST).
                                execute(object);

                    } else {
                        DatabaseHandler DB = new DatabaseHandler(MainActivity2.this);
                        Log.e("GET_MENU_LIST Start", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_MENU_LIST.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "Menu" + Global_deptId).size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.GET_MENU_LIST.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "Menu" + Global_deptId).size() > 0) {
                            //Show Events
                            try {

                                showMenu(DB.GetAllOfflineDataViaFunction(TaskType.GET_MENU_LIST.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "Menu" + Global_deptId).get(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(MainActivity2.this, Econstants.NO_DATA);
                        }
                        Toast.makeText(getApplicationContext(), "Application running in Offline Mode.", Toast.LENGTH_LONG).show();
                    }


                } catch (Exception ex) {
                    CD.showDialog(MainActivity2.this, ex.getLocalizedMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }

        });


        if (AppStatus.getInstance(MainActivity2.this).isOnline()) {
            GetDataPojo object2 = new GetDataPojo();
            object2.setUrl(Econstants.url);
            object2.setMethord(Econstants.getDepartmentsViaRoles);
            object2.setMethordHash(Econstants.encodeBase64(Econstants.getDepartmentsViaRolesToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
            object2.setTaskType(TaskType.GET_DEPARTMENTS_VIA_ROLES);
            object2.setTimeStamp(CommonUtils.getTimeStamp());
            List<String> parameters = new ArrayList<>();
            parameters.add(Preferences.getInstance().user_id);
            parameters.add(Preferences.getInstance().role_id);
            object2.setParameters(parameters);
            object2.setBifurcation("GET_DEPARTMENTS_VIA_ROLES");

            new Generic_Async_Get(
                    MainActivity2.this,
                    MainActivity2.this,
                    TaskType.GET_DEPARTMENTS_VIA_ROLES).
                    execute(object2);

        } else {

            DatabaseHandler DB = new DatabaseHandler(MainActivity2.this);
            Log.e("GET_DEPARTMENTS_VIA", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "GET_DEPARTMENTS_VIA_ROLES").size()));
            if (DB.GetAllOfflineDataViaFunction(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "GET_DEPARTMENTS_VIA_ROLES").size() > 0) {
                //Show Events
                try {

                    showDepartments(DB.GetAllOfflineDataViaFunction(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "GET_DEPARTMENTS_VIA_ROLES").get(0));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                CD.showDialogCloseActivity(MainActivity2.this, Econstants.NO_DATA);
            }

        }


//        home_gv.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                int topRowVerticalPosition = (home_gv == null || home_gv.getChildCount() == 0) ? 0 : home_gv.getChildAt(0).getTop();
//                pullToRefresh.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
//            }
//        });

//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                if (AppStatus.getInstance(MainActivity2.this).isOnline()) {
//                    GetDataPojo object2 = new GetDataPojo();
//                    object2.setUrl(Econstants.url);
//                    object2.setMethord(Econstants.getDepartmentsViaRoles);
//                    object2.setMethordHash(Econstants.encodeBase64(Econstants.getDepartmentsViaRolesToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
//                    object2.setTaskType(TaskType.GET_DEPARTMENTS_VIA_ROLES);
//                    object2.setTimeStamp(CommonUtils.getTimeStamp());
//                    List<String> parameters = new ArrayList<>();
//                    parameters.add(Preferences.getInstance().user_id);
//                    parameters.add(Preferences.getInstance().role_id);
//                    object2.setParameters(parameters);
//
//                    new Generic_Async_Get(
//                            MainActivity2.this,
//                            MainActivity2.this,
//                            TaskType.GET_DEPARTMENTS_VIA_ROLES).
//                            execute(object2);
//
//                } else {
//
//                    DatabaseHandler DB = new DatabaseHandler(MainActivity2.this);
//                    Log.e("GET_DEPARTMENTS_VIA", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "GET_DEPARTMENTS_VIA_ROLES").size()));
//                    if (DB.GetAllOfflineDataViaFunction(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "GET_DEPARTMENTS_VIA_ROLES").size() > 0) {
//                        //Show Events
//                        try {
//
//                            showDepartments(DB.GetAllOfflineDataViaFunction(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "GET_DEPARTMENTS_VIA_ROLES").get(0));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        CD.showDialogCloseActivity(MainActivity2.this, Econstants.NO_DATA);
//                    }
//                }
//
//
//                pullToRefresh.setRefreshing(false);
//            }
//        });

        SliderAdapter adapters = new SliderAdapter(this);


        sliderView.setSliderAdapter(adapters);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);

            }
        });


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("We are Here", intent.getAction());
                if (intent.getAction() == "getAgenda") {


                    if (AppStatus.getInstance(MainActivity2.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordGetOnlineCabinetIDMeetingStatus);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordGetOnlineCabinetIDMeetingToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.CABINET_MEETING_STATUS);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        object.setBifurcation("CABINET_MEETING_STATUS");

                        new Generic_Async_Get(
                                MainActivity2.this,
                                MainActivity2.this,
                                TaskType.CABINET_MEETING_STATUS).
                                execute(object);

                    } else {
                        DatabaseHandler DB = new DatabaseHandler(MainActivity2.this);
                        Log.e("CABINET_MEETING_STATUS", Integer.toString(DB.GetAllOfflineDataViaFunction(TaskType.CABINET_MEETING_STATUS.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "CABINET_MEETING_STATUS").size()));
                        if (DB.GetAllOfflineDataViaFunction(TaskType.CABINET_MEETING_STATUS.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "CABINET_MEETING_STATUS").size() > 0) {
                            //Show Events
                            try {

                                showCabinetAgenda(DB.GetAllOfflineDataViaFunction(TaskType.CABINET_MEETING_STATUS.toString(), Preferences.getInstance().user_id, Preferences.getInstance().role_id, "CABINET_MEETING_STATUS").get(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            CD.showDialogCloseActivity(MainActivity2.this, Econstants.NO_DATA);
                        }
                    }


                }


            }
        };
    }

    private void showMenu(OfflineDataModel menu) throws JSONException {

        if (menu.getFunctionName().equalsIgnoreCase(TaskType.GET_MENU_LIST.toString())) {
            Object json = new JSONTokener(menu.getResponse()).nextValue();
            if (json instanceof JSONObject) {
                Log.e("Json Object", "Object");
            } else if (json instanceof JSONArray) {
                Log.e("Json Object", "Object");
                JSONArray arrayReports = new JSONArray(menu.getResponse());
                Log.e("arrayReports", arrayReports.toString());

                if (arrayReports.length() > 0) {
                    modules = new ArrayList<>();
                    //ReportsModelPojo


                    for (int i = 0; i < arrayReports.length(); i++) {
                        ModulesPojo modulesPojo = new ModulesPojo();
                        JSONObject object = arrayReports.getJSONObject(i);

                        modulesPojo.setId(Econstants.decodeBase64(object.optString("Menuid")));
                        modulesPojo.setName(Econstants.decodeBase64(object.optString("MenuName")));
                        modulesPojo.setLogo(Econstants.decodeBase64(object.optString("MenuIcon")));


                        modules.add(modulesPojo);
                    }

                    adapter_modules = new HomeGridViewAdapter(this, (ArrayList<ModulesPojo>) modules, Global_deptId);
                    home_gv.setAdapter(adapter_modules);


                } else {
                    Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_LONG).show();

                }
            }
        }
    }


    private void showDepartments(OfflineDataModel result) throws JSONException {

        if (result.getFunctionName().equalsIgnoreCase(TaskType.GET_DEPARTMENTS_VIA_ROLES.toString())) {
            Log.e("Result fd == ", result.getResponse());
            // if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
            Log.e("Result == ", result.getResponse());
            Object json = new JSONTokener(result.getResponse()).nextValue();
            if (json instanceof JSONObject) {
                Log.e("Json Object", "Object");
            } else if (json instanceof JSONArray) {
                Log.e("Json Object", "Object");
                JSONArray arrayReports = new JSONArray(result.getResponse());
                Log.e("arrayReports", arrayReports.toString());

                if (arrayReports.length() > 0) {
                    departments = new ArrayList<>();
                    //ReportsModelPojo

                    DepartmentsPojo all = new DepartmentsPojo();
                    all.setDeptName("All");
                    all.setDeptId("0");


                    for (int i = 0; i < arrayReports.length(); i++) {
                        DepartmentsPojo departmentsPojo = new DepartmentsPojo();
                        JSONObject object = arrayReports.getJSONObject(i);

                        departmentsPojo.setDeptId(Econstants.decodeBase64(object.getString("DeptId")));
                        departmentsPojo.setDeptName(Econstants.decodeBase64(object.getString("DeptName")));


                        departments.add(departmentsPojo);
                    }
                    departments.add(0, all);
                    Log.e("Departments Data", departments.toString());
                    departmentsAdapter = new DepartmentsAdapter(MainActivity2.this, android.R.layout.simple_spinner_item, departments);
                    department.setAdapter(departmentsAdapter);


                } else {
                    CD.showDialog(MainActivity2.this, "No Departments Found");

                }
            }


            // }
            else {
                CD.showDialog(MainActivity2.this, result.getResponse());

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
                        CD.showDialogActiveAjenda(MainActivity2.this, agendaPojo);
                        //TODO Agenda Pop Up
                    } else {
                        Log.e("Agenda", agendaPojo.toString());
                        CD.showDialogSuccess(MainActivity2.this, "No Active Agenda Item Available.");
                    }

                } catch (Exception ex) {
                    Log.e("arrayReports", ex.toString());
                }


            }


        } else {
            CD.showDialog(MainActivity2.this, result.getResponse());

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // PreventScreenshot.on(MainActivity2.this);
        registerReceiver(mReceiver, new IntentFilter("getAgenda"));

    }


    @Override
    protected void onStop() {
        // PreventScreenshot.on(MainActivity2.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // PreventScreenshot.on(MainActivity2.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        // PreventScreenshot.on(MainActivity2.this);
        unregisterReceiver(mReceiver);
        super.onPause();

    }

//    private LengthAgenda onLengthChangedListener = new LengthAgenda() {
//        @Override
//        public void onLengthChanged(View v, int progress) {
//            Log.e("Length is:- ", String.valueOf(progress));
//        }
//    };

    @Override
    public void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.GET_DEPARTMENTS_VIA_ROLES) {

            if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                //Save the rsult to Database
                DatabaseHandler DH = new DatabaseHandler(MainActivity2.this);
                //Check weather the Hash is Present in the DB or not
                Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), "GET_DEPARTMENTS_VIA_ROLES")));
                if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), "GET_DEPARTMENTS_VIA_ROLES") == 1) {
                    //Update the Earlier Record
                    DH.updateData(result);
                    Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), "GET_DEPARTMENTS_VIA_ROLES") == 0) {
                    DH.addOfflineAccess(result);
                    Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                } else {
                    //DELETE ALL THE RECORDS
                    DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), "GET_DEPARTMENTS_VIA_ROLES");
                    Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_DEPARTMENTS_VIA_ROLES.toString(), "GET_DEPARTMENTS_VIA_ROLES")));
                    //Add the Latest Record
                    DH.addOfflineAccess(result);
                }

                showDepartments(result);
            } else {
                CD.showDialogCloseActivity(MainActivity2.this, result.getResponse());
            }

        } else if (taskType == TaskType.GET_MENU_LIST) {

            if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                //Save the rsult to Database
                DatabaseHandler DH = new DatabaseHandler(MainActivity2.this);
                //Check weather the Hash is Present in the DB or not
                Log.e("??Total Numner of Rows", Integer.toString(DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_MENU_LIST.toString(), "Menu" + Global_deptId)));
                if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_MENU_LIST.toString(), "Menu" + Global_deptId) == 1) {
                    //Update the Earlier Record
                    DH.updateData(result);
                    Log.e("Updated Row", Boolean.toString(DH.updateData(result)));
                } else if (DH.getNoOfRowsBeforeOfflineSave(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_MENU_LIST.toString(), "Menu" + Global_deptId) == 0) {
                    DH.addOfflineAccess(result);
                    Log.e("Added Row", Boolean.toString(DH.addOfflineAccess(result)));
                } else {
                    //DELETE ALL THE RECORDS
                    DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_MENU_LIST.toString(), "Menu" + Global_deptId);
                    Log.e("Total Records Deleted:-", Integer.toString(DH.deleteAllExistingOfflineData(Preferences.getInstance().user_id, Preferences.getInstance().role_id, TaskType.GET_MENU_LIST.toString(), "Menu" + Global_deptId)));
                    //Add the Latest Record
                    DH.addOfflineAccess(result);
                }

                showMenu(result);
            } else {
                CD.showDialogCloseActivity(MainActivity2.this, result.getResponse());
            }


        } else if (taskType == TaskType.CABINET_MEETING_STATUS) {
            if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                //Save the rsult to Database
                DatabaseHandler DH = new DatabaseHandler(MainActivity2.this);
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

                showCabinetAgenda(result);
            } else {
                CD.showDialogCloseActivity(MainActivity2.this, result.getResponse());
            }

        }
    }

    @Override
    public void onLengthChanged(View v, int progress) {
        Log.e("Methord Called", "We are HEre");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.log_in) {
            Intent i = new Intent(MainActivity2.this, Login.class);
            startActivity(i);

        }
        //else
        else if (id == R.id.log_out) {
            Preferences.getInstance().loadPreferences(MainActivity2.this);


            Preferences.getInstance().role_id = "";
            Preferences.getInstance().user_id = "";
            Preferences.getInstance().user_name = "";
            Preferences.getInstance().role_name = "";
            Preferences.getInstance().mapped_departments = "";
            Preferences.getInstance().branched_mapped = "";
            Preferences.getInstance().photo = "";
            Preferences.getInstance().is_cabinet_minister = false;
            Preferences.getInstance().isLoggedIn = false;


            Preferences.getInstance().savePreferences(MainActivity2.this);
            Toast.makeText(MainActivity2.this, "Logout Successful", Toast.LENGTH_LONG).show();

            Intent mainIntent = new Intent(MainActivity2.this, Login.class);
            (MainActivity2.this).startActivity(mainIntent);
            MainActivity2.this.finish();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
