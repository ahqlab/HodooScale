package com.animal.scale.hodoo.activity.meal.search;

import android.content.Context;

import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.SearchHistory;

import java.util.List;

public interface MealSearchIn {

    interface View{

        void setProgress(boolean b);

    }

    interface AdapterView {

        void setFeedList(List<AutoCompleateFeed> d);

        void setSearchHistory(List<SearchHistory> searchHistory);
    }

    interface AdapterPresenter {

        void loadData(Context context);

        void getSearchFeed(String s);

        void setSearchHistory();
    }

    interface Presenter{

        void loadData(Context context);

    }
}
