package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public class DashBoardModel extends CommonModel {
    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getTodaySumCalorie(String date, final CommonModel.ObjectCallBackListner<CommonResponce<MealHistory>> callBackListner) {
        Call<CommonResponce<MealHistory>> call = NetRetrofit.getInstance().getMealHistoryService().getAndroidTodaySumCalorie(sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX), date);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<MealHistory>>() {

            @Override
            protected void doPostExecute(CommonResponce<MealHistory> mealHistoryCommonResponce) {
                callBackListner.doPostExecute(mealHistoryCommonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
//        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<MealHistory>() {
//            @Override
//            protected void doPostExecute(MealHistory mealHistory) {
//                domainCallBackListner.doPostExecute(mealHistory);
//            }
//
//            @Override
//            protected void doPreExecute() {
//
//            }
//
//            @Override
//            protected void doCancelled() {
//
//            }
//        }.execute(call), limitedTime, interval, true).start();
    }
}
