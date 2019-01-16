package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;

import com.github.mikephil.charting.data.LineData;

public interface WeightStatistics {

    interface View{
    }

    interface Presenter{

        void initLoadData(Context context);

        void getDailyStatisticalData(int type);

        void getWeeklyStatisticalData(int type);

        void getMonthlyStatisticalData(int type);

        void getStatisticalDataByYear(int type);

        void initChart();
    }
}
