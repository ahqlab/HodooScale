package com.animal.scale.hodoo.activity.meal.regist;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityMealRegistrationBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class MealRegistrationActivity extends BaseActivity<MealRegistrationActivity> implements MealRegistrationIn.View{

    ActivityMealRegistrationBinding binding;

    MealRegistrationIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_meal_registration);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_registration);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("음식"));
        presenter = new MealRegistrationPresenter(this);
        presenter.loadData(this);
    }

    @Override
    protected BaseActivity<MealRegistrationActivity> getActivityClass() {
        return MealRegistrationActivity.this;
    }
}
