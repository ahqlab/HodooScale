package com.animal.scale.hodoo.activity.meal.detail;

import android.content.Context;

import com.animal.scale.hodoo.domain.Feed;

public interface IngredientsOfMealIn {

    interface View{

        void setFeedInfo(Feed feed);
    }
    interface Presenter{

        void initData(Context context);

        void getFeedInfo(int feedId);
    }
}
