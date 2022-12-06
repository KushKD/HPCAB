package com.dit.himachal.rms.enums;

/**
 * @author Kush.Dhawan
 * @project eCabinet
 * @Time 21, 08 , 2020
 */
public enum TaskType {
    GET_ROLES(1),
    GET_DEPARTMENTS_VIA_ROLES(2),
    GET_BRANCHES(3),
    GET_USERS(4),
    GET_OTP_VIA_MOBILE(5),
    LOGIN(5),
    GET_MENU_LIST(6),
    CABINET_MEETING_STATUS(7),
    GET_PENDING_MEMO_LIST_CABINET(8),
    CABINET_MEMOS_DETAILS(9),
    SEND_BACK(10),
    FORWARD(11),
    ALLOW(12),
    GET_ALLOWED_MEMO_LIST_CABINET(13),
    GET_PUBLISHED_MEETING_ID_BY_ROLE(14),
    FINAL_MEETING_AGENDA_LIST(15),
    GET_CABINET_DECISIONS_COUNT(16),
    GET_ACTION(17),
    GET_SENT_BACK_TO(18),

    Get_Registered_Advocates_List(19),
    Get_Archive_Cases(20),
    Get_Advocate_Subscribed_Cause_List(21),
    Get_Case_Notices_By_Advocate(22),
    Get_Subscribed_Cases(23),
    Get_Zimni_Orders_Advocate(24);

    int value;

    TaskType(int value) {
        this.value = value;
    }
}
