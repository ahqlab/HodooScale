package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;


public class WelcomeHomeModel extends CommonModel {

    private SharedPrefManager sharedPrefManager;

    public void initData(Context context) {
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public void removeAllPref() {
        sharedPrefManager.removeAllPreferences();
    }


    public void doSnsLogin(User user, final ObjectCallBackListner<CommonResponce<User>> commonDomainCallBackListner) {
        retrofit2.Call<CommonResponce<User>> call = NetRetrofit.getInstance().getUserService().doSnsRegist(user);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<User>>() {
            @Override
            protected void doPostExecute(CommonResponce<User> commonResponce) {
                commonDomainCallBackListner.doPostExecute(commonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

}
