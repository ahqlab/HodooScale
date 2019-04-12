package com.animal.scale.hodoo.activity.user.signup;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SignUpModel extends CommonModel {

    public void registUser(User user, final ObjectCallBackListner<CommonResponce<User>> domainCallBackListner) {
        Call<CommonResponce<User>> call = NetRetrofit.getInstance().getUserService().registUser(user);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<User>>() {

            @Override
            protected void doPostExecute(CommonResponce<User> userCommonResponce) {
                domainCallBackListner.doPostExecute(userCommonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }


    public void userCertifiedMailSend (String toMailAddr, final DomainCallBackListner callback) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().userCertifiedMailSend(toMailAddr);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                callback.doPostExecute(integer);
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
