package com.dit.himachal.ecabinet.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.activities.CabinetMemoListByRoleActivity;
import com.dit.himachal.ecabinet.lazyloader.ImageLoader;
import com.dit.himachal.ecabinet.modal.ModulesPojo;
import com.dit.himachal.ecabinet.presentation.CustomDialog;

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


        if (s.getLogo().equalsIgnoreCase("")||s.getLogo() == null) {
            //show uk icon
            String fnm = "hp_n";
            String PACKAGE_NAME = c.getApplicationContext().getPackageName();
            int imgId = this.c.getApplicationContext().getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + fnm, null, null);
            System.out.println("IMG ID :: " + imgId);
            System.out.println("PACKAGE_NAME :: " + PACKAGE_NAME);
            img.setImageBitmap(BitmapFactory.decodeResource(c.getApplicationContext().getResources(), imgId));
        } else {
            String fnm = s.getLogo();
            il.DisplaySquareImage(fnm, img, null,null, false);
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ID",s.getId());


                if (s.getId().equalsIgnoreCase("1")) {
                    Log.e("DIT",dept_id_);
                    Intent i = new Intent(c.getApplicationContext(), CabinetMemoListByRoleActivity.class);
                    i.putExtra("department_id", dept_id_);
                    (c).startActivity(i);

                }
//                if (s.getName().equalsIgnoreCase("Total Scanned Passes")) {
//
//                    DatabaseHandler DB = new DatabaseHandler(c);
//                    CD.showDialog((Activity) c, Integer.toString(DB.getNoOfRowsCount()));
//
//                }
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