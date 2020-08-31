package com.dit.himachal.ecabinet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.CabinetHistoryMemosAdapter;
import com.dit.himachal.ecabinet.adapter.CabinetMemosAdapter;
import com.dit.himachal.ecabinet.modal.CabinetMemoPojo;
import com.dit.himachal.ecabinet.modal.ListCabinetMemoTrackingHistoryListsPojo;
import com.dit.himachal.ecabinet.presentation.CustomDialog;

public class CabinetMemoHistory extends AppCompatActivity {

    CabinetMemoPojo data = null;
    ListView list;
    CabinetHistoryMemosAdapter cabinetMemoshistoryAdapter;
    CustomDialog CD = new CustomDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_memo_history);

        data = (CabinetMemoPojo) getIntent().getSerializableExtra("data");

        list = findViewById(R.id.list);

        if(data.getListCabinetMemoTrackingHistoryLists().size()>0){
            cabinetMemoshistoryAdapter = new CabinetHistoryMemosAdapter(CabinetMemoHistory.this, data.getListCabinetMemoTrackingHistoryLists());
            list.setAdapter(cabinetMemoshistoryAdapter);
            list.setTextFilterEnabled(true);

            Log.e("DAta", data.getListCabinetMemoTrackingHistoryLists().toString());
        }else{
            CD.showDialogCloseActivity(CabinetMemoHistory.this, "No Records Found.");
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListCabinetMemoTrackingHistoryListsPojo cabinet_memo_pojo = (ListCabinetMemoTrackingHistoryListsPojo) parent.getItemAtPosition(position);
                if(cabinet_memo_pojo.getRemarks().isEmpty()){
                    CD.showDialog(CabinetMemoHistory.this,"Remarks Not added.");
                }else{
                    CD.showDialog(CabinetMemoHistory.this,cabinet_memo_pojo.getRemarks());
                }

            }
        });

    }
}