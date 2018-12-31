package com.animal.scale.hodoo.activity.user.reset.password.send;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class SendCertificationNumberModel extends CommonModel {


    public void sendTempPassword(User user, final  DomainCallBackListner<ResultMessageGroup> commonResponceDomainCallBackListner) {
        Call<ResultMessageGroup> call = NetRetrofit.getInstance().getUserService().findUserPassword(user.getEmail());
        new AbstractAsyncTask<ResultMessageGroup>() {
            @Override
            protected void doPostExecute(ResultMessageGroup resultMessageGroup) {
                commonResponceDomainCallBackListner.doPostExecute(resultMessageGroup);
            }

            @Override
            protected void doPreExecute() {
                commonResponceDomainCallBackListner.doPreExecute();
            }
        }.execute(call);
    }
}
