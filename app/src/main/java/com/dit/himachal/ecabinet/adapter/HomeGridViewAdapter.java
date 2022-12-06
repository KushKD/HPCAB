package com.dit.himachal.ecabinet.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.activities.AdvocateList;
import com.dit.himachal.ecabinet.activities.ArchiveCaseActivity;
import com.dit.himachal.ecabinet.activities.CauseList;
import com.dit.himachal.ecabinet.activities.NoticeAdvocates;
import com.dit.himachal.ecabinet.activities.SubscribedCases;
import com.dit.himachal.ecabinet.activities.ZimniOrder;
import com.dit.himachal.ecabinet.lazyloader.ImageLoader;
import com.dit.himachal.ecabinet.modal.ModulesPojo;
import com.dit.himachal.ecabinet.presentation.CustomDialog;

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


    public HomeGridViewAdapter(Context c, ArrayList<ModulesPojo> spacecrafts) {
        this.c = c;
        this.gridHome = spacecrafts;
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

        ImageView img = view.findViewById(R.id.spacecraftImg);
        TextView nameTxt = view.findViewById(R.id.nameTxt);


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


                //Advocate List
                if (s.getId().equalsIgnoreCase("1")) {
                    Log.e("Advocate List", s.getId());
                    Intent i = new Intent(c.getApplicationContext(), AdvocateList.class);
                    (c).startActivity(i);

                }
                //Archived Case List
                if (s.getId().equalsIgnoreCase("2")) {
                    Log.e("Archived Case List", s.getId());
                    Intent i = new Intent(c.getApplicationContext(), ArchiveCaseActivity.class);
                    (c).startActivity(i);
                }
                //Case List
                if (s.getId().equalsIgnoreCase("3")) {
                    Log.e("Case List", s.getId());
                    Intent i = new Intent(c.getApplicationContext(), CauseList.class);
                    (c).startActivity(i);

                }
                //Notices
                if (s.getId().equalsIgnoreCase("4")) {
                    Log.e("Notices", s.getId());
                    Intent i = new Intent(c.getApplicationContext(), NoticeAdvocates.class);
                    (c).startActivity(i);

                }
                //Subscribed List
                if (s.getId().equalsIgnoreCase("5")) {
                    Log.e("Subscribed List", s.getId());
                    Intent i = new Intent(c.getApplicationContext(), SubscribedCases.class);
                    (c).startActivity(i);

                }
                //Zimni Orders
                if (s.getId().equalsIgnoreCase("6")) {
                    Log.e("Zimni Orders", s.getId());
                    Intent i = new Intent(c.getApplicationContext(), ZimniOrder.class);
                    (c).startActivity(i);
                }

//
//                if (s.getId().equalsIgnoreCase("21")) {
//                    Preferences.getInstance().loadPreferences(c.getApplicationContext());
//
//
//                    Preferences.getInstance().role_id = "";
//                    Preferences.getInstance().user_id = "";
//                    Preferences.getInstance().user_name = "";
//                    Preferences.getInstance().Loginuserinfo="";
//                    Preferences.getInstance().phone_number="";
//                    Preferences.getInstance().advocate_name="";
//                    Preferences.getInstance().isLoggedIn = false;
//
//
//                    Preferences.getInstance().savePreferences(c.getApplicationContext());
//                    Toast.makeText(c.getApplicationContext(), "Logout Successful", Toast.LENGTH_LONG).show();
//
//                    Intent mainIntent = new Intent(c.getApplicationContext(), Login.class);
//                    (c).startActivity(mainIntent);
//                    ((Activity) c).finish();
//
//                }



            }
        });

        return view;
    }


}