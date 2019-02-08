package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.list.FeedListModel;
import com.animal.scale.hodoo.common.CommonModel;

import java.util.List;

public class MeaAdapterlSearchPresenter implements MealSearchIn.AdapterPresenter{

    MealSearchIn.AdapterView view;

    MealSearchModel model;

    public MeaAdapterlSearchPresenter(MealSearchIn.AdapterView view) {
        this.view = view;
        this.model = new MealSearchModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getSearchFeed(String s) {
        model.getSearchFeed(s, new CommonModel.DomainListCallBackListner<AutoCompleateFeed>() {
            @Override
            public void doPostExecute(List<AutoCompleateFeed> d) {
                view.setFeedList(d);
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
    public void setSearchHistory() {
        view.setSearchHistory(model.getSearchHistory());
    }
}
