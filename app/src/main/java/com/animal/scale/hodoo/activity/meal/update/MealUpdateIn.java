package com.animal.scale.hodoo.activity.meal.update;

import android.content.Context;

import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

public interface MealUpdateIn {

    interface View{

        void setFeedInfo(Feed feed);

        void setInsertResult(Integer integer);

        void setTodaySumCalorie(MealHistory mealHistory);

        void setThisHistory(MealHistory mealHistory);

        void setUpdateResult(int result);
    }

    interface Presenter{

        void loadData(Context context);

        void getFeedInfo(int feedId);

        void saveMeal(MealHistory mealHistory);

        void updateMeal(MealHistory mealHistory);

        void getTodaySumCalorie(String date);

        void getThisHistory(int historyIdx);
    }
}
