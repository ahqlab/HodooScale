package com.animal.scale.hodoo.common;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.activity.user.invitation.Invitation;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

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

    /* new code 2019.01.15 song (s) */
    public void setInvitationData(int to, int from ) {
        String usersStr = mSharedPrefManager.getStringExtra(SharedPrefVariable.INVITATION_USERS);
        List<InvitationUser> users = new ArrayList<>();
        InvitationUser user = InvitationUser.builder()
                .toUserIdx(to)
                .fromUserIdx(from)
                .build();
        if ( usersStr != null && !usersStr.equals("") ) {
            users = (List<InvitationUser>) VIewUtil.fromJson(usersStr, new TypeToken<List<InvitationUser>>(){}.getType());
            for (int i = 0; i < users.size(); i++) {
                if ( users.get(i).getToUserIdx() == to && users.get(i).getFromUserIdx() == from )
                    return;
            }
        }
        Gson gson = new Gson();
        users.add(user);
        mSharedPrefManager.putStringExtra(SharedPrefVariable.INVITATION_USERS, gson.toJson(users));
    }
    public void setAllInvitationUsers ( List<InvitationUser> users ) {
        Gson gson = new Gson();
        mSharedPrefManager.putStringExtra(SharedPrefVariable.INVITATION_USERS, gson.toJson(users));
    }
    public List<InvitationUser> getInvitationUsers() {
        String usersStr = mSharedPrefManager.getStringExtra(SharedPrefVariable.INVITATION_USERS);
        if ( usersStr != null || !usersStr.equals("") ) {
            return (List<InvitationUser>) VIewUtil.fromJson(usersStr, new TypeToken<List<InvitationUser>>(){}.getType());
        }
        return null;
    }
    public int getInvitationCount() {
        String usersStr = mSharedPrefManager.getStringExtra(SharedPrefVariable.INVITATION_USERS);
        if ( usersStr != null && !usersStr.equals("") ) {
            List<InvitationUser> users = (List<InvitationUser>) VIewUtil.fromJson(usersStr, new TypeToken<List<InvitationUser>>(){}.getType());
            return users.size();
        }
        return 0;
    }
    public void removeInvitationUser( int to, int from ) {
        List<InvitationUser> users = getInvitationUsers();
        for (int i = 0; i < users.size(); i++)
            if ( users.get(i).getToUserIdx() == to && users.get(i).getFromUserIdx() == from )
                users.remove(i);
        mSharedPrefManager.putStringExtra(SharedPrefVariable.INVITATION_USERS, new Gson().toJson(users));
    }
    /* new code 2019.01.15 song (e) */









}
