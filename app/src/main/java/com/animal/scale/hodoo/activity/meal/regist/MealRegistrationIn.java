package com.animal.scale.hodoo.activity.meal.regist;

import android.content.Context;

import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

public interface MealRegistrationIn {

    interface View{

        void setFeedInfo(Feed feed);

        void setInsertResult(Integer integer);

        //void setPetAllInfo(PetAllInfos petAllInfos);

        void setTodaySumCalorie(MealHistory mealHistory);
    }

    interface Presenter{

        void loadData(Context context);

        void getFeedInfo(int feedId);

        void saveMeal(MealHistory mealHistory);

       // void getPetAllInfo();

        void getTodaySumCalorie(String date);
    }
}
