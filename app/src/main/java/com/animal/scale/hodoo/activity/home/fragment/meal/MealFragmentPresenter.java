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

    public MealFragmentPresenter(MealFragmentIn.View view) {
        this.view = view;
        this.model = new MealFragmentModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
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

}
