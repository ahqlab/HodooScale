package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.content.Context;
import android.graphics.Color;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart.MyMarkerView;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Statistics;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class WeightStatisticsModel extends CommonModel{

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initLoadData(Context context) {
        this.context  = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void setupChart(LineChart chart, LineData data, final List<Statistics> xValues, final String type) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);

        MyMarkerView mv  = new MyMarkerView(context, R.layout.mpchart_market_layout);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart
//        chart.setBackgroundColor(Color.parseColor("#f7f7f7"));
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(type.matches("Day")){
                    return setDayXValueFormatted(value, axis, xValues);
                }else if(type.matches("Week")){
                    return setWeekXValueFormatted(value, axis, xValues);
                }else if(type.matches("Month")){
                    return setMonthXValueFormatted(value, axis, xValues);
                }else if(type.matches("Year")){
                    return setYearXValueFormatted(value, axis, xValues);
                }
                return "0";
            }
        });
        chart.setData(data);
        chart.getLegend().setEnabled(false);
        //chart.animateX(2500);
        chart.invalidate();

        Highlight highlight = new Highlight((float) data.getEntryCount(), 0, -1);
        chart.highlightValue(highlight, false);
        chart.setNoDataText(context.getString(R.string.weight_data_available));
        chart.setNoDataTextColor(context.getResources().getColor(R.color.mainBlack));
    }

    public String setDayXValueFormatted(float value, AxisBase axis, List<Statistics> xValues){
        return xValues.get((int) value % xValues.size()).getTheDay();
    }

    public String setWeekXValueFormatted(float value, AxisBase axis, List<Statistics> xValues){
        return xValues.get((int) value % xValues.size()).getTheWeek() + context.getString(R.string.istyle_statistic_week);
    }

    public String setMonthXValueFormatted(float value, AxisBase axis, List<Statistics> xValues){
        return xValues.get((int) value % xValues.size()).getTheMonth() + context.getString(R.string.istyle_statistic_month);
    }

    public String setYearXValueFormatted(float value, AxisBase axis, List<Statistics> xValues){
        return xValues.get((int) value % xValues.size()).getTheYear() + context.getString(R.string.istyle_one_year);
    }

    public LineData getData(ArrayList<Entry> yVals) {
        LineDataSet set1 = new LineDataSet(yVals, "DataSet");
        set1.setDrawValues(false);
        //커브 곡선
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false);
        set1.setDrawCircles(false);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(context.getResources().getColor(R.color.mainRed));
        set1.setHighLightColor(context.getResources().getColor(R.color.mainRed));
        set1.setColor(context.getResources().getColor(R.color.mainRed));
        set1.setDrawHorizontalHighlightIndicator(false);
        return new LineData(set1);
    }

    public void getDailyStatisticalData(int type , final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfDay(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), type);
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

    public void getWeeklyStatisticalData(int type, final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfWeek(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), DateUtil.getCurrentMonth(), type);
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
    public void getMonthlyStatisticalData( int type, final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfMonth(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), DateUtil.getCurrentYear(),type);
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
    public void getStatisticalDataByYear( int type, final CommonModel.DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfYear(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), type);
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
}
