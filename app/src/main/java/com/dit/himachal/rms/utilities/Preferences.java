package com.dit.himachal.rms.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class Preferences {

    private static Preferences instance;
    private String preferenceName = "com.dit.himachal.rms";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private String KEY_IS_LOGED_IN = "isLoggedIn";
    private String LOAD_TUTORIAL="toloadTutorial";
    private String ADVOCATE_NAME = "advocate_name";
    private String INFO_LOG = "Loginuserinfo";
    private String KEY_ROLE_ID = "role_id";
    private String KEY_USER_ID = "user_id";
    private String KEY_USERNAME = "user_name";
    private String KEY_MOBILENUMBER = "phone_number";




    public String  role_id, user_id, user_name, phone_number, Loginuserinfo, advocate_name;
    public boolean isLoggedIn = false;
    public boolean loadTutorial = false;


    private Preferences() {

    }

    public synchronized static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();
        return instance;
    }

    public void loadPreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        role_id = preferences.getString(KEY_ROLE_ID, "");
        user_id = preferences.getString(KEY_USER_ID, "");
        isLoggedIn = preferences.getBoolean(KEY_IS_LOGED_IN, isLoggedIn);
        loadTutorial = preferences.getBoolean(LOAD_TUTORIAL,loadTutorial);
        phone_number = preferences.getString(KEY_MOBILENUMBER, "");
        user_name = preferences.getString(KEY_USERNAME, "");
        Loginuserinfo = preferences.getString(INFO_LOG, "");
        advocate_name = preferences.getString(ADVOCATE_NAME, "");
    }

    public void savePreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(KEY_ROLE_ID, role_id);
        editor.putString(KEY_USER_ID, user_id);
        editor.putString(KEY_USERNAME, user_name);
        editor.putString(KEY_MOBILENUMBER, phone_number);
        editor.putBoolean(KEY_IS_LOGED_IN, isLoggedIn);
        editor.putString(INFO_LOG, Loginuserinfo);
        editor.putString(ADVOCATE_NAME, advocate_name);
        editor.putBoolean(LOAD_TUTORIAL,loadTutorial);
        //editor.clear();
        editor.commit();
    }
}
