package com.dit.himachal.ecabinet.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.dit.himachal.ecabinet.R;

import org.json.JSONException;

import java.io.File;
import java.text.ParseException;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 01, 05 , 2020
 */
public class CustomDialog {
    int downloadIdOne;


    public void showDialog(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.50);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        text.setText(msg);

        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialogCloseActivity(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.50);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.dialog_result);
        text.setText(msg);

        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

//    public void showDialogHTML(final Activity activity, final String msg, final String msgServer
//    ,final String passFile, final String CovidFile, final String OtherFile) throws JSONException {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_web);
//
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//
//        final WebView webView = dialog.findViewById(R.id.dialog_result);
//        final EditText remarks = dialog.findViewById(R.id.remarks);
//        final Button pass_id = dialog.findViewById(R.id.pass_id);
//        final Button covid_id = dialog.findViewById(R.id.covid_id);
//        final Button other_id = dialog.findViewById(R.id.other_id);
//
//        if(!passFile.isEmpty()){
//           pass_id.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   //Download File
//                   String filename= passFile.substring(passFile.lastIndexOf("/")+1);
//                   Log.e("fileName",filename);
//                   String extension = passFile.substring(passFile.lastIndexOf("."));
//                   Log.e("extention",extension);
//                   if(extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpeg")||
//                           extension.equalsIgnoreCase(".gif")){
//                       showDialogDownloadImageWithName(activity,passFile,filename,"Downloading Attachment 1");
//                   }else if(extension.equalsIgnoreCase(".pdf")){
//                       showDialogDownloadPDFWithoutAsOnDate(activity,passFile,filename);
//                   }else{
//                       showDialog(activity,"File not valid");
//                   }
//               }
//           });
//
//        }else{
//            pass_id.setVisibility(View.GONE);
//        }
//
//        if(!CovidFile.isEmpty()){
//            covid_id.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Download File
//                    String filename= CovidFile.substring(CovidFile.lastIndexOf("/")+1);
//                    Log.e("fileName",filename);
//                    String extension = CovidFile.substring(CovidFile.lastIndexOf("."));
//                    Log.e("extention",extension);
//                    if(extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpeg")||
//                            extension.equalsIgnoreCase(".gif")){
//                        showDialogDownloadImageWithName(activity,CovidFile,filename,"Downloading Attachment 2");
//                    }else if(extension.equalsIgnoreCase(".pdf")){
//                        showDialogDownloadPDFWithoutAsOnDate(activity,CovidFile,filename);
//                    }else{
//                        showDialog(activity,"File not valid");
//                    }
//                }
//            });
//        }else{
//            covid_id.setVisibility(View.GONE);
//        }
//
//        if(!OtherFile.isEmpty()){
//            other_id.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Download File
//                    String filename= OtherFile.substring(OtherFile.lastIndexOf("/")+1);
//                    Log.e("fileName",filename);
//                    String extension = OtherFile.substring(OtherFile.lastIndexOf("."));
//                    Log.e("extention",extension);
//                    if(extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpeg")||
//                            extension.equalsIgnoreCase(".gif")){
//                        showDialogDownloadImageWithName(activity,OtherFile,filename,"Downloading Attachment 3");
//                    }else if(extension.equalsIgnoreCase(".pdf")){
//                        showDialogDownloadPDFWithoutAsOnDate(activity,OtherFile,filename);
//                    }else{
//                        showDialog(activity,"File not valid");
//                    }
//                }
//            });
//        }else{
//            other_id.setVisibility(View.GONE);
//        }
//
//        //  webView.setWebChromeClient(new WebChromeClient() );
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                Log.d("Log-->> ", "onPageStarted: ");
//            }
//
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.d("Log-->> ", "onPageFinished: ");
//
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                Log.d("Log-->> ", "onReceivedError: ");
//            }
//
//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
//                Log.d("Log-->> ", "onReceivedHttpError: ");
//            }
//        });
//        webView.requestFocus();
//        webView.setVerticalScrollBarEnabled(true);
//
//
//        WebSettings settings = webView.getSettings();
//        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setDefaultTextEncodingName("utf-8");
//        settings.setAppCacheMaxSize(1024 * 1024 * 128);
//        settings.setJavaScriptEnabled(true);
//
//
//        final String mimeType = "text/html";
//        final String encoding = "UTF-8";
//
//        //msg
//        Log.e("HTML", msg);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            settings.setMixedContentMode(0);
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
//
//
//        webView.postInvalidateDelayed(1500);
//
//
//        webView.loadDataWithBaseURL("", msg, mimeType, encoding, "");
//
////        Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                webView.loadDataWithBaseURL("", msg, mimeType, encoding, "");
////            }
////        }, 2000) ;
////
////        webView.reload();
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        Button dialog_verified = (Button) dialog.findViewById(R.id.dialog_verified);
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        dialog_verified.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                //TODO
//                Log.e("Message", msgServer);
//                //{"pass_id":51827,"id":587}
//                String serverMessage = null;
//                try {
//                    VerifyObject objectVerify = JsonParse.createVerifyMessage(msgServer);
//                    if (remarks.getText().toString() != null || !remarks.getText().toString().isEmpty()) {
//                        objectVerify.setRemarks(remarks.getText().toString().trim());
//                    } else {
//                        objectVerify.setRemarks("");
//                    }
//                    //Create Object
//                    serverMessage = JsonParse.createJson(objectVerify);
//                    Log.e("Server ", serverMessage);
//                } catch (JSONException ex) {
//                    serverMessage = msgServer;
//                }
//
//
//                Intent intent = new Intent("verifyData");
//                intent.setPackage(activity.getPackageName());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("VERIFY_DATA", serverMessage);
//                intent.putExtras(bundle);
//                activity.sendBroadcast(intent);
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public void showDialogHTMLGeneric(final Activity activity, String msg) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_web_generic);
//
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        WebView webView = (WebView) dialog.findViewById(R.id.dialog_result);
//        webView.setVerticalScrollBarEnabled(true);
//        webView.requestFocus();
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.getSettings().setJavaScriptEnabled(true);
//        final String mimeType = "text/html";
//        final String encoding = "UTF-8";
//
//
//        webView.loadDataWithBaseURL("", msg, mimeType, encoding, "");
//
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog.show();
//
//    }
//
//
//    public void showDialogScanData(final Activity activity, final ScanDataPojo scanData) throws ParseException {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_pojo_custom);
//
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        // TextView name = (TextView)dialog.findViewById(R.id.name);
//        TextView passnumber = (TextView) dialog.findViewById(R.id.passnumber);
//        TextView vehiclenumber = (TextView) dialog.findViewById(R.id.vehiclenumber);
//        TextView mobile = (TextView) dialog.findViewById(R.id.mobile);
//        TextView dateissue = (TextView) dialog.findViewById(R.id.dateissue);
//        TextView district = (TextView) dialog.findViewById(R.id.district);
//        TextView barrier = (TextView) dialog.findViewById(R.id.barrier);
//        TextView datescan = (TextView) dialog.findViewById(R.id.datescan);
//        TextView timescan = (TextView) dialog.findViewById(R.id.timescan);
//        final EditText number_of_passengers = (EditText) dialog.findViewById(R.id.number_of_passengers);
//
//
//        // Log.e("====Manual Entry", scanData.getNumber_of_passengers_manual());
//
//
//        // name.setText(taskPojo.getTask_name());
//        passnumber.setText(scanData.getPassNo());
//        vehiclenumber.setText("-");
//        mobile.setText(scanData.getMobileNumbr());
//        dateissue.setText(scanData.getDateIssueDate());
//
//
//        if (scanData.getDistict().equalsIgnoreCase("0")) {
//            district.setText("Other");
//
//        } else {
//            district.setText(Preferences.getInstance().district_name);
//        }
//
//        if (scanData.getBarrrier().equalsIgnoreCase("0")) {
//            barrier.setText("Other");
//        } else {
//            barrier.setText(Preferences.getInstance().barrier_name);
//        }
//
//
//        datescan.setText(DateTime.Change_Date_Format_second(scanData.getScanDate()));
//
//
//        Log.e("ScanDae", DateTime.Change_Date_Format_second(scanData.getScanDate()));
//        timescan.setText(DateTime.changeTime(scanData.getScanDate()));
////        task_completed_by.setText(taskPojo.getTask_completed_by_name());
//
//
//        Button cancel = (Button) dialog.findViewById(R.id.cancel);
//        Button proceed = (Button) dialog.findViewById(R.id.proceed);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!number_of_passengers.getText().toString().isEmpty() && number_of_passengers.getText().toString() != null) {
//
//                    scanData.setNumber_of_passengers_manual(number_of_passengers.getText().toString());
//                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
//                } else {
//                    scanData.setNumber_of_passengers_manual("0");
//                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
//                }
//
//                scanData.setVersionApp(Econstants.getVersion(activity));
//                //Start ASYNC TASK TODO
//                // prepare your parameters that need to be sent back to activity
//                Intent intent = new Intent("UploadServer");
//                intent.setPackage(activity.getPackageName());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("SCAN_DATA", scanData);
//                intent.putExtras(bundle);
//                activity.sendBroadcast(intent);
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public void showDialogScanDataOut(final Activity activity, final ScanDataPojo scanData) throws ParseException {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_pojo_custom);
//
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        // TextView name = (TextView)dialog.findViewById(R.id.name);
//        TextView passnumber = (TextView) dialog.findViewById(R.id.passnumber);
//        TextView vehiclenumber = (TextView) dialog.findViewById(R.id.vehiclenumber);
//        TextView mobile = (TextView) dialog.findViewById(R.id.mobile);
//        TextView dateissue = (TextView) dialog.findViewById(R.id.dateissue);
//        TextView district = (TextView) dialog.findViewById(R.id.district);
//        TextView barrier = (TextView) dialog.findViewById(R.id.barrier);
//        TextView datescan = (TextView) dialog.findViewById(R.id.datescan);
//        TextView timescan = (TextView) dialog.findViewById(R.id.timescan);
//        final EditText number_of_passengers = (EditText) dialog.findViewById(R.id.number_of_passengers);
//
//
//        // Log.e("====Manual Entry", scanData.getNumber_of_passengers_manual());
//
//
//        // name.setText(taskPojo.getTask_name());
//        passnumber.setText(scanData.getPassNo());
//        vehiclenumber.setText("-");
//        mobile.setText(scanData.getMobileNumbr());
//        dateissue.setText(scanData.getDateIssueDate());
//
//
//        if (scanData.getDistict().equalsIgnoreCase("0")) {
//            district.setText("Other");
//
//        } else {
//            district.setText(Preferences.getInstance().district_name);
//        }
//
//        if (scanData.getBarrrier().equalsIgnoreCase("0")) {
//            barrier.setText("Other");
//        } else {
//            barrier.setText(Preferences.getInstance().barrier_name);
//        }
//
//
//        datescan.setText(DateTime.Change_Date_Format_second(scanData.getScanDate()));
//
//
//        Log.e("ScanDae", DateTime.Change_Date_Format_second(scanData.getScanDate()));
//        timescan.setText(DateTime.changeTime(scanData.getScanDate()));
////        task_completed_by.setText(taskPojo.getTask_completed_by_name());
//
//
//        Button cancel = (Button) dialog.findViewById(R.id.cancel);
//        Button proceed = (Button) dialog.findViewById(R.id.proceed);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!number_of_passengers.getText().toString().isEmpty() && number_of_passengers.getText().toString() != null) {
//
//                    scanData.setNumber_of_passengers_manual(number_of_passengers.getText().toString());
//                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
//                } else {
//                    scanData.setNumber_of_passengers_manual("0");
//                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
//                }
//
//                scanData.setVersionApp(Econstants.getVersion(activity));
//                //Start ASYNC TASK TODO
//                // prepare your parameters that need to be sent back to activity
//                Intent intent = new Intent("UploadServerOut");
//                intent.setPackage(activity.getPackageName());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("SCAN_DATA", scanData);
//                intent.putExtras(bundle);
//                activity.sendBroadcast(intent);
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public void showDialogSearchByPassId(final Activity activity) throws ParseException {
//        final ScanDataPojo scanData = new ScanDataPojo();
//        scanData.setScannedByPhoneNumber(Preferences.getInstance().phone_number);
//        scanData.setDistict(Preferences.getInstance().district_id);
//        scanData.setBarrrier(Preferences.getInstance().barrier_id);
//        scanData.setLatitude("0");
//        scanData.setLongitude("0");
//        scanData.setPassNo("-");
//        scanData.setMobileNumbr("-");
//        scanData.setPrsonNo("-");
//        scanData.setDateIssueDate("-");
//        scanData.setScanDate(CommonUtils.getCurrentDate());
//        scanData.setVersionApp(Econstants.getVersion(activity));
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_pojopassid_custom);
//
//        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
//        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.95);
//        dialog.getWindow().setLayout(width, height);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        // TextView name = (TextView)dialog.findViewById(R.id.name);
//        final EditText passnumber = (EditText) dialog.findViewById(R.id.passnumber);
//
//
//        TextView district = (TextView) dialog.findViewById(R.id.district);
//        TextView barrier = (TextView) dialog.findViewById(R.id.barrier);
//        TextView datescan = (TextView) dialog.findViewById(R.id.datescan);
//        TextView timescan = (TextView) dialog.findViewById(R.id.timescan);
//        final EditText number_of_passengers = (EditText) dialog.findViewById(R.id.number_of_passengers);
//
//
//        // name.setText(taskPojo.getTask_name());
//
//        if (scanData.getDistict().equalsIgnoreCase("0")) {
//            district.setText("Other");
//
//        } else {
//            district.setText(Preferences.getInstance().district_name);
//        }
//
//        if (scanData.getBarrrier().equalsIgnoreCase("0")) {
//            barrier.setText("Other");
//        } else {
//            barrier.setText(Preferences.getInstance().barrier_name);
//        }
//
//
//        datescan.setText(DateTime.Change_Date_Format_second(scanData.getScanDate()));
//
//        timescan.setText(DateTime.changeTime(scanData.getScanDate()));
////        task_completed_by.setText(taskPojo.getTask_completed_by_name());
//
//
//        Button cancel = (Button) dialog.findViewById(R.id.cancel);
//        Button proceed = (Button) dialog.findViewById(R.id.proceed);
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//            }
//        });
//
//        proceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!number_of_passengers.getText().toString().isEmpty() && number_of_passengers.getText().toString() != null) {
//
//                    scanData.setNumber_of_passengers_manual(number_of_passengers.getText().toString());
//                    System.out.println("====Manual Entry" + scanData.getNumber_of_passengers_manual());
//                } else {
//                    scanData.setNumber_of_passengers_manual("0");
//                }
//
//
//                if (!passnumber.getText().toString().isEmpty() && passnumber.getText().toString() != null) {
//
//                    scanData.setPassNo(passnumber.getText().toString());
//                    System.out.println("====Manual Entry" + scanData.getPassNo());
//                    Intent intent = new Intent("UploadServerManual");
//                    intent.setPackage(activity.getPackageName());
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("SCAN_DATA", scanData);
//                    intent.putExtras(bundle);
//                    activity.sendBroadcast(intent);
//                    dialog.dismiss();
//                } else {
//                    showDialog(activity, "Please Enter Pass Number");
//                }
//
//
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//    @SuppressLint("NewApi")
//    public void showDialogDownloadImageWithName(final Activity activity, String url, final String name , final String display_name) {
//
//        Log.e("!@!@!@!URl",url);
//        Log.e("!@!@!@!@Name",name);
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(display_name);
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(url, HardwareDetails.getRootDirPath(activity), name)
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                       // Toast.makeText(activity, name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+name);
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "image/*");
//                            // target.setFlags(Intent.FLAG_H);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                Log.e("PDF Viewer ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                                Log.e("PDF Viewer Not","Installed");
//                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
//
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//    @SuppressLint("NewApi")
//    public void showDialogDownloadPDFWithoutAsOnDate(final Activity activity, String pdf_url, final String pdf_name) {
//
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();
//
//
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        dialog.setContentView(R.layout.dialog_custom_download_pdf);
//
//        TextView text = (TextView) dialog.findViewById(R.id.name_file);
//        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
//        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
//        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);
//
//        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
//                .setReadTimeout(300000)
//                .setConnectTimeout(300000)
//                .build();
//        PRDownloader.initialize(activity, config);
//
//        text.setText(pdf_name);
//
//
//
//        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.pause(downloadIdOne);
//            return;
//        }
//
//        // buttonOne.setEnabled(false);
//        progressBar.setIndeterminate(true);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
//            PRDownloader.resume(downloadIdOne);
//            return;
//        }
//
//        downloadIdOne = PRDownloader.download(pdf_url, HardwareDetails.getRootDirPath(activity), pdf_name+".pdf")
//                .build()
//                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
//                    @Override
//                    public void onStartOrResume() {
//                        //progressBarOne.setIndeterminate(false);
//                        //buttonOne.setEnabled(true);
//                        //buttonOne.setText(R.string.pause);
//                        //buttonCancelOne.setEnabled(true);
//                    }
//                })
//                .setOnPauseListener(new OnPauseListener() {
//                    @Override
//                    public void onPause() {
//                        // buttonOne.setText(R.string.resume);
//                    }
//                })
//                .setOnCancelListener(new OnCancelListener() {
//                    @Override
//                    public void onCancel() {
////                                buttonOne.setText(R.string.start);
////                                buttonCancelOne.setEnabled(false);
////                                progressBarOne.setProgress(0);
////                                textViewProgressOne.setText("");
//                        downloadIdOne = 0;
////                                progressBarOne.setIndeterminate(false);
//                    }
//                })
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        Log.e("Progress",Long.toString(progressPercent));
//                        progressBar.setProgress((int) progressPercent);
//                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                        progressBar.setIndeterminate(false);
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
////                                buttonOne.setEnabled(false);
////                                buttonCancelOne.setEnabled(false);
////                                buttonOne.setText(R.string.completed);
//                        Toast.makeText(activity, pdf_name +"  Download successfully", Toast.LENGTH_SHORT).show();
//                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+pdf_name+".pdf");
//                        if(file.exists()) {
//                            Intent target = new Intent(Intent.ACTION_VIEW);
//                            target.setDataAndType(Uri.fromFile(file), "application/pdf");
//                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            Intent intent = Intent.createChooser(target, "Open File");
//                            try {
//                                Log.e("PDF Viewer ","Installed");
//                                activity.startActivity(intent);
//                                dialog.dismiss();
//                            } catch (ActivityNotFoundException e) {
//                                // Instruct the user to install a PDF reader here, or something
//                                Log.e("PDF Viewer Not","Installed");
//                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
////                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
////                                activity.startActivity(i);
//
//                            }
//                        }
//                        else
//                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
//                        textViewProgress.setText("");
//                    }
//
////                            @Override
////                            public void onError(Error error) {
////                               // buttonOne.setText(R.string.start);
////                                Toast.makeText(getApplicationContext(), "Error " + "1", Toast.LENGTH_SHORT).show();
////                              //  textViewProgressOne.setText("");
////                               // progressBarOne.setProgress(0);
////                                downloadIdOne = 0;
////                                //buttonCancelOne.setEnabled(false);
////                                //progressBarOne.setIndeterminate(false);
////                                //buttonOne.setEnabled(true);
////                            }
//                });
//
//
//
//
//
//        dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // activity.finish();
//                dialog.dismiss();
//                PRDownloader.cancelAll();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//


}
