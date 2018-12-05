package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FeedListModel extends CommonModel {

    Context context;

    private SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getList(String date, final DomainListCallBackListner<MealHistoryContent> domainListCallBackListner) {
        Call<List<MealHistoryContent>> call = NetRetrofit.getInstance().getMealHistoryService().getList(date, sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AbstractAsyncTaskOfList<MealHistoryContent>() {
            @Override
            protected void doPostExecute(List<MealHistoryContent> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
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
}
