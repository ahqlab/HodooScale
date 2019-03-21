package com.animal.scale.hodoo.activity.meal.regist;

import android.content.Context;

import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragmentModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

public class MealRegistrationPresenter implements MealRegistrationIn.Presenter{

    MealRegistrationIn.View view;

    MealRegistrationModel model;

    public MealRegistrationPresenter(MealRegistrationIn.View view) {
        this.view = view;
        this.model = new MealRegistrationModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getFeedInfo(int feedId) {
        model.getFeedInfo(feedId, new CommonModel.DomainCallBackListner<Feed>() {
            @Override
            public void doPostExecute(Feed feed) {
                view.setFeedInfo(feed);
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
    public void saveMeal(MealHistory mealHistory) {
        model.saveMeal(mealHistory, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.setInsertResult(integer);
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
    public void getTodaySumCalorie(String date) {
        model.getTodaySumCalorie(date, new CommonModel.DomainCallBackListner<MealHistory>() {
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
}
