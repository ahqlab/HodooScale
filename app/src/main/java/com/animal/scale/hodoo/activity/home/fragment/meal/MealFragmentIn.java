package com.animal.scale.hodoo.activity.home.fragment.meal;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.text.ParseException;
import java.util.List;

public interface MealFragmentIn {

    interface View{

        public void setProgress(Boolean play);

        void initRaderChart();

        void setRadarChartData(Feed d);

        void setPetAllInfo(PetAllInfos petAllInfos);

        void setTodaySumCalorie(MealHistory mealHistory);

        void setTipMessageOfCountry(MealTip tip);
    }
    interface Presenter{

        void loadData(Context context);

        void initRaderChart();

        void getRadarChartData(String currentDatetime);

        void getPetAllInfo();

        void getTodaySumCalorie();

        void getTipMessageOfCountry(MealTip mealTip);
    }

}
