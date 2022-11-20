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
    //URL Staging
     public static final String url = "https://staging8.hp.gov.in/rmsservice.svc";



    public static String delemeter = "/";
    public static String seperator = "#";

    private static String Color_Grey = "#000000";


    public static String advaocateLogin= "2";
    public static String citizenLogin= "1";



    public static final String methordLogin = "GetLogin";
    public static final String methordLoginToken = "DFGG16E4C7F2C4F694A52D20ED2A520";

    public static final String getDepartmentsViaRoles ="DepartmentsListByRole";
    public static final String getDepartmentsViaRolesToken ="ccaa314e7421e7e12f5cf7171081a0ddd";

    public static final String methordMenuList ="GetMenuList";
    public static final String methordMenuListToken ="KKdd33444444444frrrr1081a0";

    public static final String methordGetOnlineCabinetIDMeetingStatus ="ActiveCabinetMemoDuringMeeting";
    public static final String methordGetOnlineCabinetIDMeetingToken ="ddddaccssaa314e7421e7e12f5cf7171081arrr";



    public static final String methordPublishedMeetingDatesListByRole ="PublishedMeetingDatesListByRole";
    public static final String methordPublishedMeetingDatesListByRoleToken ="da314e7421e7e12f5cf7171081a0ddd";

    public static final String methordFinalMeetingAgendaList ="FinalMeetingAgendalist";
    public static final String methordFinalMeetingAgendaListToken ="khjlhifutrdfgf2344hhyi666issdfddd345";



    public static final String methordCabinetMemoListByRole = "CabinetMemoListByRole";
    public static final String methordCabinetMemoListByToken = "dsssaccaa314e7421e7e12f5cf7171081a0ss";

    public static final String methordCabinetMemoDetails = "CabinetMemoDetails";
    public static final String methordCabinetMemoDetailsToken = "wwwdaccaa314e7421e7e12f5cf7171081a0ss";


    public static final String methordsendBackCabinetMemoLists = "SentBackCabinetMemo";
    public static final String methordsendBackCabinetMemoListsToken = "ttttdaccaa314e7421e7e12f5cf7171081a0fff";

    public static final String methordForwardCabinetMemo = "ForwardCabinetMemo";
    public static final String methordForwardCabinetMemoToken = "hhhdaccaa314e7421e7e12f5cf7171081a0fff";

    public static final String methordForwardedCabinetMemoListByRole = "ForwardedCabinetMemoListByRole";
    public static final String methordForwardedCabinetMemoListByRoleToken = "ssdaccaa314e7421e7e12f5cf7171081a0ss";


    public static final String methordSentBackCabinetMemoListByRole = "SentBackCabinetMemoListByRole";
    public static final String methordSentBackCabinetMemoListByRoleToken = "daddccaa314e7421e7e12f5cf7171081a0ss";

    public static final String methordCabinetMemoDetailsOther = "CabinetFSMemoDetails";
    public static final String methordCabinetMemoDetailsTokenOther = "YYjj098979YTHKKDRGGGGHHHH";

    public static final String methordAllowedCabinetMemo = "AllowedCabinetMemo";
    public static final String methordAllowedCabinetMemoToken = "ghsssaccaa314e7421e7e12f5cf0sseeddd";


    public static final String PlaceinCabinetagendalists = "PlaceinCabinetagendalists";
    public static final String PlaceinCabinetagendalistsToken = "ssdaccaa314e7421e7e12f5cf7171081a0ss";

    //CabinetDecisionlists/{Token}/{DeptID}/{UserID}/{RoleID}/{LoginDeptID}/{Meetingid}
    public static final String CabinetDecisionlists = "CabinetDecisionlists";
    public static final String CabinetDecisionlistsToken = "69FBBF62A31CBC7C761B0AB96696C9EB";

    //GetCabinetDecisionbyMeetingDates
    public static final String GetCabinetDecisionbyMeetingDates = "GetCabinetDecisionbyMeetingDates";
    public static final String GetCabinetDecisionbyMeetingDatesToken = "da314e7421e7e12f5cf7171081a0ddd";

    //GetChannelistbyRole Action
    public static final String GetChannelistbyRole = "GetChannelistbyRole";
    public static final String GetChannelistbyRoleToken = "3GFHF5BE7CB28FF963C57D51E6D3FGHYGTRDF";

    //GetSectListsbyDeptBranch Sent back to
    public static final String GetSectListsbyDeptBranch = "GetSectListsbyDeptBranch";
    public static final String GetSectListsbyDeptBranchToken = "893GFHF5BE7CB28FF963C57D51E6D3FGHYGTRDF";

    //GetSentBackListsbyDeptBranch
    public static final String GetSentBackListsbyDeptBranch = "GetSentBackListsbyDeptBranch";
    public static final String GetSentBackListsbyDeptBranchToken = "3000GFHF5BE7CB2IJHUGYHJIKKKIJJOKKJIKKOIK";


    public static final String NO_DATA = "No Data Available. Please Connect to Internet and try again !";


    public static final String success = "SUCCESS";
    public static final String failure = "FAILURE";



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
