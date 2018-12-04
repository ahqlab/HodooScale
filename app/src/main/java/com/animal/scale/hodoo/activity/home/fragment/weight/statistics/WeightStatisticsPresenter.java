package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Statistics;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class WeightStatisticsPresenter implements WeightStatistics.Presenter {

    WeightStatistics.View view;

    WeightStatisticsModel model;

    LineChart chart;

    public WeightStatisticsPresenter(WeightStatistics.View view, LineChart chart) {
        this.view = view;
        this.chart = chart;
        this.model = new WeightStatisticsModel();
    }

    @Override
    public void initLoadData(Context context) {
        model.initLoadData(context);
    }

    public void getDailyStatisticalData() {
        model.getDailyStatisticalData(new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {
                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, "Day");
                } else {
                    chart.clear();
                }
            }
            @Override
            public void doPreExecute() {
            }
        });
    }

    @Override
    public void getWeeklyStatisticalData() {
        model.getWeeklyStatisticalData(new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {
                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, "Week");
                } else {
                    chart.clear();
                }
            }
            @Override
            public void doPreExecute() {
            }
        });
    }

    @Override
    public void getMonthlyStatisticalData() {
        model.getMonthlyStatisticalData(new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {
                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, "Month");
                } else {
                    chart.clear();
                }
            }
            @Override
            public void doPreExecute() {
            }
        });
    }

    @Override
    public void getStatisticalDataByYear() {
        model.getStatisticalDataByYear(new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {
                    if (chart.getData() != null) {
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    setStatisticalData(d, "Year");
                } else {
                    chart.clear();
                }
            }
            @Override
            public void doPreExecute() {
            }
        });
    }

    private void setStatisticalData(List<Statistics> d, String type) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < d.size(); i++) {
            yVals.add(new Entry(i, d.get(i).getAverage()));
        }
        model.setupChart(chart, model.getData(yVals), d, type);
    }

    @Override
    public void initChart() {
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
    }
}
