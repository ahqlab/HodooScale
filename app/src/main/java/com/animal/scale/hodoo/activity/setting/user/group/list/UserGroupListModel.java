package com.animal.scale.hodoo.activity.setting.user.group.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.user.invitation.confirm.InvitationConfirmModel;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class UserGroupListModel extends CommonModel {
    private SharedPrefManager mSharedPrefManager;
    @Override
    public void loadData(Context context) {
        super.loadData(context);
        mSharedPrefManager = SharedPrefManager.getInstance(context);
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
    public void setNotificationData (  ) {

    }
}
