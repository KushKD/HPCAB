package com.dit.himachal.ecabinet.utilities;

import android.content.Context;
import android.graphics.Typeface;

import com.dit.himachal.ecabinet.modal.ResponsObject;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 21, 08 , 2020
 */
public class Econstants {
    public static final String url = "http://164.100.138.114/eCabinetService.svc";

    public static String delemeter = "/";
    public static String seperator = "#";

    public static final String methordGetRoles = "GetRole";
    public static final String methordGetRolesToken = "UFG776888a314e7421e7e12f5cfuhuu081f0";


    public static final String methordDepartmentsViaRoles = "GetMappedDepartmentsListByUserRole";
    public static final String methordDepartmentsToken = "klhkhughiknbv12f5cf7171081a0dddss";


    public static final String methordBranchesViaDept = "GetBranchDetails";
    public static final String methordBranchesToken = "ckhijjkkjjhjdddii990001e7e12f5cf71710";

    public static final String methordUsers = "GetUserRegistration";
    public static final String methordUsersToken = "KKuuugghgjhjjdd888898hiuh710";

    public static final String methordGetOTP = "LoginOTP";
    public static final String methordOTPToken = "dff89999a314e7421e7e12f5cf7171081f0";


    public static final String methordLogin = "Login";
    public static final String methordLoginToken = "daccaa314e7421e7e12f5cf7171081a0";

    public static final String getDepartmentsViaRoles ="DepartmentsListByRole";
    public static final String getDepartmentsViaRolesToken ="ccaa314e7421e7e12f5cf7171081a0ddd";


    public static final String methordMenuList ="GetMenuList";
    public static final String methordMenuListToken ="KKdd33444444444frrrr1081a0";

    public static final String methordGetOnlineCabinetIDMeetingStatus ="GetOnlineCabinetIDMeetingStatus";
    public static final String methordGetOnlineCabinetIDMeetingToken ="KJJkkjjj00hvjhj68888dhdjjjjjjjjjdddd";











    public static final String success = "SUCCESS";
    public static final String failure = "FAILURE";



    public String successFailure;
    public Integer responseCode;
    public String respnse;


    public static ResponsObject getResponseObject(String successFailure, String respnse, Integer responseCode){
        ResponsObject responsObject = new ResponsObject();
        responsObject.setRespnse(respnse);
        responsObject.setResponseCode(responseCode);
        responsObject.setSuccessFailure(successFailure);
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
