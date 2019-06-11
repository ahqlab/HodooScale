package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.chart.MyMarkerView;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.Statistics;
import com.animal.scale.hodoo.domain.WeightGoalChart;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;

public class WeightFragmentModel extends CommonModel {

    public Context context;

    public SharedPrefManager mSharedPrefManager;

    ArrayList<Entry> yVals = new ArrayList<Entry>();

    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getBcs(int basicIdx, final DomainCallBackListner<PetWeightInfo> domainCallBackListner) {
        Call<PetWeightInfo> call = NetRetrofit.getInstance().getPetWeightInfoService().getBcs(basicIdx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<PetWeightInfo>() {
            @Override
            protected void doPostExecute(PetWeightInfo petWeightInfo) {
                domainCallBackListner.doPostExecute(petWeightInfo);
            }

            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void setupChart(LineChart chart, LineData data, final List<Statistics> xValues) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        //chart.getAxisLeft().setAxisMaxValue(7f);
        //chart.getAxisLeft().setAxisMinValue(5f);

        MyMarkerView mv = new MyMarkerView(context, R.layout.mpchart_market_layout);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart
        chart.setBackgroundColor(Color.parseColor("#ffffff"));
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = chart.getXAxis();
        //xAxis.setAxisLineColor();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues.get((int) value % xValues.size()).getTheHour() + context.getResources().getString(R.string.time);
            }
        });
        chart.setData(data);
        chart.getLegend().setEnabled(false);
        chart.animateX(1000);
        chart.setNoDataText(context.getString(R.string.weight_data_available));
        chart.setNoDataTextColor(context.getResources().getColor(R.color.mainBlack));

        chart.invalidate();

        Highlight highlight = new Highlight((float) data.getEntryCount(), 0, -1);
        chart.highlightValue(highlight, false);

    }


    public LineData getData(ArrayList<Entry> yVals, final LineChart chart) {
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

        /*set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.2f);
        //set1.setLineWidth(1.75f);
        //set1.setCircleRadius(5f);
        //set1.setCircleHoleRadius(2.5f);
        set1.setDrawCircles(true);

        set1.setColor(Color.parseColor("#fc9596"));
        set1.setCircleColor(Color.parseColor("#fc9596"));
        //focus 되었을 경우 생기는 line 의 색상
        set1.setHighLightColor(Color.parseColor("#fc9596"));
        set1.setFillAlpha(100);
        // set the filled area
        set1.setDrawFilled(true);*/
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }
        return new LineData(set1);
    }

    public Entry dumeEntry(ArrayList<Entry> yVals){
        LineDataSet set1 = new LineDataSet(yVals, "DataSet");
        Entry entry = set1.getEntryForXValue(yVals.get(0).getX(), yVals.get(0).getY());
        return null;
    }

    public void getDayData(String date,  int type, final DomainListCallBackListner<Statistics> domainListCallBackListner) {
        Call<List<Statistics>> call = NetRetrofit.getInstance().getRealTimeWeightService().getStatisticsOfTime(
                mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), date, type, mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
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

    public void getLastCollectionData(String date, int type, final DomainCallBackListner<RealTimeWeight> domainListCallBackListner) {
        Call<RealTimeWeight> call = NetRetrofit.getInstance().getRealTimeWeightService().getLastCollectionData(date, mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), type, mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<RealTimeWeight>() {
            @Override
            protected void doPostExecute(RealTimeWeight realTimeWeight) {
                domainListCallBackListner.doPostExecute(realTimeWeight);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {
                domainListCallBackListner.doCancelled();
            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getTipMessageOfCountry(WeightTip weightTip, final DomainCallBackListner<WeightTip> domainListCallBackListner) {
        Call<WeightTip> call = NetRetrofit.getInstance().getWeightTipService().getWeightTipOfCountry(weightTip);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<WeightTip>() {
            @Override
            protected void doPostExecute(WeightTip tip) {
                domainListCallBackListner.doPostExecute(tip);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void getWeightGoal(float currentWeight, int bodyFat, int petType, final CommonDomainCallBackListner<WeightGoalChart> commonDomainCallBackListner) {

        Call<CommonResponce<WeightGoalChart>> call = NetRetrofit.getInstance().getWeightGoalChartService().getWeightGoal(currentWeight, bodyFat, petType);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<WeightGoalChart>>() {
            @Override
            protected void doPostExecute(CommonResponce<WeightGoalChart> responce) {
                commonDomainCallBackListner.doPostExecute(responce);
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
