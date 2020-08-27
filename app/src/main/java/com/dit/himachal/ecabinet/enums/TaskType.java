package com.dit.himachal.ecabinet.enums;

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
    FORWARD(11);

    int value; private TaskType(int value) { this.value = value; }
}
