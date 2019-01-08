package com.animal.scale.hodoo.common;

import android.content.Context;

import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonNotificationData {
    private SharedPrefManager mSharedPrefManager;
    CommonNotificationData ( Context context ) {
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public static CommonNotificationData getInstance ( Context context ) {
        return new CommonNotificationData(context);
    }
    public int getBadgeCount () {
        Map<String, String> firebaseInfos = (Map<String, String>) VIewUtil.fromJson( mSharedPrefManager.getStringExtra(SharedPrefVariable.FIREBASE_NOTI), new TypeToken< Map<String,String>>(){}.getType());
        List<InvitationUser> invitationUsers = new ArrayList<>();
        if ( firebaseInfos != null ) {
            String invitationStr = firebaseInfos.get( String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE) );
            if ( invitationStr != null || !invitationStr.equals("") )
                invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(invitationStr, new TypeToken<List<InvitationUser>>(){}.getType());

        }
        return invitationUsers.size();
    }
}
