package com.animal.scale.hodoo.activity.home.fragment.dashboard;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.WeightGoalChart;

import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-22.
 */
public class DashBoardPresenter implements DashBoardIn.Presenter {
    private DashBoardIn.View view;
    private DashBoardModel model;
    DashBoardPresenter ( DashBoardIn.View view ) {
        this.view = view;
    }

    @Override
    public void initData( Context context ) {
        model = new DashBoardModel();
        model.loadData( context );
    }

    @Override
    public void getTodaySumCalorie(String date) {
        model.getTodaySumCalorie(date, new CommonModel.ObjectCallBackListner<CommonResponce<MealHistory>>() {
            @Override
            public void doPostExecute(CommonResponce<MealHistory> mealHistoryCommonResponce) {
                view.setMealHistory(mealHistoryCommonResponce.domain);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void updatePhysical(PetPhysicalInfo info) {
        model.updatePhysical(info, new CommonModel.ObjectCallBackListner<CommonResponce<PetPhysicalInfo>>() {
            @Override
            public void doPostExecute(CommonResponce<PetPhysicalInfo> integerCommonResponce) {
                view.physicalUpdateDone(integerCommonResponce.domain);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getDevice() {
        model.getDevice(new CommonModel.DomainListCallBackListner<Device>() {
            @Override
            public void doPostExecute(List<Device> d) {
                view.setDevice(d);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getGoalWeight(float currentWeight, int bodyFat, int petType) {
        model.getWeightGoal(currentWeight, bodyFat, petType, new CommonModel.ObjectCallBackListner<WeightGoalChart>() {
            @Override
            public void doPostExecute(WeightGoalChart weightGoalChart) {
                view.setWeightGoal(weightGoalChart);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void setAverageWeight(String weight) {
        model.setAverageWeight(weight);
    }

    @Override
    public void getPetAllInfos(int petIdx) {
        model.getPetAllInfos(petIdx, new CommonModel.ObjectCallBackListner<CommonResponce<PetAllInfos>>() {
            @Override
            public void doPostExecute(CommonResponce<PetAllInfos> petAllInfosCommonResponce) {
                view.setPetAllInfos(petAllInfosCommonResponce.domain);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public String getTodayAverageWeight() {
        return model.getAverageWeight();
    }
}
