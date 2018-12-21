package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Statistics;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeightStatisticsPresenter implements WeightStatistics.Presenter {

    WeightStatistics.View view;

    WeightStatisticsModel model;

    LineChart chart;

    private Context mContext;

    public WeightStatisticsPresenter(WeightStatistics.View view, LineChart chart) {
        this.view = view;
        this.chart = chart;
        this.model = new WeightStatisticsModel();
    }

    @Override
    public void initLoadData(Context context) {
        mContext = context;
        model.initLoadData(context);
    }

    public void getDailyStatisticalData(String type) {
        model.getDailyStatisticalData(type, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {

                    /* 임시 일본어 처리(s) */
                    String[] ko = { "월", "화", "수", "목", "금", "토", "일" };
                    String[] ja = { "月", "火", "水", "木", "金", "土", "日" };
                    Locale locale = mContext.getResources().getConfiguration().locale;
                    String localeStr = locale.getLanguage();
                    if ( localeStr.equals("ja") )
                        for ( Statistics value : d )
                            for (int i = 0; i < ko.length; i++)
                                if ( value.getTheDay().equals(ko[i]) )
                                    value.setTheDay(ja[i]);
                    /* 임시 일본어 처리(e) */

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
    public void getWeeklyStatisticalData(String type) {
        model.getWeeklyStatisticalData(type, new CommonModel.DomainListCallBackListner<Statistics>() {
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
    public void getMonthlyStatisticalData(String type) {
        model.getMonthlyStatisticalData(type, new CommonModel.DomainListCallBackListner<Statistics>() {
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
    public void getStatisticalDataByYear(String type) {
        model.getStatisticalDataByYear(type, new CommonModel.DomainListCallBackListner<Statistics>() {
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
