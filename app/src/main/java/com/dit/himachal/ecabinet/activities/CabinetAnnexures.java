package com.dit.himachal.ecabinet.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dit.himachal.ecabinet.R;
import com.dit.himachal.ecabinet.adapter.CabinetAnnextureMemosAdapter;
import com.dit.himachal.ecabinet.modal.CabinetMemoPojo;
import com.dit.himachal.ecabinet.modal.ListAnnexures;
import com.dit.himachal.ecabinet.presentation.CustomDialog;

public class CabinetAnnexures extends AppCompatActivity {

    CabinetMemoPojo data = null;
    ListView list;
    CabinetAnnextureMemosAdapter cabinetMemoshistoryAdapter;
    CustomDialog CD = new CustomDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_annexures);
        data = (CabinetMemoPojo) getIntent().getSerializableExtra("data");
        list = findViewById(R.id.list);
        //PreventScreenshot.on(CabinetAnnexures.this);
        list = findViewById(R.id.list);

        if(data.getListCabinetMemoTrackingHistoryLists().size()>0){
            cabinetMemoshistoryAdapter = new CabinetAnnextureMemosAdapter(CabinetAnnexures.this, data.getListAnnexures_());
            list.setAdapter(cabinetMemoshistoryAdapter);
            list.setTextFilterEnabled(true);

            Log.e("DAta", data.getListCabinetMemoTrackingHistoryLists().toString());
        }else{
            CD.showDialogCloseActivity(CabinetAnnexures.this, "No Records Found.");
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListAnnexures cabinet_memo_pojo = (ListAnnexures) parent.getItemAtPosition(position);
                Log.e("PDF URL", cabinet_memo_pojo.getAttachment());
                if(cabinet_memo_pojo.getAttachment().isEmpty()){
                    CD.showDialog(CabinetAnnexures.this,"Attachment Not added.");
                }else{
                    CD.showDialogDownloadPDFWithoutAsOnDate(CabinetAnnexures.this,cabinet_memo_pojo.getAttachment(),"Attachment");
                   // CD.showDialog(CabinetAnnexures.this,cabinet_memo_pojo.getAttachment());

                }

            }
        });
    }

    @Override
    protected void onStop() {
        //  PreventScreenshot.on(CabinetAnnexures.this);
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // PreventScreenshot.on(CabinetAnnexures.this);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onPause() {
        // PreventScreenshot.on(CabinetAnnexures.this);
        super.onPause();

    }

    @Override
    protected void onResume() {
        //  PreventScreenshot.on(CabinetAnnexures.this);
        super.onResume();
    }
}