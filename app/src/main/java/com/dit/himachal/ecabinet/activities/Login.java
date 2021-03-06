package com.dit.himachal.ecabinet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.BranchAdapter;
import com.dit.himachal.ecabinet.adapter.DepartmentsAdapter;
import com.dit.himachal.ecabinet.adapter.RolesAdapter;
import com.dit.himachal.ecabinet.adapter.UsersAdapter;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.BranchPojo;
import com.dit.himachal.ecabinet.modal.DepartmentsPojo;
import com.dit.himachal.ecabinet.modal.DepartmentsUserPojo;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.OfflineDataModel;
import com.dit.himachal.ecabinet.modal.RolesPojo;
import com.dit.himachal.ecabinet.modal.UserDataPojo;
import com.dit.himachal.ecabinet.modal.UserPojo;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
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

public class Login extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    CustomDialog CD = new CustomDialog();
    EditText otp, mobile;
    SearchableSpinner user, branch, department, role;
    LinearLayout linear, dept_lay;
    Button login, reset, get_otp;

    List<RolesPojo> roles = new ArrayList<>();
    RolesAdapter adapter = null;

    List<DepartmentsUserPojo> departmentsUserPojos = null;


    List<DepartmentsPojo> departments = new ArrayList<>();
    DepartmentsAdapter departmentsAdapter = null;

    List<BranchPojo> branches = new ArrayList<>();
    BranchAdapter branchAdapter = null;

    List<UserPojo> users = new ArrayList<>();

    UsersAdapter usersAdapter = null;

    private String Global_deptId, Global_roleId, Global_Branch_id, Global_user_id, Global_Photo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        LinearLayout login_form = findViewById(R.id.login_form);

        linear = (LinearLayout) login_form.findViewById(R.id.linear);
        dept_lay = (LinearLayout) login_form.findViewById(R.id.dept_lay);
        linear.setVisibility(View.VISIBLE);
        otp = (EditText) login_form.findViewById(R.id.otp);
        mobile = (EditText) login_form.findViewById(R.id.mobile);
        user = (SearchableSpinner) login_form.findViewById(R.id.user);
        user.setTitle("Please Select User");
        user.setPrompt("Please Select User");
        branch = (SearchableSpinner) login_form.findViewById(R.id.branch);
        branch.setTitle("Please Select Branch");
        branch.setPrompt("Please Select Branch");
        department = (SearchableSpinner) login_form.findViewById(R.id.department);
        department.setTitle("Please Select Department");
        department.setPrompt("Please Select Department");
        role = (SearchableSpinner) login_form.findViewById(R.id.role);
        role.setTitle("Please Select Role");
        role.setPrompt("Please Select Role");
        login = (Button) login_form.findViewById(R.id.login);
        get_otp = (Button) login_form.findViewById(R.id.get_otp);

      //  PreventScreenshot.on(Login.this);


        if (AppStatus.getInstance(Login.this).isOnline()) {
            GetDataPojo object = new GetDataPojo();
            object.setUrl(Econstants.url);
            object.setMethord(Econstants.methordGetRoles);
            object.setMethordHash(Econstants.encodeBase64(Econstants.methordGetRolesToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
            object.setTaskType(TaskType.GET_ROLES);
            object.setTimeStamp(CommonUtils.getTimeStamp());


            new Generic_Async_Get(
                    Login.this,
                    Login.this,
                    TaskType.GET_ROLES).
                    execute(object);


        } else {
            CD.showDialog(Login.this, "Please connect to Internet and try again.");
        }

//        mobile.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                // TODO Auto-generated method stub
//            }
//        This mobile number is not mapped in the application
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                // TODO Auto-generated method stub
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                // if(addPersons.isEmpty()){
//                if (mobile.getText().toString().length() == 10) {
//                    if (AppStatus.getInstance(Login.this).isOnline()) {
//                        GetDataPojo object = new GetDataPojo();
//                        object.setUrl(Econstants.url);
//                        object.setMethord(Econstants.methordGetOTP);
//                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordOTPToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
//                        object.setTaskType(TaskType.GET_OTP_VIA_MOBILE);
//                        object.setTimeStamp(CommonUtils.getTimeStamp());
//                        List<String> parameters = new ArrayList<>();
//                        parameters.add(mobile.getText().toString());
//                        parameters.add(Global_user_id);
//                        parameters.add(Global_roleId);
//                        object.setParameters(parameters);
//                        new Generic_Async_Get(
//                                Login.this,
//                                Login.this,
//                                TaskType.GET_OTP_VIA_MOBILE).
//                                execute(object);
//
//
//                    } else {
//                        CD.showDialog(Login.this, "Please connect to Internet and try again.");
//                    }
//                } else {
//                    Log.e("Ted", "rere");
//                }
//
//
//            }
//        });

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mobile.getText().toString().length() == 10) {
                    if (AppStatus.getInstance(Login.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordGetOTP);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordOTPToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_OTP_VIA_MOBILE);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(mobile.getText().toString());
                        parameters.add(Global_user_id);
                        parameters.add(Global_roleId);
                        object.setParameters(parameters);
                        new Generic_Async_Get(
                                Login.this,
                                Login.this,
                                TaskType.GET_OTP_VIA_MOBILE).
                                execute(object);


                    } else {
                        CD.showDialog(Login.this, "Please connect to Internet and try again.");
                    }
                } else {
                    CD.showDialog(Login.this, "Please enter a valid 10 digit Mobile Number.");
                }

            }
        });

        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                try {
                    RolesPojo roles = adapter.getItem(position);
                    Log.e("Role ID", roles.getRoleId());
                    Log.e("Role Name", roles.getRoleName());
                    Global_roleId = roles.getRoleId();


                    if (Global_roleId.equalsIgnoreCase("7") || Global_roleId.equalsIgnoreCase("6")
                            || Global_roleId.equalsIgnoreCase("4") ||  Global_roleId.equalsIgnoreCase("5")) {
                        dept_lay.setVisibility(View.GONE);
                        linear.setVisibility(View.GONE);
                        if (AppStatus.getInstance(Login.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordUsers);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordUsersToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.GET_USERS);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add("0");
                            parameters.add("0");
                            parameters.add(Global_roleId);
                            object.setParameters(parameters);
                            new Generic_Async_Get(
                                    Login.this,
                                    Login.this,
                                    TaskType.GET_USERS).
                                    execute(object);


                        } else {
                            CD.showDialog(Login.this, "Please connect to Internet and try again.");
                        }
                    } else {
                        dept_lay.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.VISIBLE);
                        if (AppStatus.getInstance(Login.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordDepartmentsViaRoles);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordDepartmentsToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.GET_DEPARTMENTS_VIA_ROLES);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add(roles.getRoleId());
                            object.setParameters(parameters);
                            new Generic_Async_Get(
                                    Login.this,
                                    Login.this,
                                    TaskType.GET_DEPARTMENTS_VIA_ROLES).
                                    execute(object);


                        } else {
                            CD.showDialog(Login.this, "Please connect to Internet and try again.");
                        }
                    }


                } catch (Exception ex) {
                    CD.showDialog(Login.this, ex.getLocalizedMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }

        });


        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                try {
                    DepartmentsPojo roles = departmentsAdapter.getItem(position);

                    Log.e("Dept Name", roles.getDeptName());
                    Global_deptId = roles.getDeptId();
                    Log.e("Dept ID", roles.getDeptId());


                    if (Global_roleId.equalsIgnoreCase("4") || Global_roleId.equalsIgnoreCase("5")) {
                        linear.setVisibility(View.GONE);

                        if (AppStatus.getInstance(Login.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordUsers);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordUsersToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.GET_USERS);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add(Global_deptId);
                            parameters.add("0");
                            parameters.add(Global_roleId);
                            object.setParameters(parameters);
                            new Generic_Async_Get(
                                    Login.this,
                                    Login.this,
                                    TaskType.GET_USERS).
                                    execute(object);


                        } else {
                            CD.showDialog(Login.this, "Please connect to Internet and try again.");
                        }
                    } else {
                        linear.setVisibility(View.VISIBLE);
                        if (AppStatus.getInstance(Login.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordBranchesViaDept);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordBranchesToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.GET_BRANCHES);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add(roles.getDeptId());
                            object.setParameters(parameters);
                            new Generic_Async_Get(
                                    Login.this,
                                    Login.this,
                                    TaskType.GET_BRANCHES).
                                    execute(object);


                        } else {
                            CD.showDialog(Login.this, "Please connect to Internet and try again.");
                        }
                    }

                } catch (Exception ex) {
                    CD.showDialog(Login.this, ex.getLocalizedMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }

        });


        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                try {
                    BranchPojo roles = branchAdapter.getItem(position);
                    Log.e("Branch Name", roles.getBranchName());

                    Global_Branch_id = roles.getBranchID();
                    if (AppStatus.getInstance(Login.this).isOnline()) {
                        GetDataPojo object = new GetDataPojo();
                        object.setUrl(Econstants.url);
                        object.setMethord(Econstants.methordUsers);
                        object.setMethordHash(Econstants.encodeBase64(Econstants.methordUsersToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                        object.setTaskType(TaskType.GET_USERS);
                        object.setTimeStamp(CommonUtils.getTimeStamp());
                        List<String> parameters = new ArrayList<>();
                        parameters.add(Global_deptId);
                        parameters.add(Global_Branch_id);
                        parameters.add(Global_roleId);
                        object.setParameters(parameters);
                        new Generic_Async_Get(
                                Login.this,
                                Login.this,
                                TaskType.GET_USERS).
                                execute(object);


                    } else {
                        CD.showDialog(Login.this, "Please connect to Internet and try again.");
                    }


                } catch (Exception ex) {
                    CD.showDialog(Login.this, ex.getLocalizedMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }

        });


//TODO
        user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                try {
                    UserPojo roles = usersAdapter.getItem(position);
                    Log.e("Branch Name", roles.getUserid());
                    Global_user_id = roles.getUserid();


                } catch (Exception ex) {
                    CD.showDialog(Login.this, ex.getLocalizedMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }

        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Mobile",mobile.getText().toString());
                if (mobile.getText().toString().length() == 10 && !mobile.getText().toString().isEmpty()) {
                    if (!otp.getText().toString().isEmpty()) {
                        if (AppStatus.getInstance(Login.this).isOnline()) {
                            GetDataPojo object = new GetDataPojo();
                            object.setUrl(Econstants.url);
                            object.setMethord(Econstants.methordLogin);
                            object.setMethordHash(Econstants.encodeBase64(Econstants.methordLoginToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
                            object.setTaskType(TaskType.LOGIN);
                            object.setTimeStamp(CommonUtils.getTimeStamp());
                            List<String> parameters = new ArrayList<>();
                            parameters.add(mobile.getText().toString());
                            parameters.add(otp.getText().toString());
                            parameters.add(Global_user_id);
                            parameters.add(Global_roleId);
                            object.setParameters(parameters);
                            new Generic_Async_Get(
                                    Login.this,
                                    Login.this,
                                    TaskType.LOGIN).
                                    execute(object);


                        } else {
                            CD.showDialog(Login.this, "Please connect to Internet and try again.");
                        }
                    } else {

                        CD.showDialog(Login.this, "Please Enter OTP.");
                    }
                } else {
                    CD.showDialog(Login.this, "Please Enter a valid 10 digit Mobile Number.");
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // PreventScreenshot.on(Login.this);

    }




    @Override
    protected void onStop() {
        // PreventScreenshot.on(Login.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // PreventScreenshot.on(Login.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        // PreventScreenshot.on(Login.this);
        super.onPause();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onTaskCompleted(OfflineDataModel result, TaskType taskType) throws JSONException {
        Log.e("Result", result.toString());
        if (taskType == TaskType.GET_ROLES) {
            //Check Weather the String is Json array or Json Object
            if (result.getHttpFlag().equalsIgnoreCase(Econstants.success)) {
                Log.e("Result == ", result.getResponse());
                Object json = new JSONTokener(result.getResponse()).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                } else if (json instanceof JSONArray) {
                    Log.e("Json Object", "Object");
                    JSONArray arrayReports = new JSONArray(result.getResponse());
                    Log.e("arrayReports", arrayReports.toString());

                    if (arrayReports.length() > 0) {
                        roles = new ArrayList<>();
                        //ReportsModelPojo
                        for (int i = 0; i < arrayReports.length(); i++) {
                            RolesPojo rolesPojo = new RolesPojo();
                            JSONObject object = arrayReports.getJSONObject(i);

                            rolesPojo.setRoleId(Econstants.decodeBase64(object.getString("Roleid")));
                            rolesPojo.setRoleName(Econstants.decodeBase64(object.getString("RoleName")));


                            roles.add(rolesPojo);
                        }
                        Log.e("Roles Data", roles.toString());
                        adapter = new RolesAdapter(Login.this, android.R.layout.simple_spinner_item, roles);
                        role.setAdapter(adapter);

                    } else {
                        CD.showDialog(Login.this, "No Roles Found");
                    }
                }


            } else {
                CD.showDialog(Login.this, result.getResponse());
            }
        }

        //Departments
        else if (taskType == TaskType.GET_DEPARTMENTS_VIA_ROLES) {

            Log.e("Result fd == ", result.getResponse());
            if (result.getHttpFlag().equalsIgnoreCase("SUCCESS")) {
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
                        for (int i = 0; i < arrayReports.length(); i++) {
                            DepartmentsPojo departmentsPojo = new DepartmentsPojo();
                            JSONObject object = arrayReports.getJSONObject(i);

                            departmentsPojo.setDeptId(Econstants.decodeBase64(object.getString("DeptId")));
                            departmentsPojo.setDeptName(Econstants.decodeBase64(object.getString("DeptName")));


                            departments.add(departmentsPojo);
                        }
                        Log.e("Departments Data", departments.toString());
                        departmentsAdapter = new DepartmentsAdapter(Login.this, android.R.layout.simple_spinner_item, departments);
                        department.setAdapter(departmentsAdapter);


                    } else {
                        CD.showDialog(Login.this, "No Departments Found");

                    }
                }


            } else {
                CD.showDialog(Login.this, result.getResponse());

            }


        } else if (taskType == TaskType.GET_BRANCHES) {
            //Check Weather the String is Json array or Json Object
            if (result.getHttpFlag().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getResponse());
                Object json = new JSONTokener(result.getResponse()).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                } else if (json instanceof JSONArray) {
                    Log.e("Json Object", "Object");
                    JSONArray arrayReports = new JSONArray(result.getResponse());
                    Log.e("arrayReports", arrayReports.toString());

                    if (arrayReports.length() > 0) {
                        branches = new ArrayList<>();
                        //ReportsModelPojo
                        for (int i = 0; i < arrayReports.length(); i++) {
                            BranchPojo rolesPojo = new BranchPojo();
                            JSONObject object = arrayReports.getJSONObject(i);

                            rolesPojo.setBranchName(Econstants.decodeBase64(object.getString("BranchName")));
                            rolesPojo.setBranchID(Econstants.decodeBase64(object.getString("BranchID")));


                            branches.add(rolesPojo);
                        }
                        Log.e("Roles Data", branches.toString());
                        branchAdapter = new BranchAdapter(Login.this, android.R.layout.simple_spinner_item, branches);
                        branch.setAdapter(branchAdapter);


                    } else {
                        CD.showDialog(Login.this, "No Branches Found");

                    }
                }


            } else {
                CD.showDialog(Login.this, result.getResponse());

            }


        } else if (taskType == TaskType.GET_USERS) {
            //Check Weather the String is Json array or Json Object
            if (result.getHttpFlag().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getResponse());
                Object json = new JSONTokener(result.getResponse()).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                } else if (json instanceof JSONArray) {
                    Log.e("Json Object", "Object");
                    JSONArray arrayReports = new JSONArray(result.getResponse());
                    Log.e("arrayReports", arrayReports.toString());

                    if (arrayReports.length() > 0) {
                        users = new ArrayList<>();
                        //ReportsModelPojo
                        for (int i = 0; i < arrayReports.length(); i++) {
                            UserPojo rolesPojo = new UserPojo();
                            JSONObject object = arrayReports.getJSONObject(i);

                            rolesPojo.setName(Econstants.decodeBase64(object.getString("name")));
                            rolesPojo.setDesignation(Econstants.decodeBase64(object.getString("Designation")));
                            rolesPojo.setMobileNumber(Econstants.decodeBase64(object.getString("MobileNO")));
                            rolesPojo.setUserid(Econstants.decodeBase64(object.getString("Userid")));
                            rolesPojo.setPhoto(Econstants.decodeBase64(object.getString("Photo")));


                            users.add(rolesPojo);
                        }
                        Log.e("Roles Data", users.toString());
                        Global_Photo = users.get(0).getPhoto();
                        usersAdapter = new UsersAdapter(Login.this, android.R.layout.simple_spinner_item, users);
                        user.setAdapter(usersAdapter);

                    } else {
                        CD.showDialog(Login.this, "No Branches Found");
                    }
                }


            } else {
                CD.showDialog(Login.this, result.getResponse());
            }
        } else if (taskType == TaskType.GET_OTP_VIA_MOBILE) {
            //Check Weather the String is Json array or Json Object
            if (result.getHttpFlag().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.getResponse());
                Object json = new JSONTokener(result.getResponse()).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                    JSONObject object = new JSONObject(result.getResponse());
                    Log.e("arrayReports", object.toString());

                    CD.showDialogSuccess(Login.this, Econstants.decodeBase64(object.getString("StatusMessage")));


                }else{
                    JSONObject json2 = new JSONObject(result.getResponse());
                    CD.showDialog(Login.this,Econstants.decodeBase64(json2.getString("StatusMessage")));
                }


            } else {
                CD.showDialog(Login.this, result.getResponse());
            }
        } else if (taskType == TaskType.LOGIN) {
            //Check Weather the String is Json array or Json Object
            if (result.getHttpFlag().equalsIgnoreCase("SUCCESS")) {
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
                        //"StatusMessage":"Incorrect OTP, please enter correct OTP!!."  Incorrect OTP, please enter correct OTP!!.
                        Log.e("RERERERE",Econstants.decodeBase64(object.getString("StatusMessage")));
                        if (Integer.parseInt(object.getString("StatusCode")) == 200) {
                            departmentsUserPojos = new ArrayList<>();
                            UserDataPojo dataPojo = new UserDataPojo();
                            for (int i = 0; i < arrayReports.length(); i++) {
                                DepartmentsUserPojo rolesPojo = new DepartmentsUserPojo();
                                JSONObject objectx = arrayReports.getJSONObject(i);

                                rolesPojo.setDepartmentID(Econstants.decodeBase64(objectx.getString("Departmentmapped")));
                                rolesPojo.setDepartmentname(Econstants.decodeBase64(objectx.getString("Departmentname")));
                                departmentsUserPojos.add(rolesPojo);

                            }
                            dataPojo.setDepartmentsUser(departmentsUserPojos);
                            dataPojo.setBranchmapped(Econstants.decodeBase64(object.getString("Branchmapped")));
                            dataPojo.setDesgination(Econstants.decodeBase64(object.getString("Desgination")));
                            dataPojo.setIsCabinetMinister(Econstants.decodeBase64(object.getString("IsCabinetMinister")));
                            dataPojo.setMobileNumber(Econstants.decodeBase64(object.getString("MobileNumber")));
                            dataPojo.setName(Econstants.decodeBase64(object.getString("Name")));
                            dataPojo.setUserID(Econstants.decodeBase64(object.getString("UserID")));
                            dataPojo.setPhoto(Global_Photo);

                            dataPojo.setRoleId(Global_roleId);

                            Log.e("User Final Data", dataPojo.toString());
                            //Save Data to shared Prefrences
                            boolean goToMain = saveDataSharedPrefrences(dataPojo);
                            if (goToMain) {
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG);
                                // Intent mainIntent = new Intent(Login.this, MainActivity.class);
                                Intent mainIntent = new Intent(Login.this, MainActivity2.class);
                                Login.this.startActivity(mainIntent);
                                Login.this.finish();
                            } else {
                                CD.showDialog(Login.this, "Something ba happened. Please restart the application.");
                            }

                        } else {
                            CD.showDialog(Login.this, Econstants.decodeBase64(object.getString("StatusMessage")));
                        }


                    } else {
                        CD.showDialog(Login.this, result.getResponse());
                    }
                }


            } else {
                CD.showDialog(Login.this, result.getResponse());
            }
        }
    }

    private boolean saveDataSharedPrefrences(UserDataPojo dataPojo) {
        StringBuilder SB = new StringBuilder();
        try {

            Preferences.getInstance().loadPreferences(Login.this);
            Preferences.getInstance().role_name = dataPojo.getDesgination();
            Preferences.getInstance().role_id = dataPojo.getRoleId();
            Preferences.getInstance().user_name = dataPojo.getName();
            Preferences.getInstance().user_id = dataPojo.getUserID();
            Preferences.getInstance().photo = dataPojo.getPhoto();
            Log.e("Mapped Departments", Preferences.getInstance().photo);

            Preferences.getInstance().is_cabinet_minister = dataPojo.getIsCabinetMinister().equalsIgnoreCase("Y");


            Preferences.getInstance().phone_number = dataPojo.getMobileNumber();
            Preferences.getInstance().isLoggedIn = true;
            Preferences.getInstance().branched_mapped = dataPojo.getBranchmapped();

            for (int i = 0; i < dataPojo.getDepartmentsUser().size(); i++) {
                SB.append(dataPojo.getDepartmentsUser().get(i).getDepartmentID());
                SB.append(",");
            }
            Log.e("Mapped Departments", SB.toString());
            Preferences.getInstance().mapped_departments = SB.toString();

            Preferences.getInstance().savePreferences(Login.this);
            finish();
            return true;
        } catch (Exception ex) {
            Log.e("Error SP==", ex.getLocalizedMessage());
            return false;
        }


    }
}