package com.animal.scale.hodoo.activity.meal.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public interface FeedListIn {

    interface View{

        void setProgress(boolean b);

        void setListView(List<MealHistoryContent> d);

        void initSeekbar();

        void setTodaySumCalorie(MealHistory mealHistory);

        void setPetAllInfo(PetAllInfos petAllInfos);

        void deleteResult(Integer integer);
    }

    interface Presenter{

        void loadData(Context context);

        void getList(String date);

        void initSeekbar();

        void getTodaySumCalorie();

        void getPetAllInfo();

        void deleteMealHistory(int historyIdx);
    }
}
