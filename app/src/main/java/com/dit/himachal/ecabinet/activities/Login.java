package com.dit.himachal.ecabinet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.enums.TaskType;
import com.dit.himachal.ecabinet.generic.Generic_Async_Get;
import com.dit.himachal.ecabinet.interfaces.AsyncTaskListenerObjectGet;
import com.dit.himachal.ecabinet.modal.GetDataPojo;
import com.dit.himachal.ecabinet.modal.ResponsObject;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.utilities.AppStatus;
import com.dit.himachal.ecabinet.utilities.CommonUtils;
import com.dit.himachal.ecabinet.utilities.Econstants;
import com.doi.spinnersearchable.SearchableSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class Login extends AppCompatActivity implements AsyncTaskListenerObjectGet {

    CustomDialog CD = new CustomDialog();
    EditText otp, mobile;
    SearchableSpinner user, branch, department, role;
    LinearLayout linear;
    Button login, reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        LinearLayout login_form = findViewById(R.id.login_form);

        linear = (LinearLayout) login_form.findViewById(R.id.linear);
        linear.setVisibility(View.GONE);
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


    }


    @Override
    public void onTaskCompleted(ResponsObject result, TaskType taskType) throws JSONException {

        if (taskType == TaskType.GET_ROLES) {
            Log.e("Result == ", result.respnse);
        }

    }
}