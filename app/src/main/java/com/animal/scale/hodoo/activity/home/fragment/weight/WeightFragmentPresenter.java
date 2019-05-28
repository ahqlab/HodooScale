package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.activity.home.fragment.temp.TempFragment;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.HodooIndex;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.Statistics;
import com.animal.scale.hodoo.domain.WeightGoalChart;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.util.DateUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import static com.animal.scale.hodoo.custom.view.input.CommonTextWatcher.TAG;

public class WeightFragmentPresenter implements WeightFragmentIn.Presenter {

    WeightFragmentIn.View view;

    WeightFragmentModel model;

    LineChart chart;

    public WeightFragmentPresenter(WeightFragment fragment, LineChart chart){
        this.view = fragment;
        this.chart = chart;
        this.model = new WeightFragmentModel();
    }

    public WeightFragmentPresenter(WeightFragment fragment){
        this.view = fragment;
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
    public void setBcsAndBcsDesc(int bcs) {
        view.setBcsAndBcsDesc(bcs);
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
        /*String[] dayArray = {"월","화","수","목", "금", "토", "일"};
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < dayArray.length; i++){
            for (int j = 0; j < d.size(); i++) {
                if(d.get(j).getTheDay().equals(dayArray[i])){
                    yVals.add(new Entry(i, d.get(i).getAverage()));
                }else{
                    Statistics statistics = new Statistics();
                    statistics.setTheDay("no");
                    statistics.setAverage(0);
                    yVals.add(new Entry(i, statistics.getAverage()));
                }
            }
        }*/

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < d.size(); i++) {
            yVals.add(new Entry(i, d.get(i).getAverage()));
        }
        model.setupChart(chart, model.getData(yVals, chart), d);
    }

 /*   @Override
    public void setupDefaultChart() {
    }*/

    @Override
    public void getLastCollectionData(final String date, int type) {
        model.getLastCollectionData(date, type , new CommonModel.DomainCallBackListner<RealTimeWeight>() {
            @Override
            public void doPostExecute(RealTimeWeight d) {
                view.setLastCollectionDataOrSaveAvgWeight(d);
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

    @Override
    public void getWeightGoal(int petIdx) {
        model.getWeightGoal(petIdx, new CommonModel.CommonDomainCallBackListner<HodooIndex>() {
            @Override
            public void doPostExecute(CommonResponce<HodooIndex> d) {
                view.setWeightGoal(d.getDomain());
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
    /*
     * 수정한 몸무게를 서버로 보내 리턴값을 받아온다.
     *
     * @param   PetPhysicalInfo info    수정한 몸무게가 담겨있는 모델
     * @return
    */
    @Override
    public void updatePhysical(PetPhysicalInfo info) {
        model.updatePhysical(info, new CommonModel.ObjectCallBackListner<CommonResponce<PetPhysicalInfo>>() {
            @Override
            public void doPostExecute(CommonResponce<PetPhysicalInfo> integerCommonResponce) {
                view.physicalUpdateDone(integerCommonResponce.domain);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    /*
     * 전 주 대비 감량비를 서버에서 받아서 리턴한다.
     *
     * @param
     * @return
    */
    @Override
    public void getWeekRate() {
        model.getWeekRate(new CommonModel.ObjectCallBackListner<Float>() {
            @Override
            public void doPostExecute(Float result) {
                if ( result != null )
                    view.setWeekRate(result);
                else
                    view.setWeekRate(0);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    /**
     * 저장되어있는 단위를 가져온다
     *
     * @param
     * @return int type 0 : cm/kg, 1 : inch/lb
     */
    @Override
    public int getWeightUnit() {
        return model.getWeightUnit();
    }
}
