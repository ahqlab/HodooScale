package com.animal.scale.hodoo.activity.meal.update;

import android.content.Context;

import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragmentModel;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.activity.meal.search.MealSearchIn;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

class MealUpdatePresenter implements MealUpdateIn.Presenter {

    MealUpdateIn.View view;

    MealUpdateModel model;

    public MealUpdatePresenter(MealUpdateIn.View view) {
        this.view = view;
        this.model = new MealUpdateModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getFeedInfo(int feedId) {
        model.getFeedInfo(feedId, new MealRegistrationModel.DomainCallBackListner<Feed>() {
            @Override
            public void doPostExecute(Feed feed) {
                view.setFeedInfo(feed);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void saveMeal(MealHistory mealHistory) {
        model.saveMeal(mealHistory, new MealRegistrationModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.setInsertResult(integer);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void updateMeal(MealHistory mealHistory) {
        model.updateMeal(mealHistory, new MealRegistrationModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.setUpdateResult(integer);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getPetAllInfo() {
        model.getPetAllInfo(new MealFragmentModel.DomainCallBackListner<PetAllInfos>() {
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
    public void getTodaySumCalorie(String date) {
        model.getTodaySumCalorie(date, new MealRegistrationModel.DomainCallBackListner<MealHistory>() {
            @Override
            public void doPostExecute(MealHistory mealHistory) {
                view.setTodaySumCalorie(mealHistory);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getThisHistory(int historyIdx) {
        model.getThisHistory(historyIdx, new MealUpdateModel.DomainCallBackListner<MealHistory>() {
            @Override
            public void doPostExecute(MealHistory mealHistory) {
                view.setThisHistory(mealHistory);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
