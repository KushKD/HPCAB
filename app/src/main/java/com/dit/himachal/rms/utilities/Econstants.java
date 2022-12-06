package com.dit.himachal.rms.utilities;

import android.content.Context;
import android.graphics.Typeface;

import com.dit.himachal.rms.modal.ResponsObject;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 21, 08 , 2020
 */
public class Econstants {
    //URL Staging
     public static final String url = "https://staging8.hp.gov.in/rmsservice.svc";



    public static String delemeter = "/";
    public static String seperator = "#";

    private static String Color_Grey = "#000000";


    public static String advaocateLogin= "2";
    public static String citizenLogin= "1";



    public static final String methordLogin = "GetLogin";
    public static final String methordLoginToken = "dff89999a314e7421e7e12f5cf7171081f0";


    public static final String methordMenuList ="GetAdvocateMenuList";
    public static final String methordMenuListToken ="UFG776888a314e7421e7e12f5cfuhuu081f0";

    public static final String methordGetRegisteredAdvocatesList = "GetRegisteredAdvocatesList";
    public static final String methordGetRegisteredAdvocatesListToken="7288c3c5f374ab7ca1c17e739f167f39";


    public static final String methordGetArchiveCases = "GetArchiveCases";
    public static final String methordGetArchiveCasesToken="03862fc4d69f68edd7ecd58eaae97c4f";

    public static final String methordGetAdvocateSubscribedCauseList = "GetAdvocateSubscribedCauseList";
    public static final String methordGetAdvocateSubscribedCauseListToken="92b2a5c6cfd41f96594002161a2fcf8e";

    public static final String methordGetCaseNoticesByAdvocate = "GetCaseNoticesByAdvocate";
    public static final String methordGetCaseNoticesByAdvocateToken="b98dc73c2a4c8bf6d867cafe1e3a290c";

    public static final String methordGetSubscribedCases = "GetSubscribedCases";
    public static final String methordGetSubscribedCasesToken="3d26f36651aa463d40536f7e695b08ee";

    public static final String methordGetZimniOrdersAdvocate = "GetZimniOrdersAdvocate";
    public static final String methordGetZimniOrdersAdvocateToken="885277ad69e673d91867f028f1d63e6e";


    public static final String NO_DATA = "No Data Available. Please Connect to Internet and try again !";


    public static final String success = "SUCCESS";
    public static final String failure = "FAILURE";


    public static final int pdfConnectionTimeOut= 300000;
    public static final String successfulDownload= "Download successfully";
    public static final String slash= "/";
    public static final String extentionPdf =".pdf";
    public static final String extentionJpg =".jpg";
    public static final String extentionJpeg =".jpeg";
    public static final String extentionPng =".png";
    public static final String applicationPdf ="application/pdf";
    public static final String intentMessageOpen = "Open File";
    public static final String downloadPdfViewer = " Download PDF Viewer";
    public static final String emptyMessage = "";
    public static final String somethingBadHappend = "Something Bad Happened. Please try again Later.";



    public String successFailure;
    public Integer responseCode;
    public String respnse;


    public static ResponsObject getResponseObject(String successFailure, String respnse, Integer responseCode, String dept_id){
        ResponsObject responsObject = new ResponsObject();
        responsObject.setRespnse(respnse);
        responsObject.setResponseCode(responseCode);
        responsObject.setSuccessFailure(successFailure);
        responsObject.setDept_id(dept_id);
        return responsObject;
    }

    public static  String encodeBase64(String data){

        byte[] encodedBase64Data = Base64.encodeBase64(data.getBytes());
        return new String(encodedBase64Data);
    }

    public static String decodeBase64(String data){

        byte[] decodeBase64Data = Base64.decodeBase64(data.getBytes());
        return new String(decodeBase64Data);
    }


    public static  Typeface getTypefaceRegular(Context applicationContext){
        Typeface typeface = Typeface.createFromAsset(applicationContext.getAssets(), "font/poppins_semibold.ttf");
        return typeface;
    }




}
