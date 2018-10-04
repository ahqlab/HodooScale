package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Feed;

import java.util.List;

public interface FeedListIn {

    interface View{

        void setProgress(boolean b);

        void setFeedList(List<AutoCompleateFeed> d);
    }

    interface Presenter{

        void loadData(Context context);

        void getAllFeed();
    }
}
