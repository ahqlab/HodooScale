package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.WeightGoalChart;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

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
    }
    public void updatePhysical(PetPhysicalInfo info, final CommonModel.ObjectCallBackListner<CommonResponce<PetPhysicalInfo>> callBackListner) {
        Call<CommonResponce<PetPhysicalInfo>> call = NetRetrofit.getInstance().getPetPhysicalInfoService().update(sharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX), sharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE),info);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<PetPhysicalInfo>>() {

            @Override
            protected void doPostExecute(CommonResponce<PetPhysicalInfo> mealHistoryCommonResponce) {
                callBackListner.doPostExecute(mealHistoryCommonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void getDevice(final DomainListCallBackListner<Device> callback ) {
        Call<List<Device>> call = NetRetrofit.getInstance().getDeviceService().getMyDeviceList(sharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList() {
            @Override
            protected void doPostExecute(List d) {

            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void getPetAllInfos (int petIdx, final CommonModel.ObjectCallBackListner<CommonResponce<PetAllInfos>> callback) {
        Call<CommonResponce<PetAllInfos>> call = NetRetrofit.getInstance().getPetService().petAllInfosForAndroid(petIdx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<PetAllInfos>>() {
            @Override
            protected void doPostExecute(CommonResponce<PetAllInfos> petAllInfosCommonResponce) {
                callback.doPostExecute(petAllInfosCommonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void getWeightGoal (float currentWeight, int bodyFat, int petType, final CommonModel.ObjectCallBackListner<WeightGoalChart> callback) {
        Call<CommonResponce<WeightGoalChart>> call = NetRetrofit.getInstance().getBodyFatRiskService().getWeightGoal(currentWeight, bodyFat, petType);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<WeightGoalChart>>() {
            @Override
            protected void doPostExecute(CommonResponce<WeightGoalChart> weightGoalChartCommonResponce) {
                callback.doPostExecute(weightGoalChartCommonResponce.domain);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void setAverageWeight( String weight ) {
        sharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, weight);
    }
    public String getAverageWeight() {
        return sharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT);
    }
}
