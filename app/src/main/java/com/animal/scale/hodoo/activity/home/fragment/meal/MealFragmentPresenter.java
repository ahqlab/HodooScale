package com.animal.scale.hodoo.activity.home.fragment.meal;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.WeightTip;

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
    public void initRaderChart(String date) {
        view.initRaderChart(date);
    }

    @Override
    public void getRadarChartData(String currentDatetime) {
        model.getRadarChartData(currentDatetime, new CommonModel.DomainCallBackListner<Feed>() {
            @Override
            public void doPostExecute(Feed feed) {
                view.setRadarChartData(feed);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    /*@Override
    public void getPetAllInfo() {
        mealRegistrationModel.getPetAllInfo(new CommonModel.DomainCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(PetAllInfos petAllInfos) {
                view.setPetAllInfo(petAllInfos);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }*/

    @Override
    public void getTodaySumCalorie(String date) {
        mealRegistrationModel.getTodaySumCalorie(date, new CommonModel.DomainCallBackListner<MealHistory>() {
            @Override
            public void doPostExecute(MealHistory mealHistory) {
                view.setTodaySumCalorie(mealHistory);
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
    public void getTipMessageOfCountry(MealTip mealTip) {
        model.getTipMessageOfCountry(mealTip, new CommonModel.DomainCallBackListner<MealTip>() {
            @Override
            public void doPostExecute(MealTip mealTip) {
                view.setTipMessageOfCountry(mealTip);
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
    public void initWeekCalendar() {
        view.initWeekCalendar();
    }

}
