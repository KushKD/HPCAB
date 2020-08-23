package com.dit.himachal.ecabinet.utilities;

import com.dit.himachal.ecabinet.modal.ResponsObject;

import org.apache.commons.codec.binary.Base64;


/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 21, 08 , 2020
 */
public class Econstants {
    public static final String url = "http://staging1.hp.gov.in/ecabinetwcf";

    public static String delemeter = "/";
    public static String seperator = "#";

    public static final String methordGetRoles = "Getrole";
    public static final String methordGetRolesToken = "UFG776888a314e7421e7e12f5cfuhuu081f0";

    public static final String success = "SUCCESS";
    public static final String failure = "FAILURE";



    public String successFailure;
    public Integer responseCode;
    public String respnse;


    public static ResponsObject getResponseObject(String successFailure, String respnse, Integer responseCode){
        ResponsObject responsObject = null;
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

}
