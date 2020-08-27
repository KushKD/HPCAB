package com.dit.himachal.ecabinet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.modal.RolesPojo;
import com.dit.himachal.ecabinet.modal.UserDataPojo;
import com.dit.himachal.ecabinet.modal.UserPojo;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import com.dit.himachal.ecabinet.utilities.Preferences;

public class Login extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    CustomDialog CD = new CustomDialog();
    EditText otp, mobile;
    SearchableSpinner user, branch, department, role;
    LinearLayout linear, dept_lay;
    Button login, reset;

    List<RolesPojo> roles = new ArrayList<>();
    RolesAdapter adapter = null;

    List<DepartmentsUserPojo> departmentsUserPojos = null;


    List<DepartmentsPojo> departments = new ArrayList<>();
    DepartmentsAdapter departmentsAdapter = null;

    List<BranchPojo> branches = new ArrayList<>();
    BranchAdapter branchAdapter = null;

    List<UserPojo> users = new ArrayList<>();

    UsersAdapter usersAdapter = null;

    private String Global_deptId, Global_roleId, Global_Branch_id, Global_user_id = null;


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
        branch = (SearchableSpinner) login_form.findViewById(R.id.branch);
        department = (SearchableSpinner) login_form.findViewById(R.id.department);
        role = (SearchableSpinner) login_form.findViewById(R.id.role);
        login = (Button) login_form.findViewById(R.id.login);


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

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // if(addPersons.isEmpty()){
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
                    Log.e("Ted", "rere");
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


                    if (Global_roleId.equalsIgnoreCase("7") || Global_roleId.equalsIgnoreCase("6")) {
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

            }
        });


    }


    @Override
    public void onTaskCompleted(ResponsObject result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.GET_ROLES) {
            //Check Weather the String is Json array or Json Object
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
                CD.showDialog(Login.this, result.getRespnse());
            }
        }

        //Departments
        else if (taskType == TaskType.GET_DEPARTMENTS_VIA_ROLES) {

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
                CD.showDialog(Login.this, result.getRespnse());

            }


        } else if (taskType == TaskType.GET_BRANCHES) {
            //Check Weather the String is Json array or Json Object
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
                CD.showDialog(Login.this, result.getRespnse());

            }


        } else if (taskType == TaskType.GET_USERS) {
            //Check Weather the String is Json array or Json Object
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
                        users = new ArrayList<>();
                        //ReportsModelPojo
                        for (int i = 0; i < arrayReports.length(); i++) {
                            UserPojo rolesPojo = new UserPojo();
                            JSONObject object = arrayReports.getJSONObject(i);

                            rolesPojo.setName(Econstants.decodeBase64(object.getString("name")));
                            rolesPojo.setDesignation(Econstants.decodeBase64(object.getString("Designation")));
                            rolesPojo.setMobileNumber(Econstants.decodeBase64(object.getString("MobileNO")));
                            rolesPojo.setUserid(Econstants.decodeBase64(object.getString("Userid")));


                            users.add(rolesPojo);
                        }
                        Log.e("Roles Data", users.toString());
                        usersAdapter = new UsersAdapter(Login.this, android.R.layout.simple_spinner_item, users);
                        user.setAdapter(usersAdapter);

                    } else {
                        CD.showDialog(Login.this, "No Branches Found");
                    }
                }


            } else {
                CD.showDialog(Login.this, result.getRespnse());
            }
        } else if (taskType == TaskType.GET_OTP_VIA_MOBILE) {
            //Check Weather the String is Json array or Json Object
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.respnse);
                Object json = new JSONTokener(result.respnse).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                    JSONObject object = new JSONObject(result.respnse);
                    Log.e("arrayReports", object.toString());

                    CD.showDialog(Login.this, Econstants.decodeBase64(object.getString("StatusMessage")));


                }


            } else {
                CD.showDialog(Login.this, result.getRespnse());
            }
        } else if (taskType == TaskType.LOGIN) {
            //Check Weather the String is Json array or Json Object
            if (result.getSuccessFailure().equalsIgnoreCase("SUCCESS")) {
                Log.e("Result == ", result.respnse);
                Object json = new JSONTokener(result.respnse).nextValue();
                if (json instanceof JSONObject) {
                    Log.e("Json Object", "Object");
                } else if (json instanceof JSONArray) {
                    Log.e("Json Object", "Object");
                    JSONArray arrayReports = new JSONArray(result.respnse);
                    Log.e("arrayReports", arrayReports.toString());

                    if (arrayReports.length() >= 0) {
                        //ReportsModelPojo
                        JSONObject object = arrayReports.getJSONObject(0);
                        if (!Econstants.decodeBase64(object.getString("StatusMessage")).equalsIgnoreCase("Incorrect OTP, please enter correct OTP!!.")) {
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
                            dataPojo.setPhoto(Econstants.decodeBase64(object.optString("Photo")));

                            dataPojo.setRoleId(Global_roleId);

                            Log.e("User Final Data", dataPojo.toString());
                            //Save Data to shared Prefrences
                            boolean goToMain = saveDataSharedPrefrences(dataPojo);
                            if (goToMain) {
                                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                                Login.this.startActivity(mainIntent);
                                Login.this.finish();
                            } else {
                                CD.showDialog(Login.this, "Something ba happened. Please restart the application.");
                            }

                        } else {
                            CD.showDialog(Login.this, Econstants.decodeBase64(object.getString("StatusMessage")));
                        }


                    } else {
                        CD.showDialog(Login.this, result.respnse);
                    }
                }


            } else {
                CD.showDialog(Login.this, result.getRespnse());
            }
        }
    }

    private boolean saveDataSharedPrefrences(UserDataPojo dataPojo) {
                StringBuilder SB =new StringBuilder();
        try {

            Preferences.getInstance().loadPreferences(Login.this);
            Preferences.getInstance().role_name = dataPojo.getDesgination();
            Preferences.getInstance().role_id = dataPojo.getRoleId();
            Preferences.getInstance().user_name = dataPojo.getName();
            Preferences.getInstance().user_id = dataPojo.getUserID();
            Preferences.getInstance().photo = dataPojo.getPhoto();
            if (dataPojo.getIsCabinetMinister().equalsIgnoreCase("Y")) {
                Preferences.getInstance().is_cabinet_minister = true;
            } else {
                Preferences.getInstance().is_cabinet_minister = false;
            }


            Preferences.getInstance().phone_number = dataPojo.getMobileNumber();
            Preferences.getInstance().isLoggedIn = true;
            Preferences.getInstance().branched_mapped = dataPojo.getBranchmapped();

            for(int i=0 ; i<dataPojo.getDepartmentsUser().size(); i++){
                SB.append(dataPojo.getDepartmentsUser().get(i).getDepartmentID());
                SB.append(",");
            }
            Log.e("Mapped Departments", SB.toString());
            Preferences.getInstance().mapped_departments = SB.toString();

            Preferences.getInstance().savePreferences(Login.this);
            finish();
            return true;
        } catch (Exception ex) {
            Log.e("Error SP==", ex.getLocalizedMessage().toString());
            return false;
        }


    }
}