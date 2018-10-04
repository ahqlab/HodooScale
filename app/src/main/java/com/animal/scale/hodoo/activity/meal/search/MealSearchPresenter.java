package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.list.FeedListModel;
import com.animal.scale.hodoo.domain.Feed;

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

    @Override
    public void getSearchFeed(String s) {
        model.getSearchFeed(s, new FeedListModel.DomainListCallBackListner<AutoCompleateFeed>() {
            @Override
            public void doPostExecute(List<AutoCompleateFeed> d) {
                view.setFeedList(d);
            }
            @Override
            public void doPreExecute() {
            }
        });
    }

    @Override
    public void getAllFeed() {
        model.getAllFeed(new FeedListModel.DomainListCallBackListner<AutoCompleateFeed>() {
            @Override
            public void doPostExecute(List<AutoCompleateFeed> d) {
                view.setProgress(false);
                view.setFeedList(d);
            }

            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }
        });
    }
}
