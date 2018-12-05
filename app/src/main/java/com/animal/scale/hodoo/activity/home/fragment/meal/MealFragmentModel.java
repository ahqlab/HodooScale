package com.animal.scale.hodoo.activity.home.fragment.meal;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class MealFragmentModel extends CommonModel {

    public void getRadarChartData(String currentDatetime, final DomainCallBackListner<Feed> feedDomainCallBackListner) {
        Call<Feed> call = NetRetrofit.getInstance().getFeedService().getRadarChartData(currentDatetime, sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AbstractAsyncTask<Feed>() {
            @Override
            protected void doPostExecute(Feed feed) {
                feedDomainCallBackListner.doPostExecute(feed);
            }
            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
}
