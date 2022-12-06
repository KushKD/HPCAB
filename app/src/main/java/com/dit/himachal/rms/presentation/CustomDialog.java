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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dit.himachal.rms.R;
import com.dit.himachal.rms.activities.ArchiveCaseActivity;
import com.dit.himachal.rms.lazyloader.ImageLoader;
import com.dit.himachal.rms.modal.AdvocateListPojo;
import com.dit.himachal.rms.modal.AgendaPojo;
import com.dit.himachal.rms.modal.ArchivedCasePojo;
import com.dit.himachal.rms.modal.CaseNoticesByAdvocatePojo;
import com.dit.himachal.rms.modal.CauseListPojo;
import com.dit.himachal.rms.modal.ZimniOrderPojo;
import com.dit.himachal.rms.utilities.Econstants;
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

    /**
     * @param activity
     * @param pojo
     */
    public void showDialog_moredetails(final Activity activity, final AdvocateListPojo pojo) {
        ImageLoader il = new ImageLoader(activity.getApplicationContext());
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_moredetails_advocate);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView details = dialog.findViewById(R.id.details);
        TextView name = dialog.findViewById(R.id.name);
        TextView bar = dialog.findViewById(R.id.bar);
        TextView registrationdate = dialog.findViewById(R.id.registrationdate);
        TextView reg_num = dialog.findViewById(R.id.reg_num);
        ImageView a = dialog.findViewById(R.id.a);


       // Log.e("Image", pojo.getPassportPhoto());
        il.DisplayCircleImage(pojo.getPassportPhoto(), a, null, null, false);

