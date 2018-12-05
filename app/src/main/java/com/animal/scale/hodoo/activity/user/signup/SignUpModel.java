package com.animal.scale.hodoo.activity.user.signup;

import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SignUpModel extends CommonModel {

    public void registUser(User user, final DomainCallBackListner<ResultMessageGroup> domainCallBackListner) {
        Call<ResultMessageGroup> call = NetRetrofit.getInstance().getUserService().registUser(user);
        new AbstractAsyncTask<ResultMessageGroup>() {
            @Override
            protected void doPostExecute(ResultMessageGroup resultMessageGroup) {
                domainCallBackListner.doPostExecute(resultMessageGroup);
            }
            @Override
            protected void doPreExecute() {
            }
        }.execute(call);


        /*result.enqueue(new Callback<ResultMessageGroup>() {
            @Override
            public void onResponse(Call<ResultMessageGroup> call, Response<ResultMessageGroup> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if((int) Double.parseDouble(response.body().getDomain().toString()) == 1){
                            goNextPage();
                        }else{
                            Toast.makeText(getApplicationContext(), getString(R.string.user_regist_failed_message), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.user_regist_failed_message), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResultMessageGroup> call, Throwable t) {
            }
        });*/
    }
}
