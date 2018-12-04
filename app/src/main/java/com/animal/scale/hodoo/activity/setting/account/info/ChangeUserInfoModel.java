package com.animal.scale.hodoo.activity.setting.account.info;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class ChangeUserInfoModel extends CommonModel {

    Context context;

    SharedPrefManager sharedPrefManager;

    public void initLoadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void initUserData(final DomainCallBackListner<User> domainCallBackListner) {
        Call<User> call = NetRetrofit.getInstance().getUserService().get(sharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID));
        new AbstractAsyncTask<User>() {
            @Override
            protected void doPostExecute(User user) {
                domainCallBackListner.doPostExecute(user);
            }
            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }

    public void updateBasicInfo(User user, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().updateBasicInfo(user);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                domainCallBackListner.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
}
