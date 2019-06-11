package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Statistics;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeightStatisticsPresenter implements WeightStatistics.Presenter {

    WeightStatistics.View view;

    WeightStatisticsModel model;

    BarChart chart;

    private Context mContext;

    public WeightStatisticsPresenter(WeightStatistics.View view, BarChart chart) {
        this.view = view;
        this.chart = chart;
        this.model = new WeightStatisticsModel();
    }

    @Override
    public void initLoadData(Context context) {
        mContext = context;
        model.initLoadData(context);
    }

    public void getDailyStatisticalData(int type) {
        model.getDailyStatisticalData(type, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if (d.size() > 0) {
                    /* 임시 일본어 처리(s) */
                    String[] ko = {"월", "화", "수", "목", "금", "토", "일"};
                    String[] ja = {"月", "火", "水", "木", "金", "土", "日"};
                    String[] en = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                    Locale locale = mContext.getResources().getConfiguration().locale;
                    String localeStr = locale.getLanguage();
                    /* 임시 일본어 처리(e) */
                    if (localeStr.equals("ja")) {
                        for (Statistics value : d) {
                            for (int i = 0; i < ko.length; i++) {
                                if (value.getTheDay().equals(ko[i])) {
                                    value.setTheDay(ja[i]);
                                }
                            }
                        }

                    }else if(localeStr.equals("ko")){

                    }else if(localeStr.equals("en")){
                        for (Statistics value : d) {
                            for (int i = 0; i < ko.length; i++) {
                                if (value.getTheDay().equals(ko[i])) {
                                    value.setTheDay(en[i]);
                                }
                            }
                        }
                    }
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

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getWeeklyStatisticalData(int type) {
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

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getMonthlyStatisticalData(int type) {
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

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getStatisticalDataByYear(int type) {
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

            @Override
            public void doCancelled() {

            }
        });
    }

    private void setStatisticalData(List<Statistics> d, String type) {
        List<Statistics> list = new ArrayList<Statistics>();
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        for (int i = 0; i < d.size(); i++) {
            yVals.add(new BarEntry(i, d.get(i).getAverage()));
        }
        model.setupChart(chart, model.getData(yVals), d, type);
    }

    public static String getDayOfHan(int dayOfWeek) {
        //System.err.println(dayOfWeek);
        if(dayOfWeek == 0) {
            dayOfWeek = 7;
        }else if(dayOfWeek == -1) {
            dayOfWeek = 6;
        }else if(dayOfWeek == -2) {
            dayOfWeek = 5;
        }else if(dayOfWeek == -3) {
            dayOfWeek = 4;
        }else if(dayOfWeek == -4) {
            dayOfWeek = 3;
        }else if(dayOfWeek == -5) {
            dayOfWeek = 2;
        }else if(dayOfWeek == -6) {
            dayOfWeek = 1;
        }

        String day = "";
        switch (dayOfWeek) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;
        }
        return day;
    }

    @Override
    public void initChart() {
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
    }
}
