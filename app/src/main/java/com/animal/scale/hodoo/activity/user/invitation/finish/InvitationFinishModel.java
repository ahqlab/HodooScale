package com.animal.scale.hodoo.activity.user.invitation.finish;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class InvitationFinishModel extends CommonModel {
    private SharedPrefManager mSharedPrefManager;
    public InvitationFinishModel ( Context context ) {
        loadData(context);
    }
    @Override
    public void loadData(Context context) {
        super.loadData(context);
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public void resend( String to, final InvitationFinishModel.DomainCallBackListner<Integer> callback ) {
        String from = mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_EMAIL);
        Call<Integer> call = NetRetrofit.getInstance().getFcmService().sendInvitation(to, from);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                callback.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void cancel (String to, final InvitationFinishModel.DomainCallBackListner<Integer> callback ) {
        int from = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        Call<Integer> call = NetRetrofit.getInstance().getFcmService().cancelInvitation(to, from);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                if ( integer > 0 ) {
                    mSharedPrefManager.removePreference(SharedPrefVariable.INVITATION_STATE);
                    mSharedPrefManager.removePreference(SharedPrefVariable.INVITATION_USER_EMAIL);
                }
                callback.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
