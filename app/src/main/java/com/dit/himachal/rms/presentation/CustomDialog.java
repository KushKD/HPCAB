package com.dit.himachal.rms.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.dit.himachal.rms.R;
import com.dit.himachal.rms.modal.AgendaPojo;
import com.dit.himachal.rms.utilities.HardwareDetails;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;

import java.io.File;

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

    public void showDialogSuccess(final Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_custom_success);

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

    public void showDialogActiveAjenda(final Activity activity, AgendaPojo object) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.agenda_description);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels );
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels );
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView department_name = (TextView) dialog.findViewById(R.id.department_name);
        TextView file_number = (TextView) dialog.findViewById(R.id.file_number);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView agenda_number = (TextView) dialog.findViewById(R.id.agenda_number);
        TextView agenda_type = (TextView) dialog.findViewById(R.id.agenda_type);
        TextView subject = (TextView) dialog.findViewById(R.id.subject);

        department_name.setText(object.getDeptName());
        file_number.setText(object.getFileNo());
        title.setText(object.getFileNo());
        agenda_number.setText(object.getAgendaItemNo());
        agenda_type.setText(object.getAgendaItemType());
        subject.setText(object.getSubject());





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
        dialog.setContentView(R.layout.dialog_custom_success);

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

    @SuppressLint("NewApi")
    public void showDialogDownloadPDFWithoutAsOnDate(final Activity activity, String pdf_url, final String pdf_name) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_download_pdf);

        TextView text = (TextView) dialog.findViewById(R.id.name_file);
        Button dialog_ok = (Button) dialog.findViewById(R.id.dialog_ok);
        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progressBar);
        final TextView textViewProgress  = (TextView)dialog.findViewById(R.id.textViewProgress);

        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(300000)
                .setConnectTimeout(300000)
                .build();
        PRDownloader.initialize(activity, config);

        text.setText(pdf_name);



        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.pause(downloadIdOne);
            return;
        }

        // buttonOne.setEnabled(false);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.resume(downloadIdOne);
            return;
        }

        downloadIdOne = PRDownloader.download(pdf_url, HardwareDetails.getRootDirPath(activity), pdf_name+".pdf")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        //progressBarOne.setIndeterminate(false);
                        //buttonOne.setEnabled(true);
                        //buttonOne.setText(R.string.pause);
                        //buttonCancelOne.setEnabled(true);
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        // buttonOne.setText(R.string.resume);
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
//                                buttonOne.setText(R.string.start);
//                                buttonCancelOne.setEnabled(false);
//                                progressBarOne.setProgress(0);
//                                textViewProgressOne.setText("");
                        downloadIdOne = 0;
//                                progressBarOne.setIndeterminate(false);
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        Log.e("Progress",Long.toString(progressPercent));
                        progressBar.setProgress((int) progressPercent);
                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                        progressBar.setIndeterminate(false);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
//                                buttonOne.setEnabled(false);
//                                buttonCancelOne.setEnabled(false);
//                                buttonOne.setText(R.string.completed);
                        Toast.makeText(activity, pdf_name +"  Download successfully", Toast.LENGTH_SHORT).show();
                        File file = new File(HardwareDetails.getRootDirPath(activity)+"/"+pdf_name+".pdf");
                        if(file.exists()) {
                            Intent target = new Intent(Intent.ACTION_VIEW);
                            target.setDataAndType(Uri.fromFile(file), "application/pdf");
                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            Intent intent = Intent.createChooser(target, "Open File");
                            try {
                                Log.e("PDF Viewer ","Installed");
                                activity.startActivity(intent);
                                dialog.dismiss();
                            } catch (ActivityNotFoundException e) {
                                // Instruct the user to install a PDF reader here, or something
                                Log.e("PDF Viewer Not","Installed");
                                Toast.makeText(activity, "Download PDF Viewer", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader&hl=en_IN")); /// here "yourpackegName" from your app packeg Name
//                                activity.startActivity(i);

                            }
                        }
                        else
                            Toast.makeText(activity, "File path is incorrect." , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(activity, "Error " + "1", Toast.LENGTH_SHORT).show();
                        textViewProgress.setText("");
                    }




                });





        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // activity.finish();
                dialog.dismiss();
                PRDownloader.cancelAll();
            }
        });

        dialog.show();

    }





}
