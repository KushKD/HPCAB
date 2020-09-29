package com.dit.himachal.ecabinet.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.activities.ApprovedMemo;
import com.dit.himachal.ecabinet.activities.CabinetDecisions;
import com.dit.himachal.ecabinet.activities.CabinetMemoListByRoleActivity;
import com.dit.himachal.ecabinet.activities.FinalAgendaList;
import com.dit.himachal.ecabinet.activities.Login;
import com.dit.himachal.ecabinet.lazyloader.ImageLoader;
import com.dit.himachal.ecabinet.modal.ModulesPojo;
import com.dit.himachal.ecabinet.presentation.CustomDialog;
import com.dit.himachal.ecabinet.utilities.Preferences;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class HomeGridViewAdapter extends BaseAdapter {
    Context c;
    ArrayList<ModulesPojo> gridHome;


    ImageLoader il = new ImageLoader(c);
    CustomDialog CD = new CustomDialog();
    String dept_id_ = null;


    public HomeGridViewAdapter(Context c, ArrayList<ModulesPojo> spacecrafts, String dept_id) {
        this.c = c;
        this.gridHome = spacecrafts;
        this.dept_id_ = dept_id;
    }

    @Override
    public int getCount() {
        return gridHome.size();
    }

    @Override
    public Object getItem(int i) {
        return gridHome.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(c).inflate(R.layout.home_gridview_model, viewGroup, false);
        }

        final ModulesPojo s = (ModulesPojo) this.getItem(i);

        ImageView img = (ImageView) view.findViewById(R.id.spacecraftImg);
        TextView nameTxt = (TextView) view.findViewById(R.id.nameTxt);


        nameTxt.setText(s.getName());


        if (s.getLogo().equalsIgnoreCase("") || s.getLogo() == null) {
            //show uk icon
            String fnm = "hp_n";
            String PACKAGE_NAME = c.getApplicationContext().getPackageName();
            int imgId = this.c.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + fnm, null, null);
            System.out.println("IMG ID :: " + imgId);
            System.out.println("PACKAGE_NAME :: " + PACKAGE_NAME);
            img.setImageBitmap(BitmapFactory.decodeResource(c.getApplicationContext().getResources(), imgId));
        } else {
            String fnm = s.getLogo();
            il.DisplaySquareImage(fnm, img, null, null, false);
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ID", s.getId());
                Log.e("DeptID", dept_id_);


                if (s.getId().equalsIgnoreCase("1")) {
                    Log.e("DIT", dept_id_);
                    Intent i = new Intent(c.getApplicationContext(), CabinetMemoListByRoleActivity.class);
                    i.putExtra("department_id", dept_id_);
                    i.putExtra("param", "Current");
                    (c).startActivity(i);

                }
                if (s.getId().equalsIgnoreCase("2")) {
                    Log.e("DIT", dept_id_);
                    Intent i = new Intent(c.getApplicationContext(), CabinetMemoListByRoleActivity.class);
                    i.putExtra("department_id", dept_id_);
                    i.putExtra("param", "Forwarded");
                    (c).startActivity(i);

                }
                if (s.getId().equalsIgnoreCase("3")) {
                    Log.e("DIT", dept_id_);
                    Intent i = new Intent(c.getApplicationContext(), CabinetMemoListByRoleActivity.class);
                    i.putExtra("department_id", dept_id_);
                    i.putExtra("param", "Backwarded");
                    (c).startActivity(i);

                }
                if (s.getId().equalsIgnoreCase("5")) {
                    CustomDialog CD = new CustomDialog();
                    CD.showDialog((Activity) c, "Under Process.");

                }
                if (s.getId().equalsIgnoreCase("10")) {
                    CustomDialog CD = new CustomDialog();
                    CD.showDialog((Activity) c, "Under Process.");

                }
                if (s.getId().equalsIgnoreCase("4")) {
                    Intent intent = new Intent("getAgenda");
                    intent.setPackage(c.getPackageName());
                    (c).sendBroadcast(intent);
                }
                if (s.getId().equalsIgnoreCase("6")) {
                    Intent i = new Intent(c.getApplicationContext(), ApprovedMemo.class);
                    i.putExtra("department_id", dept_id_);
                    i.putExtra("param", "allowedCabinetMemos");
                    (c).startActivity(i);
                }
                if (s.getId().equalsIgnoreCase("7")) {
                    Intent i = new Intent(c.getApplicationContext(), FinalAgendaList.class);
                    i.putExtra("department_id", dept_id_);
                    (c).startActivity(i);
                }  //PlacedInCabinet
                if (s.getId().equalsIgnoreCase("8")) {
                    Intent i = new Intent(c.getApplicationContext(), CabinetMemoListByRoleActivity.class);
                    i.putExtra("department_id", dept_id_);
                    i.putExtra("param", "PlacedInCabinet");
                    (c).startActivity(i);
                }
                if(s.getId().equalsIgnoreCase("9")){  //Cabinet_Decisions
                    Intent i = new Intent(c.getApplicationContext(), CabinetDecisions.class);
                    i.putExtra("department_id", dept_id_);
                    i.putExtra("param","Cabinet_Decisions");
                    (c).startActivity(i);
                }

                if (s.getId().equalsIgnoreCase("21")) {
                    Preferences.getInstance().loadPreferences(c.getApplicationContext());


                    Preferences.getInstance().role_id = "";
                    Preferences.getInstance().user_id = "";
                    Preferences.getInstance().user_name = "";
                    Preferences.getInstance().role_name = "";
                    Preferences.getInstance().mapped_departments = "";
                    Preferences.getInstance().branched_mapped = "";
                    Preferences.getInstance().photo = "";
                    Preferences.getInstance().is_cabinet_minister = false;
                    Preferences.getInstance().isLoggedIn = false;


                    Preferences.getInstance().savePreferences(c.getApplicationContext());
                    Toast.makeText(c.getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();

                    Intent mainIntent = new Intent(c.getApplicationContext(), Login.class);
                    (c).startActivity(mainIntent);
                    ((Activity) c).finish();

                }
//                if (s.getName().equalsIgnoreCase("Search Pass")) {
//                    try {
//                        CD.showDialogSearchByPassId((Activity) c);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (s.getName().equalsIgnoreCase("Manual Entry")) {
//
//                    Intent i = new Intent(c.getApplicationContext(), ManualEntry.class);
//
//                    (c).startActivity(i);
//
//                }


            }
        });

        return view;
    }


}