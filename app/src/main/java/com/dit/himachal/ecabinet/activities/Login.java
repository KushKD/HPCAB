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
    SearchableSpinner role;
    Button login, reset, get_otp;


    private String  Global_roleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        LinearLayout login_form = findViewById(R.id.login_form);

        otp = (EditText) login_form.findViewById(R.id.otp);
        mobile = (EditText) login_form.findViewById(R.id.mobile);

        role = (SearchableSpinner) login_form.findViewById(R.id.role);
        role.setTitle("Please Select Role");
        role.setPrompt("Please Select Role");
        login = (Button) login_form.findViewById(R.id.login);

      //  PreventScreenshot.on(Login.this);


//        if (AppStatus.getInstance(Login.this).isOnline()) {
//            GetDataPojo object = new GetDataPojo();
//            object.setUrl(Econstants.url);
//            object.setMethord(Econstants.methordGetRoles);
//            object.setMethordHash(Econstants.encodeBase64(Econstants.methordGetRolesToken + Econstants.seperator + CommonUtils.getTimeStamp())); //Encode Base64 TODO
//            object.setTaskType(TaskType.GET_ROLES);
//            object.setTimeStamp(CommonUtils.getTimeStamp());
//
//        } else {
//            CD.showDialog(Login.this, "Please connect to Internet and try again.");
//        }






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
                            if(role.getSelectedItem().toString().equalsIgnoreCase("Citizen")){
                                parameters.add(Econstants.citizenLogin);
                                object.setParameters(parameters);
                                new Generic_Async_Get(
                                        Login.this,
                                        Login.this,
                                        TaskType.LOGIN).
                                        execute(object);
                            }else if (role.getSelectedItem().toString().equalsIgnoreCase("Advocate")){
                                parameters.add(Econstants.advaocateLogin);
                                object.setParameters(parameters);
                                new Generic_Async_Get(
                                        Login.this,
                                        Login.this,
                                        TaskType.LOGIN).
                                        execute(object);
                            }else{
                                CD.showDialog(Login.this,"Please Select Role");
                            }


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
       if (taskType == TaskType.LOGIN) {
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

                            UserDataPojo dataPojo = new UserDataPojo();

                            dataPojo.setBranchmapped(Econstants.decodeBase64(object.getString("Branchmapped")));
                            dataPojo.setDesgination(Econstants.decodeBase64(object.getString("Desgination")));
                            dataPojo.setIsCabinetMinister(Econstants.decodeBase64(object.getString("IsCabinetMinister")));
                            dataPojo.setMobileNumber(Econstants.decodeBase64(object.getString("MobileNumber")));
                            dataPojo.setName(Econstants.decodeBase64(object.getString("Name")));
                            dataPojo.setUserID(Econstants.decodeBase64(object.getString("UserID")));


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