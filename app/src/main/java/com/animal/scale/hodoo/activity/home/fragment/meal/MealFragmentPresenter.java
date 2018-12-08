package com.animal.scale.hodoo.activity.home.fragment.meal;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public class MealFragmentPresenter implements MealFragmentIn.Presenter{

    MealFragmentIn.View view;

    MealFragmentModel model;

    MealRegistrationModel mealRegistrationModel;

    public MealFragmentPresenter(MealFragmentIn.View view) {
        this.view = view;
        this.model = new MealFragmentModel();
        this.mealRegistrationModel = new MealRegistrationModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        mealRegistrationModel.loadData(context);
    }

    @Override
    public void initRaderChart() {
        view.initRaderChart();
    }

    @Override
    public void getRadarChartData(String currentDatetime) {
        model.getRadarChartData(currentDatetime, new MealFragmentModel.DomainCallBackListner<Feed>() {
            @Override
            public void doPostExecute(Feed feed) {
                view.setRadarChartData(feed);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getPetAllInfo() {
        mealRegistrationModel.getPetAllInfo(new MealFragmentModel.DomainCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(PetAllInfos petAllInfos) {
                view.setPetAllInfo(petAllInfos);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getTodaySumCalorie() {
        mealRegistrationModel.getTodaySumCalorie(new MealRegistrationModel.DomainCallBackListner<MealHistory>() {
            @Override
            public void doPostExecute(MealHistory mealHistory) {
                view.setTodaySumCalorie(mealHistory);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

}
