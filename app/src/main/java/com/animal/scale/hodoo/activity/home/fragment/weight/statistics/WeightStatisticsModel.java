package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart.DayBarDataSet;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart.MonthBarDataSet;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart.MyMarkerView;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart.WeekBarDataSet;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Statistics;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class WeightStatisticsModel extends CommonModel {

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initLoadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void setupChart(BarChart chart, BarData data, final List<Statistics> xValues, final String type, final String dateStr) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);

        //max 와 min을 고정한다.
        //chart.getAxisLeft().setAxisMaxValue(7f);
        //chart.getAxisLeft().setAxisMinValue(5f);

        MyMarkerView mv = new MyMarkerView(context, R.layout.mpchart_market_layout);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart
//        chart.setBackgroundColor(Color.parseColor("#f7f7f7"));
        chart.getAxisRight().setEnabled(false);
        //chart.getXAxis().setDrawGridLines(false);


        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        if ( type.matches("Day") )
            xAxis.setLabelCount(7);
        else if ( type.matches("Week") )
            xAxis.setLabelCount(5);
        else if ( type.matches("Month") )
            xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (type.matches("Day")) {
                    return setDayXValueFormatted(value, axis, xValues);
                } else if (type.matches("Week")) {
                    return setWeekXValueFormatted(value, axis, xValues);
                } else if (type.matches("Month")) {
                    return setMonthXValueFormatted(value, axis, xValues);
                } else if (type.matches("Year")) {
                    return setYearXValueFormatted(value, axis, xValues);
                }
                return "0";
            }
        });
//        if ( type.matches("Week") )
        chart.setMaxVisibleValueCount(5);
        chart.setData(data);
        chart.setFitBars(true);
        chart.getLegend().setEnabled(false);
        //chart.animateX(2500);
        chart.invalidate();

        Highlight highlight = null;

        if ( type.matches("Day") ){
            if(dateStr != null){
                highlight = new Highlight(((DateUtil.getCalendatOfDate(dateStr).get(Calendar.DAY_OF_WEEK) == 1 ? 8 : DateUtil.getCalendatOfDate(dateStr).get(Calendar.DAY_OF_WEEK)) - 2), 0, -1);
            }else{
                highlight = new Highlight(0, 0, -1);
            }
        }else if ( type.matches("Week") ){
            if(dateStr != null){
                highlight = new Highlight((DateUtil.getCalendatOfDate(dateStr).get(Calendar.WEEK_OF_MONTH) - 1), 0, -1);
            }else{
                highlight = new Highlight(0, 0, -1);
            }
        }else if ( type.matches("Month") ){
            if(dateStr != null){
                highlight = new Highlight(Integer.parseInt(dateStr) - 1, 0, -1);
            }
        }
        chart.highlightValue(highlight, false);


        chart.setNoDataText(context.getString(R.string.weight_data_available));
        chart.setNoDataTextColor(context.getResources().getColor(R.color.mainBlack));
    }

    public String setDayXValueFormatted(float value, AxisBase axis, List<Statistics> xValues) {
        return xValues.get((int) value % xValues.size()).getTheDay();
    }

    public String setWeekXValueFormatted(float value, AxisBase axis, List<Statistics> xValues) {
        return xValues.get((int) value % xValues.size()).getTheWeek() + context.getString(R.string.istyle_statistic_week);
    }

    public String setMonthXValueFormatted(float value, AxisBase axis, List<Statistics> xValues) {
        //return Integer.parseInt(xValues.get((int) value % xValues.size()).getTheMonth()) + context.getString(R.string.istyle_statistic_month);
        return xValues.get((int) value % xValues.size()).getTheMonth()+ context.getString(R.string.istyle_statistic_month);
    }

    public String setYearXValueFormatted(float value, AxisBase axis, List<Statistics> xValues) {
        return xValues.get((int) value % xValues.size()).getTheYear() + context.getString(R.string.istyle_one_year);
    }

    public BarData getDayData(ArrayList<BarEntry> yVals, String date) {
        DayBarDataSet set1 = new DayBarDataSet(yVals, "DataSet", date);
        set1.setColors(new int[]{ContextCompat.getColor(context, R.color.grey),
                ContextCompat.getColor(context, R.color.mainRed)});
        set1.setDrawValues(false);
        set1.setHighLightColor(context.getResources().getColor(R.color.mainRed));
        BarData data = new BarData(set1);
        data.setBarWidth(0.1f);
        return data;
    }

    public BarData getDayData(ArrayList<BarEntry> yVals ) {
        DayBarDataSet set1 = new DayBarDataSet(yVals, "DataSet", "");
        set1.setColors(new int[]{ContextCompat.getColor(context, R.color.grey),
                ContextCompat.getColor(context, R.color.mainRed)});
        set1.setDrawValues(false);
        set1.setHighLightColor(context.getResources().getColor(R.color.mainRed));
        BarData data = new BarData(set1);
        data.setBarWidth(0.1f);
        return data;
    }


    public BarData getWeekData(ArrayList<BarEntry> yVals, String date) {
        WeekBarDataSet set1 = new WeekBarDataSet(yVals, "DataSet", date);
        set1.setColors(new int[]{ContextCompat.getColor(context, R.color.grey),
                ContextCompat.getColor(context, R.color.mainRed)});
        set1.setDrawValues(false);
        set1.setHighLightColor(context.getResources().getColor(R.color.mainRed));
        BarData data = new BarData(set1);
        data.setBarWidth(0.1f);
        return data;
    }

    public BarData getMonthData(ArrayList<BarEntry> yVals, String month) {
        MonthBarDataSet set1 = new MonthBarDataSet(yVals, "DataSet", month);
        set1.setColors(new int[]{ContextCompat.getColor(context, R.color.grey),
                ContextCompat.getColor(context, R.color.mainRed)});
        set1.setDrawValues(false);
        set1.setHighLightColor(context.getResources().getColor(R.color.mainRed));
        BarData data = new BarData(set1);
        data.setBarWidth(0.1f);
        return data;
    }

    public void getDailyStatisticalData(int type, String date, final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfDay(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), type , date ,
                mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<Statistics>() {
            @Override
            protected void doPostExecute(List<Statistics> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getWeeklyStatisticalData(int type, String year, String month , final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfWeek(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), year , month, type,
                mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<Statistics>() {
            @Override
            protected void doPostExecute(List<Statistics> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getMonthlyStatisticalData(int type, String month , final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfMonth(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), DateUtil.getCurrentYear(), month, type,
                mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<Statistics>() {
            @Override
            protected void doPostExecute(List<Statistics> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    /*public void getStatisticalDataByYear(int type, final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfYear(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), type,
                mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<Statistics>() {
            @Override
            protected void doPostExecute(List<Statistics> d) {
                domainListCallBackListner.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }*/
}
