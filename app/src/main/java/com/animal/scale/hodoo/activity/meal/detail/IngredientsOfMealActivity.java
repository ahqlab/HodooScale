package com.animal.scale.hodoo.activity.meal.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityIngredientsOfMealBinding;
import com.animal.scale.hodoo.domain.Feed;

public class IngredientsOfMealActivity extends BaseActivity<IngredientsOfMealActivity> implements IngredientsOfMealIn.View{


    ActivityIngredientsOfMealBinding binding;

    IngredientsOfMealIn.Presenter presenter;

    private int feedId;

    /**
     * IngredientsOfMealActivity 사용안함.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ingredients_of_meal);
        presenter = new IngredientsOfMealPresenter(this);
        presenter.initData(IngredientsOfMealActivity.this);
        feedId = intent.getIntExtra("feedId", 0);
        presenter.getFeedInfo(feedId);
    }

    @Override
    protected BaseActivity<IngredientsOfMealActivity> getActivityClass() {
        return IngredientsOfMealActivity.this;
    }

    @Override
    public void setFeedInfo(Feed feed) {
        binding.setDomain(feed);
    }
}
