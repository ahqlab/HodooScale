package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.content.Context;

import com.animal.scale.hodoo.activity.setting.user.group.list.UserGroupListModel;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class InvitationConfirmModel extends CommonModel {
    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public void setInvitationState(int state, int toUserIdx, int fromUseridx, final UserGroupListModel.DomainCallBackListner<Integer> callback) {
        Call<Integer> call = NetRetrofit.getInstance().getInvitationService().setInvitationType(state, toUserIdx, fromUseridx);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                callback.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
    public void invitationApproval ( int toUserIdx, int fromUserIdx, final InvitationConfirmModel.DomainCallBackListner<Integer> callback ) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().invitationApproval(toUserIdx, fromUserIdx);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                callback.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
    public void invitationRefusal ( int to, int from, final InvitationConfirmModel.DomainCallBackListner<Integer> callback ) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().invitationRefusal(to, from);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                callback.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }

    public int updateBadgeCount ( int to, int from ) {
        Map<String, String> firebaseInfos = (Map<String, String>) VIewUtil.fromJson( mSharedPrefManager.getStringExtra(SharedPrefVariable.FIREBASE_NOTI), new TypeToken< Map<String,String>>(){}.getType());
        List<InvitationUser> invitationUsers = new ArrayList<>();
        Gson gson = new Gson();
        if ( firebaseInfos != null ) {
            String invitationStr = firebaseInfos.get( String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE) );
            if ( invitationStr != null || !invitationStr.equals("") ) {
                boolean state = false;
                invitationUsers = (List<InvitationUser>) VIewUtil.fromJson(invitationStr, new TypeToken<List<InvitationUser>>(){}.getType());
                for (int i = 0; i < invitationUsers.size(); i++) {
                    if ( invitationUsers.get(i).getToUserIdx() == to && invitationUsers.get(i).getFromUserIdx() == from ) {
                        invitationUsers.remove(i);
                        firebaseInfos.put(String.valueOf(HodooConstant.FIREBASE_INVITATION_TYPE), gson.toJson(invitationUsers));
                        state = true;
                    }
                }
                if ( state ) {

                    mSharedPrefManager.putStringExtra(SharedPrefVariable.FIREBASE_NOTI, gson.toJson(firebaseInfos));
                }
            }

        }
        return invitationUsers.size();
    }
    public void saveBadgeCount( int count ) {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, count);
        BadgeUtils.setBadge(context, count);
    }

    public void getInvitationList (final UserGroupListModel.DomainListCallBackListner<InvitationUser> callback) {
        int userIdx = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        Call<List<InvitationUser>> call = NetRetrofit.getInstance().getInvitationService().getInvitationUser(userIdx);
        new AbstractAsyncTaskOfList<InvitationUser>() {
            @Override
            protected void doPostExecute(List<InvitationUser> users) {
                callback.doPostExecute(users);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
}
