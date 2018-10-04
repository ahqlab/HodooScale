package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;

import com.animal.scale.hodoo.domain.Feed;

import java.util.List;

public interface MealSearchIn {

    interface View{

        void setProgress(boolean b);

        void setFeedList(List<AutoCompleateFeed> d);

    }

    interface AdapterView {

        void setFeedList(List<AutoCompleateFeed> d);
    }

    interface AdapterPresenter {

        void loadData(Context context);

        void getSearchFeed(String s);
    }

    interface Presenter{

        void getAllFeed();

        void loadData(Context context);

        void getSearchFeed(String s);
    }
}
