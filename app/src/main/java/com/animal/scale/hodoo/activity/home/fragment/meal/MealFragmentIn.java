package com.animal.scale.hodoo.activity.home.fragment.meal;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.text.ParseException;
import java.util.List;

public interface MealFragmentIn {

    interface View{

        public void setProgress(Boolean play);

        void initRaderChart();

        void setRadarChartData(Feed d);
    }
    interface Presenter{

        void loadData(Context context);

        void initRaderChart();

        void getRadarChartData(String currentDatetime);
    }

}
