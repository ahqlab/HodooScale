package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.content.Context;

import com.animal.scale.hodoo.domain.ArrayListDevice;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.WeightGoalChart;
import com.animal.scale.hodoo.domain.WeightTip;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

import java.util.List;

public interface WeightFragmentIn {

    interface View{

        void setBcs(PetWeightInfo petWeightInfo);

        void setBcsAndBcsDesc(int bcs);

        //void setLastCollectionData(RealTimeWeight d);

        void initWeekCalendar();

        void setLastCollectionDataOrSaveAvgWeight(RealTimeWeight d);

        void setTipMessageOfCountry(WeightTip weightTip);

        void setCalendar();

        void setWeightGoal(WeightGoalChart d);

        void setLastCollectionData(RealTimeWeight d);
    }

    interface Presenter{

        void loadData(Context context);

        void getBcs(int basicIdx);

        void setBcsAndBcsDesc(int bcs);

        void getDefaultData(String date, int type);

        //void setupDefaultChart();

        void getLastCollectionData(String date, int type);

        void initWeekCalendar();

        //void initChart();

        void getTipMessageOfCountry(WeightTip weightTip);

        void getWeightGoal(float currentWeight, int bodyFat, int petType);
    }
}
