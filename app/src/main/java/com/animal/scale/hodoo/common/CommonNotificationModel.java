package com.animal.scale.hodoo.common;

import android.content.Context;

import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommonNotificationModel {
    private SharedPrefManager mSharedPrefManager;
    private Context mContext;
    CommonNotificationModel(Context context ) {
        mSharedPrefManager = SharedPrefManager.getInstance(context);
        mContext = context;
    }
    public static CommonNotificationModel getInstance (Context context ) {
        return new CommonNotificationModel(context);
    }
    public int getBadgeCount () {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.BADGE_COUNT);
    }
    public int getInvitationBadgeCount() {
        Map<String, String> firebaseInfos = (Map<String, String>) VIewUtil.fromJson( mSharedPrefManager.getStringExtra(SharedPrefVariable.FIREBASE_NOTI), new TypeToken< Map<String,String>>(){}.getType());
        List<InvitationUser> invitationUsers = new ArrayList<>();
        if ( firebaseInfos != null ) {
            String invitationStr = firebaseInfos.get( String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE) );
            if ( invitationStr != null || !invitationStr.equals("") )
                invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(invitationStr, new TypeToken<List<InvitationUser>>(){}.getType());

        }
        return invitationUsers.size();
    }
    public void removeInvitationData ( int to, int from ) {
        Map<String, String> firebaseInfos = (Map<String, String>) VIewUtil.fromJson( mSharedPrefManager.getStringExtra(SharedPrefVariable.FIREBASE_NOTI), new TypeToken< Map<String,String>>(){}.getType());
        List<InvitationUser> invitationUsers = new ArrayList<>();
        Gson gson = new Gson();
        if ( firebaseInfos != null ) {
            String invitationStr = firebaseInfos.get(String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE));
            if ( invitationStr != null || !invitationStr.equals("") ) {
                invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(invitationStr, new TypeToken<List<InvitationUser>>(){}.getType());
                for (int i = 0; i < invitationUsers.size(); i++) {
                    if ( invitationUsers.get(i).getToUserIdx() == to && invitationUsers.get(i).getFromUserIdx() == from ) {
                        invitationUsers.remove(i);
                    }
                }
            }
            firebaseInfos.put(String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE), gson.toJson(invitationUsers));
            mSharedPrefManager.putStringExtra(SharedPrefVariable.FIREBASE_NOTI, gson.toJson(firebaseInfos));
            mSharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, getInvitationBadgeCount());
            BadgeUtils.setBadge(mContext, getInvitationBadgeCount());
        }
    }
    public List<InvitationUser> getSavedinvitationUsers () {
        Map<String, String> firebaseInfos = (Map<String, String>) VIewUtil.fromJson( mSharedPrefManager.getStringExtra(SharedPrefVariable.FIREBASE_NOTI), new TypeToken< Map<String,String>>(){}.getType());
        List<InvitationUser> invitationUsers = new ArrayList<>();
        if ( firebaseInfos != null ) {
            String invitationStr = firebaseInfos.get( String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE) );
            if ( invitationStr != null || !invitationStr.equals("") )
                invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(invitationStr, new TypeToken<List<InvitationUser>>(){}.getType());

        }
        return invitationUsers;
    }
}
