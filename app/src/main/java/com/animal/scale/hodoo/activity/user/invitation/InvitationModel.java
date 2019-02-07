package com.animal.scale.hodoo.activity.user.invitation;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class InvitationModel extends CommonModel {
    public SharedPrefManager mSharedPrefManager;
    private Context context;
    @Override
    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public String getUserEmail () {
        return mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_EMAIL);
    }
    public void setInvitationUser ( String to ) {
        mSharedPrefManager.putBooleanExtra(SharedPrefVariable.INVITATION_STATE, true);
        mSharedPrefManager.putStringExtra(SharedPrefVariable.INVITATION_USER_EMAIL, to);
    }
    public void sendInvitation ( String to, String from, final InvitationModel.DomainCallBackListner<Integer> callback ) {
        Call<Integer> call = NetRetrofit.getInstance().getFcmService().sendInvitation(to, from);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                callback.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
    public void removeAutoLogin() {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.AUTO_LOGIN, 0);
    }
}