//        Glide.with(activity)
//                .load(pojo.getPassportPhoto()) // image url
//                .placeholder(R.drawable.judge) // any placeholder to load at start
//                .error(R.drawable.judge)  // any image in case of error
//                .override(200, 200) // resizing
//                .centerCrop()
//                .into(a);  // imageview object

        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);



        details.setText("Registration Number:- "+pojo.getRegistrationNo());
        name.setText(pojo.getAdvocateName());
        bar.setText(pojo.getBarCouncilName());
        registrationdate.setText(pojo.getDateofRegistration());
        reg_num.setText(pojo.getRegistrationNo());



        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialog_moredetails_causeList(final Activity activity, final ArchivedCasePojo pojo) {
        ImageLoader il = new ImageLoader(activity.getApplicationContext());
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_moredetails_cause);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView caseno = dialog.findViewById(R.id.caseno);
        TextView casesubtype = dialog.findViewById(R.id.casesubtype);
        TextView casetitle = dialog.findViewById(R.id.casetitle);
        TextView casetype = dialog.findViewById(R.id.casetype);
        TextView caseyear = dialog.findViewById(R.id.caseyear);
        TextView institutiondate = dialog.findViewById(R.id.institutiondate);



        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);



        caseno.setText(pojo.getCaseNo());
        casesubtype.setText(pojo.getCaseSubType());
        casetitle.setText(pojo.getCaseTitle());
        casetype.setText(pojo.getCaseType());
        caseyear.setText(pojo.getCaseYear());
        institutiondate.setText(pojo.getInstitutionDate());



        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }




    public void showDialog_moredetails_zimni(final Activity activity, final ZimniOrderPojo pojo) {
        ImageLoader il = new ImageLoader(activity.getApplicationContext());
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_moredetails_zimni);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView caseno = dialog.findViewById(R.id.caseno);
        TextView casetitle = dialog.findViewById(R.id.casetitle);
        TextView caseyear = dialog.findViewById(R.id.caseyear);
        TextView hearingdate = dialog.findViewById(R.id.hearingdate);
        TextView publisheddate = dialog.findViewById(R.id.publisheddate);



        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);
        Button dowmload = dialog.findViewById(R.id.dowmload);



        caseno.setText(pojo.getCaseNo());
        casetitle.setText(pojo.getCaseTitle());
        caseyear.setText(pojo.getCaseYear());
        hearingdate.setText(pojo.getHearingDate());
        publisheddate.setText(pojo.getPublishedDate());

        dowmload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(pojo.getZimniPdf());
                showDialogDownloadPDFWithName(activity,pojo.getZimniPdf(),"zimni.pdf", "zimni.pdf");

            }
        });



        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showDialog_moredetails_causelist(final Activity activity, final CauseListPojo pojo) {
        ImageLoader il = new ImageLoader(activity.getApplicationContext());
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_moredetails_causelist);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView casefixedfor = dialog.findViewById(R.id.casefixedfor);
        TextView caseno = dialog.findViewById(R.id.caseno);
        TextView casetitle = dialog.findViewById(R.id.casetitle);
        TextView caseyear = dialog.findViewById(R.id.caseyear);
        TextView court = dialog.findViewById(R.id.court);
        TextView hearingaddress = dialog.findViewById(R.id.hearingaddress);
        TextView hearingdate = dialog.findViewById(R.id.hearingdate);



        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);



        caseno.setText(pojo.getCaseNo());
        casetitle.setText(pojo.getCaseTitle());
        caseyear.setText(pojo.getCaseYear());
        hearingdate.setText(pojo.getHearingDate());
        hearingaddress.setText(pojo.getHearingAddress());
        hearingdate.setText(pojo.getHearingDate());
        court.setText(pojo.getCourt());
        casefixedfor.setText(pojo.getCaseFixedFor());



        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void showDialog_moredetails_notice(final Activity activity, final CaseNoticesByAdvocatePojo pojo) {
        ImageLoader il = new ImageLoader(activity.getApplicationContext());
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_moredetails_notice);

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 1);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 1);
        dialog.getWindow().setLayout(width, height);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView caseno = dialog.findViewById(R.id.caseno);
        TextView casetitle = dialog.findViewById(R.id.casetitle);
        TextView caseyear = dialog.findViewById(R.id.caseyear);
        TextView hearingdate = dialog.findViewById(R.id.hearingdate);
        TextView isseddate = dialog.findViewById(R.id.isseddate);
        TextView notice = dialog.findViewById(R.id.notice);




        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);
        Button download = dialog.findViewById(R.id.download);



        caseno.setText(pojo.getCaseNo());
        casetitle.setText(pojo.getCaseTitle());
        caseyear.setText(pojo.getCaseYear());
        hearingdate.setText(pojo.getHearingDate());
        isseddate.setText(pojo.getIssuedDate());
        notice.setText(pojo.getNotice());



        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(pojo.getNoticeCopy());
                showDialogDownloadPDFWithName(activity,pojo.getNoticeCopy(),"notice.pdf", "notice.pdf");
            }
        });

        dialog.show();

    }


    /**
     * @param activity
     * @param pdf_url
     * @param pdf_name
     * @param name
     */
    @SuppressLint("NewApi")
    public void showDialogDownloadPDFWithName(final Activity activity, String pdf_url, final String pdf_name, final String name) {


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_download_pdf);
        TextView text = dialog.findViewById(R.id.name_file);
        Button dialog_ok = dialog.findViewById(R.id.dialog_ok);
        final ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        final TextView textViewProgress = dialog.findViewById(R.id.textViewProgress);

        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(Econstants.pdfConnectionTimeOut)
                .setConnectTimeout(Econstants.pdfConnectionTimeOut)
                .build();
        PRDownloader.initialize(activity, config);

        text.setText(name);


        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.pause(downloadIdOne);
            return;
        }


        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
            PRDownloader.resume(downloadIdOne);
            return;
        }

        downloadIdOne = PRDownloader.download(pdf_url, HardwareDetails.getRootDirPath(activity), pdf_name)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                        downloadIdOne = 0;

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;

                        progressBar.setProgress((int) progressPercent);
                        textViewProgress.setText(HardwareDetails.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                        progressBar.setIndeterminate(false);
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        Toast.makeText(activity, pdf_name + Econstants.successfulDownload, Toast.LENGTH_SHORT).show();
                        File file = new File(HardwareDetails.getRootDirPath(activity) + Econstants.slash + pdf_name);
                        if (file.exists()) {
                            Intent target = new Intent(Intent.ACTION_VIEW);
                            target.setDataAndType(Uri.fromFile(file), Econstants.applicationPdf);
                            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            Intent intent = Intent.createChooser(target, Econstants.intentMessageOpen);
                            try {

                                activity.startActivity(intent);
                                dialog.dismiss();
                            } catch (ActivityNotFoundException e) {

                                Toast.makeText(activity, Econstants.downloadPdfViewer, Toast.LENGTH_SHORT).show();


                            }
                        } else
                            Toast.makeText(activity, "File path is incorrect.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(activity, Econstants.somethingBadHappend, Toast.LENGTH_SHORT).show();
                        textViewProgress.setText(Econstants.emptyMessage);
                    }


                });


        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                PRDownloader.cancelAll();
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
