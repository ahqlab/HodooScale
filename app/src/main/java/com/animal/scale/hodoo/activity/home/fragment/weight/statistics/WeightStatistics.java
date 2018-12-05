package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;

import com.github.mikephil.charting.data.LineData;

public interface WeightStatistics {

    interface View{
    }

    interface Presenter{

        void initLoadData(Context context);

        void getDailyStatisticalData();

        void getWeeklyStatisticalData();

        void getMonthlyStatisticalData();

        void getStatisticalDataByYear();

        void initChart();
    }
}
