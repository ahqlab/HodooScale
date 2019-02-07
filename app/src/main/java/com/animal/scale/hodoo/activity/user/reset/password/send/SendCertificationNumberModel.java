package com.animal.scale.hodoo.activity.user.reset.password.send;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class SendCertificationNumberModel extends CommonModel {


    public void sendTempPassword(User user, final  DomainCallBackListner<CommonResponce<User>> commonResponceDomainCallBackListner) {
        Call<CommonResponce<User>> call = NetRetrofit.getInstance().getUserService().findUserPassword(user.getEmail());
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<User>>() {
            @Override
            protected void doPostExecute(CommonResponce<User> userCommonResponce) {
                commonResponceDomainCallBackListner.doPostExecute(userCommonResponce);
            }

            @Override
            protected void doPreExecute() {
                commonResponceDomainCallBackListner.doPreExecute();
            }


        }.execute(call), limitedTime, interval, true).start();
    }
}
