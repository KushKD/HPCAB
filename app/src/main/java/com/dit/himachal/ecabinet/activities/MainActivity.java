package com.dit.himachal.ecabinet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.DepartmentsAdapter;
import com.dit.himachal.ecabinet.adapter.HomeGridViewAdapter;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.lazyloader.ImageLoader;
import com.dit.himachal.ecabinet.modal.DepartmentsPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.ModulesPojo;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.presentation.MeetingStatus;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.dit.himachal.ecabinet.utilities.Preferences;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    GridView home_gv;
    HomeGridViewAdapter adapter_modules;

    List<DepartmentsPojo> departments = new ArrayList<>();
    DepartmentsAdapter departmentsAdapter = null;

    CustomDialog CD = new CustomDialog();
    SearchableSpinner department;
    List<ModulesPojo> modules = null;
    LinearLayout layout_user_dashboard;

    TextView username, designation, mobile, is_cabinet;
    ImageView imageuser;

    ImageLoader imageLoader = new ImageLoader(MainActivity.this);

    MeetingStatus meetingStatus;
    public String Global_deptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home_gv = findViewById(R.id.gv);
        department = findViewById(R.id.department);
        LinearLayout layout_user_dashboard = findViewById(R.id.user_dashboard);
        username = (TextView) layout_user_dashboard.findViewById(R.id.username);
        designation = (TextView) layout_user_dashboard.findViewById(R.id.designation);
        meetingStatus = (MeetingStatus) layout_user_dashboard.findViewById(R.id.meeting_status);
        meetingStatus.setSelected(true);
        imageuser = (ImageView) layout_user_dashboard.findViewById(R.id.imageuser);
        // mobile = (TextView) layout_user_dashboard.findViewById(R.id.mobile);
        //  is_cabinet = (TextView) layout_user_dashboard.findViewById(R.id.is_cabinet);

        username.setText(Preferences.getInstance().user_name);
        designation.setText(Preferences.getInstance().role_name);

//        if(!Preferences.getInstance().photo.equalsIgnoreCase("")   ){
//            imageLoader.DisplayCircleImage(Preferences.getInstance().photo, imageuser, null,null, false);
//        }


        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                try {
                    DepartmentsPojo roles = departmentsAdapter.getItem(position);

                    Log.e("Dept Name", roles.getDeptName());
                    Global_deptId = roles.getDeptId();

                    if (AppStatus.getInstance(MainActivity.this).isOnline()) {
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

                        new Generic_Async_Get(
                                MainActivity.this,
                                MainActivity.this,
                                TaskType.GET_MENU_LIST).
                                execute(object);

                    } else {
                        CD.showDialog(MainActivity.this, "Please connect to Internet and try again.");
                    }




                } catch (Exception ex) {
                    CD.showDialog(MainActivity.this, ex.getLocalizedMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }

        });





        if (AppStatus.getInstance(MainActivity.this).isOnline()) {
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

            new Generic_Async_Get(
                    MainActivity.this,
                    MainActivity.this,
                    TaskType.GET_DEPARTMENTS_VIA_ROLES).
                    execute(object2);

        } else {
            CD.showDialog(MainActivity.this, "Please connect to Internet and try again.");
        }


        meetingStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CD.showDialog(MainActivity.this, meetingStatus.getText().toString());
            }
        });

    }

    @Override
    public void onTaskCompleted(ResponsObject result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.GET_DEPARTMENTS_VIA_ROLES) {

            Log.e("Result fd == ", result.respnse);
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.respnse);
                Object json = new JSONTokener(result.respnse).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                } else if (json instanceof JSONArray) {
                    Log.e("Json Object", "Object");
                    JSONArray arrayReports = new JSONArray(result.respnse);
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
                        departmentsAdapter = new DepartmentsAdapter(MainActivity.this, android.R.layout.simple_spinner_item, departments);
                        department.setAdapter(departmentsAdapter);


                    } else {
                        CD.showDialog(MainActivity.this, "No Departments Found");

                    }
                }


            } else {
                CD.showDialog(MainActivity.this, result.getRespnse());

            }

        } else if (taskType == TaskType.GET_MENU_LIST) {

            Log.e("Result fd == ", result.respnse);
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.respnse);
                Object json = new JSONTokener(result.respnse).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                } else if (json instanceof JSONArray) {
                    Log.e("Json Object", "Object");
                    JSONArray arrayReports = new JSONArray(result.respnse);
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

                      //  Log.e("Departments Data", departments.toString());;
                        adapter_modules = new HomeGridViewAdapter(this, (ArrayList<ModulesPojo>) modules, result.getDept_id());
                        home_gv.setAdapter(adapter_modules);


                    } else {
                        CD.showDialog(MainActivity.this, "No Departments Found");

                    }
                }


            } else {
                CD.showDialog(MainActivity.this, result.getRespnse());

            }

        }
    }
}