package com.animal.scale.hodoo.activity.meal.detail;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Feed;

public class IngredientsOfMealPresenter implements IngredientsOfMealIn.Presenter{

    IngredientsOfMealIn.View view;

    IngredientsOfMealModel model;

    MealRegistrationModel mealRegistrationModel;

    public IngredientsOfMealPresenter(IngredientsOfMealIn.View view) {
        this.view = view;
        this.model = new IngredientsOfMealModel();
        this.mealRegistrationModel = new MealRegistrationModel();
    }

    @Override
    public void initData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getFeedInfo(int feedId) {
        mealRegistrationModel.getFeedInfo(feedId, new CommonModel.DomainCallBackListner<Feed>() {
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
}
