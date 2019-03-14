package com.animal.scale.hodoo.constant;

public class HodooConstant {

    public static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 100;

    public static final boolean DEBUG = true;

    public static final String IN_APP_SETTING_KEY = "in_app_setting_key";

    /* 초대관련 (s) */
    public static final int ACCEPT_TYPE = 1;
    public static final int DECLINE_TYPE = 2;

    public static final String INVITATION_EMAIL_KEY = "INVITATION_EMAIL_KEY";
    /* 초대관련 (e) */

    /* fcm 관련 (s) */
    public static final String FCM_RECEIVER_NAME = "HodooFCMReceiver";

    public static final String REMOVE_NOTI_KEY = "REMOVE_NOTI_KEY";
    public static final String NOTI_TYPE_KEY = "NOTI_TYPE_KEY";
    public static final String REMOVE_NOTI_ID_KEY = "REMOVE_NOTI_ID_KEY";

    public static final int FIREBASE_NORMAL_TYPE = 0;
    public static final int FIREBASE_WEIGHT_TYPE = 1;
    public static final int FIREBASE_FEED_TYPE = 2;
    public static final int FIREBASE_INVITATION_TYPE = 3;
    public static final int FIREBASE_INVITATION_ACCEPT = 4;


    public static final int FAIL_CODE = -1;
    public static final int SUCCESS_CODE = 1;

    public static final int MEMBER_EXIST = -2;
    public static final int NOT_GROUP_MASTER = 2;

    /* oreo version notification channel (s) */
    public static final String NORMAL_CHANNEL = "notice_channel";
    public static final String WEIGHT_CHECK_CHANNEL = "weight_check_channel";
    public static final String FEED_CHECK_CHANNEL = "feed_check_channel";
    public static final String INVITATION_GROUP_CHANNEL = "invitation_group_channel";
    public static final String INVITATION_ACCEPT_CHANNEL = "invitation_accept_channel";
    /* oreo version notification channel (e) */
    /* fcm 관련 (e) */
    public static final int AUTO_LOGIN_SUCCESS = 1;


    public static final int WITHDRAW = -1;

    public static final String CHANNEL_ID = "HODOO_CHANNEL";

    /* Retrofit 관련 (s) */
    public static final int LIMITED_TIME = 60000;
    public static final int INTERVAL = 1000;
    /* Retrofit 관련 (e) */



}
