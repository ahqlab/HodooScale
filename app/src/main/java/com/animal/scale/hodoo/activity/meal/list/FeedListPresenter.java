package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Feed;

import java.util.List;

public class FeedListPresenter implements FeedListIn.Presenter{

    FeedListModel model;

    FeedListIn.View view;

    public FeedListPresenter(FeedListIn.View view) {
        this.view = view;
        this.model = new FeedListModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getAllFeed() {
        model.getAllFeed(new FeedListModel.DomainListCallBackListner<AutoCompleateFeed>() {
            @Override
            public void doPostExecute(List<AutoCompleateFeed> d) {
                //view.setProgress(false);
                view.setFeedList(d);
            }

            @Override
            public void doPreExecute() {
                //view.setProgress(true);
            }
        });
    }
}
