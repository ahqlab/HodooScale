package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.activity.home.fragment.temp.TempFragment;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.ArrayListDevice;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.Statistics;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;

public class WeightFragmentPresenter implements WeightFragmentIn.Presenter{

    WeightFragmentIn.View view;

    WeightFragmentModel model;

    LineChart chart;

    public WeightFragmentPresenter(WeightFragment fragment, LineChart chart){
        this.view = fragment;
        this.chart = chart;
        this.model = new WeightFragmentModel();
    }

    public WeightFragmentPresenter(TempFragment fragment, LineChart chart){
        this.view = fragment;
        this.chart = chart;
        this.model = new WeightFragmentModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getBcs(int basicIdx) {
        model.getBcs(basicIdx, new CommonModel.DomainCallBackListner<PetWeightInfo>() {
            @Override
            public void doPostExecute(PetWeightInfo petWeightInfo) {
                if ( petWeightInfo != null )
                    view.setBcs(petWeightInfo);
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
    public void setAnimationGaugeChart(int bcs) {
        view.setAnimationGaugeChart(bcs);
    }

    @Override
    public void getDefaultData(String date, int type) {
        model.getDayData(date, type, new CommonModel.DomainListCallBackListner<Statistics>() {
            @Override
            public void doPostExecute(List<Statistics> d) {
                if(d.size() > 0){
                    if(chart.getData() != null){
                        chart.getData().notifyDataChanged();
                    }
                    chart.notifyDataSetChanged();
                    getDayData(d);
                }else{
                    chart.clear();
                    //initChart();
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

    private void getDayData(List<Statistics> d) {
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < d.size(); i++) {
            yVals.add(new Entry(i, d.get(i).getAverage()));
        }
        model.setupChart(chart, model.getData(yVals, chart), d);
    }

    @Override
    public void setupDefaultChart() {
    }

    @Override
    public void getLastCollectionData(final String date, int type) {
        model.getLastCollectionData(date, type , new CommonModel.DomainCallBackListner<RealTimeWeight>() {
            @Override
            public void doPostExecute(RealTimeWeight d) {
                if(date.matches(DateUtil.getCurrentDatetime())){
                    view.setLastCollectionDataOrSaveAvgWeight(d);
                }else{
                    view.setLastCollectionData(d);
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
    public void initWeekCalendar() {
        view.initWeekCalendar();
    }

    @Override
    public void initChart() {

    }

    @Override
    public void getTipMessageOfCountry(WeightTip weightTip) {
        model.getTipMessageOfCountry(weightTip, new CommonModel.DomainCallBackListner<WeightTip>() {
            @Override
            public void doPostExecute(WeightTip weightTip) {
                view.setTipMessageOfCountry(weightTip);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
