package com.animal.scale.hodoo.test.server;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class TestServerResponseModel extends CommonModel {
    @Override
    public void loadData(Context context) {
        super.loadData(context);
    }


    public void sendNoti( String toUserEmail, final DomainCallBackListner<Integer> callback ) {
       /* Call<Integer> call = NetRetrofit.getInstance().getFcmService().normalPush("TestServerResponseIn Notification", "testContent", toUserEmail);
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
        }.execute(call), limitedTime, interval, true).start();*/
    }
}
