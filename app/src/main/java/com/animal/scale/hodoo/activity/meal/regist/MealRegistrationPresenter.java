package com.animal.scale.hodoo.activity.meal.regist;

import android.content.Context;

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
}
