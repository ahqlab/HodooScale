package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;

import com.github.mikephil.charting.data.LineData;

public interface WeightStatistics {

    interface View{
    }

    interface Presenter{

        void initLoadData(Context context);

        void getDailyStatisticalData(int type, String date);

        void getWeeklyStatisticalData(int type, String date , String year, String month);

        void getMonthlyStatisticalData(int type, String month);

        //void getStatisticalDataByYear(int type, String date);

        void initChart();
    }
}
