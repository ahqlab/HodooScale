package com.animal.scale.hodoo.activity.home.fragment.meal;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class MealFragmentModel extends CommonModel {

    public void getRadarChartData(String currentDatetime, final DomainCallBackListner<Feed> feedDomainCallBackListner) {
        Call<Feed> call = NetRetrofit.getInstance().getFeedService().getRadarChartData(currentDatetime, sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Feed>() {
            @Override
            protected void doPostExecute(Feed feed) {
                feedDomainCallBackListner.doPostExecute(feed);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }


    public void getTipMessageOfCountry(MealTip mealTip, final DomainCallBackListner<MealTip> domainListCallBackListner) {
        Call<MealTip> call = NetRetrofit.getInstance().getMealTipService().getTipOfCountry(mealTip);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<MealTip>() {
            @Override
            protected void doPostExecute(MealTip tip) {
                domainListCallBackListner.doPostExecute(tip);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getLastCollectionData(String date, int type, final DomainCallBackListner<RealTimeWeight> domainCallBackListner) {
        Call<RealTimeWeight> call = NetRetrofit.getInstance().getRealTimeWeightService().getLastCollectionData(date, sharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), type, sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<RealTimeWeight>() {
            @Override
            protected void doPostExecute(RealTimeWeight realTimeWeight) {
                domainCallBackListner.doPostExecute(realTimeWeight);
            }

            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {
                domainCallBackListner.doCancelled();
            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
