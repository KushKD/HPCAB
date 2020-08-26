package com.dit.himachal.ecabinet.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 19, 08 , 2020
 */
public class Preferences {

    private static Preferences instance;
    private String preferenceName = "com.dit.himachal.ecabinet";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private String KEY_IS_LOGED_IN = "isLoggedIn";
    private String KEY_ROLE_NAME = "role_name";
    private String KEY_ROLE_ID = "role_id";
    private String KEY_USER_ID = "user_id";
    private String KEY_USERNAME = "user_name";
    private String KEY_MOBILENUMBER = "phone_number";
    private String KEY_IsCabinetMinister = "is_cabinet_minister";
    private String BRACHED_MAPPED = "branched_mapped";
    private String KEY_PHOTO = "photo";
    private String KEY_DEPARTMENTS = "mapped_departments";


    public String role_name, role_id, user_id, user_name, phone_number, branched_mapped, photo, mapped_departments;
    public boolean isLoggedIn, is_cabinet_minister;


    private Preferences() {

    }

    public synchronized static Preferences getInstance() {
        if (instance == null)
            instance = new Preferences();
        return instance;
    }

    public void loadPreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        role_name = preferences.getString(KEY_ROLE_NAME, "");
        role_id = preferences.getString(KEY_ROLE_ID, "");
        user_id = preferences.getString(KEY_USER_ID, "");
        isLoggedIn = preferences.getBoolean(KEY_IS_LOGED_IN, isLoggedIn);
        phone_number = preferences.getString(KEY_MOBILENUMBER, "");
        user_name = preferences.getString(KEY_USERNAME, "");
        branched_mapped = preferences.getString(BRACHED_MAPPED, "");
        is_cabinet_minister = preferences.getBoolean(KEY_IsCabinetMinister, is_cabinet_minister);
        photo = preferences.getString(KEY_PHOTO, photo);
        mapped_departments = preferences.getString(KEY_DEPARTMENTS,mapped_departments);
    }

    public void savePreferences(Context c) {
        preferences = c.getSharedPreferences(preferenceName, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(KEY_ROLE_NAME, role_name);
        editor.putString(KEY_ROLE_ID, role_id);
        editor.putString(KEY_USER_ID, user_id);
        editor.putString(KEY_USERNAME, user_name);
        editor.putString(KEY_MOBILENUMBER, phone_number);
        editor.putBoolean(KEY_IS_LOGED_IN, isLoggedIn);
        editor.putBoolean(KEY_IsCabinetMinister, is_cabinet_minister);
        editor.putString(BRACHED_MAPPED, branched_mapped);
        editor.putString(KEY_PHOTO, photo);
        editor.putString(KEY_DEPARTMENTS,mapped_departments);
        //editor.clear();
        editor.commit();
    }
}
