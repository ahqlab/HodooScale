package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;

import com.github.mikephil.charting.data.LineData;

public interface WeightStatistics {

    interface View{
    }

    interface Presenter{

        void initLoadData(Context context);

        void getDailyStatisticalData(String type);

        void getWeeklyStatisticalData(String type);

        void getMonthlyStatisticalData(String type);

        void getStatisticalDataByYear(String type);

        void initChart();
    }
}
