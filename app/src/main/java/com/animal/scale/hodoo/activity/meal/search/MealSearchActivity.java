package com.animal.scale.hodoo.activity.meal.search;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityMealSearchBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class MealSearchActivity extends BaseActivity<MealSearchActivity> {

    ActivityMealSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_search);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_title)));
    }

    @Override
    protected BaseActivity<MealSearchActivity> getActivityClass() {
        return MealSearchActivity.this;
    }
}
