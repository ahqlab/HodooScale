package com.animal.scale.hodoo.service.firebase;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

public class FirebasePresenter implements FirebaseIn.Presenter {
    private static final String TAG = FirebasePresenter.class.getSimpleName();
    private FirebaseIn.View mView;
    private FirebaseModel mModel;
    private CommonNotificationModel notificationModel;
    FirebasePresenter ( FirebaseIn.View view ) {
        mView = view;
        mModel = new FirebaseModel();
    }

    @Override
    public void initDate(Context context) {
        mModel.loadData(context);
        notificationModel = CommonNotificationModel.getInstance(context);
    }

    @Override
    public void getData( Map<String, String> data ) {
        Map<String, String> firebaseInfos = mModel.getFirebaseInfos();

        String notiTypeStr = data.get("notiType");
        int type = 0;
        if ( notiTypeStr != null || !notiTypeStr.equals("") )
            type = Integer.parseInt( notiTypeStr );

        switch (type) {
            case HodooConstant.FIREBASE_NORMAL_TYPE :


                break;
            case HodooConstant.FIREBASE_INVITATION_TYPE:

                int toUserIdx = Integer.parseInt(data.get("toUserIdx"));
                int fromUserIdx = Integer.parseInt(data.get("fromUserIdx"));

                Gson gson = new Gson();

                List<InvitationUser> invitationUsers = new ArrayList<>();

                if ( firebaseInfos != null ) {
                    switchStatement : switch ( type ) {
                        case HodooConstant.FIREBASE_INVITATION_TYPE :
                            String invitationStr = firebaseInfos.get( String.valueOf(type) );
                            invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(invitationStr, new TypeToken<List<InvitationUser>>(){}.getType());

                            for ( InvitationUser user : invitationUsers) {
                                if ( user.getToUserIdx() == toUserIdx && user.getFromUserIdx() == fromUserIdx )
                                    break switchStatement;
                            }
                            InvitationUser user = InvitationUser.builder()
                                    .toUserIdx( toUserIdx )
                                    .fromUserIdx( fromUserIdx )
                                    .build();
                            invitationUsers.add(user);
                            firebaseInfos.put( String.valueOf(type), gson.toJson(invitationUsers) );
                            mModel.setData( gson.toJson(firebaseInfos) );
                            break;
                    }
                } else {
                    firebaseInfos = new HashMap<>();
                    invitationUsers = new ArrayList<>();
                    InvitationUser user = InvitationUser.builder()
                            .toUserIdx(toUserIdx)
                            .fromUserIdx(fromUserIdx)
                            .build();
                    invitationUsers.add(user);

                    firebaseInfos.put( String.valueOf(type), gson.toJson(invitationUsers));
                    mModel.setData( gson.toJson(firebaseInfos) );
                }
                break;
        }

        /* send noti (s) */
        mView.sendNotification(data);
        /* send noti (e) */

    }

    @Override
    public void countingBadge( int type, int badgeCount ) {
        mView.setBadge(badgeCount + 1);

    }

    @Override
    public void saveBadgeCount(int count) {
        mModel.saveBadge(count);
        mView.sendBroad();
    }

    @Override
    public void setInvitationUser(int to, int from) {
        notificationModel.setInvitationData(to, from);
        getInvitationUsers();
    }

    @Override
    public void getInvitationUsers() {
        List<InvitationUser> users = notificationModel.getInvitationUsers();
        if ( DEBUG ) Log.e(TAG, "debug");
    }


}
