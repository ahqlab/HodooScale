package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.list.FeedListModel;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.SearchHistory;

import java.util.List;

public class MealSearchPresenter implements MealSearchIn.Presenter{

    MealSearchIn.View view;

    MealSearchModel model;

    public MealSearchPresenter(MealSearchIn.View view) {
        this.view = view;
        this.model = new MealSearchModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

}
