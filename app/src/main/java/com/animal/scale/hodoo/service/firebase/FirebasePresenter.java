package com.animal.scale.hodoo.service.firebase;

import android.content.Context;

import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebasePresenter implements FirebaseIn.Presenter {
    private static final String TAG = FirebasePresenter.class.getSimpleName();
    private FirebaseIn.View mView;
    private FirebaseModel mModel;
    FirebasePresenter ( FirebaseIn.View view ) {
        mView = view;
        mModel = new FirebaseModel();
    }

    @Override
    public void initDate(Context context) {
        mModel.loadData(context);
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

        switch (type) {
            case HodooConstant.FIREBASE_NORMAL_TYPE :
                badgeCount += 1;
                break;
            case HodooConstant.FIREBASE_INVITATION_TYPE:
                int count = 0;
                Map<String, String> firebaseInfos = mModel.getFirebaseInfos();
                if ( firebaseInfos != null ) {
                    List<InvitationUser> invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(firebaseInfos.get(String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE)), new TypeToken<List<InvitationUser>>(){}.getType());
                    if ( invitationUsers != null )
                        count += invitationUsers.size();
                }
                badgeCount += count;
                break;
        }


        mView.setBadge(badgeCount);

    }

    @Override
    public void saveBadgeCount(int count) {
        mModel.saveBadge(count);
        mView.sendBroad();
    }


}
