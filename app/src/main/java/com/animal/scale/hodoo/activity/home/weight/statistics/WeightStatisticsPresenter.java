package com.animal.scale.hodoo.activity.home.weight.statistics;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

public class WeightStatisticsPresenter implements WeightStatistics.Presenter{

    WeightStatistics.View view;

    WeightStatisticsModel model;

    LineChart chart;


    public WeightStatisticsPresenter(WeightStatistics.View view,  LineChart chart) {
        this.view = view;
        this.chart = chart;
        this.model = new WeightStatisticsModel();
    }

    @Override
    public void initLoadData(Context context) {
        model.initLoadData(context);
    }

    @Override
    public void setupDefaultChart(LineData data) {
        model.setupChart(chart, data, model.mDays);
    }

    @Override
    public LineData getDefaultData() {
        return model.getData(model.getDayData());
    }

    @Override
    public void getDayStatitics() {
        initChart();
        model.setupChart(chart, model.getData(model.getDayData()), model.mDays);
    }

    @Override
    public void getWeekStatitics() {
        initChart();
        model.setupChart(chart, model.getData(model.getWeekData()), model.mWeeks);
    }

    @Override
    public void getMonthStatitics() {
        initChart();
        model.setupChart(chart, model.getData(model.getWeekData()), model.mMonths);
    }

    @Override
    public void getYearStatitics() {
        initChart();
        model.setupChart(chart, model.getData(model.getYearData()), model.mYears);
    }

    @Override
    public void initChart() {
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
    }
}
