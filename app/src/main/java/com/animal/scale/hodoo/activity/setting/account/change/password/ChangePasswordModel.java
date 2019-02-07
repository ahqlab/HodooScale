package com.animal.scale.hodoo.activity.setting.account.change.password;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class ChangePasswordModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void changeUserPassword(User user, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().updateUsetPassword(user);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                domainCallBackListner.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
