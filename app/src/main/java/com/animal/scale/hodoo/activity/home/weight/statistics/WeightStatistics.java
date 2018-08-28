package com.animal.scale.hodoo.activity.home.weight.statistics;

import android.content.Context;

import com.github.mikephil.charting.data.LineData;

public interface WeightStatistics {

    interface View{
    }

    interface Presenter{

        void initLoadData(Context context);

        void setupDefaultChart(LineData data);

        LineData getDefaultData();

        void getDayStatitics();

        void getWeekStatitics();

        void getMonthStatitics();

        void getYearStatitics();

        void initChart();
    }
}
