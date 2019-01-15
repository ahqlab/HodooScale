package com.animal.scale.hodoo.activity.setting.device.feeder;

import android.content.Context;
import android.content.Intent;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.FeedOrders;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class FeederOrderModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getPetAllInfo(final DomainCallBackListner<PetAllInfos> domainCallBackListner) {
        Call<PetAllInfos> call = NetRetrofit.getInstance().getPetService().petAllInfos(sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AbstractAsyncTask<PetAllInfos>() {
            @Override
            protected void doPostExecute(PetAllInfos petAllInfos) {
                domainCallBackListner.doPostExecute(petAllInfos);
            }
            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }

    public void insertOrder(float rer, final DomainCallBackListner<Integer> domainCallBackListner) {
        String groupCode = sharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE);
        int userIdx = sharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        int feedIdx = 2472;
        int petIdx = sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX);

        FeedOrders feedOrders = new FeedOrders();
        feedOrders.setGroupCode(groupCode);
        feedOrders.setUserIdx(userIdx);
        feedOrders.setFeedIdx(feedIdx);
        feedOrders.setPetIdx(petIdx);
        feedOrders.setRer(rer);

        Call<Integer> call = NetRetrofit.getInstance().getFeederService().insert(feedOrders);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                domainCallBackListner.doPostExecute(integer);
            }
            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
}
