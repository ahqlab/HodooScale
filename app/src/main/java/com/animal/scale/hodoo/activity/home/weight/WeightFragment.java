package com.animal.scale.hodoo.activity.home.weight;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.weight.statistics.WeightStatisticsActivity;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.FragmentWeightBinding;
import com.animal.scale.hodoo.service.NetRetrofit;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import im.dacer.androidcharts.LineView;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.listener.OnWeekChangeListener;
import retrofit2.Call;

public class WeightFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private WeekCalendar weekCalendar;

    FragmentWeightBinding binding;

    Animation animation;

    int randomint = 6;

    SharedPrefManager mSharedPrefManager;

    public WeightFragment() {
    }

    public static WeightFragment newInstance() {
        return new WeightFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_weight, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weight, container, false);
        binding.setActivity(this);
        init();
        return binding.getRoot();
    }

    private void setCurrentKg() {
        Call<Float> call = NetRetrofit.getInstance().getRealTimeWeightService().getLatelyData(mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID));
        new AbstractAsyncTask<Float>() {
            @Override
            protected void doPostExecute(Float aFloat) {
                binding.currentKg.setText(aFloat + "Kg");
            }
            @Override
            protected void doPreExecute() {
            }
        }.execute(call);
    }

    private void setBcsMessage() {
        mSharedPrefManager = SharedPrefManager.getInstance(getActivity());
        Call<String> call = NetRetrofit.getInstance().getPetWeightInfoService().getMyBcs(mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID));
        new AbstractAsyncTask<String>() {
            @Override
            protected void doPostExecute(String str) {
                binding.myBcsStep.setText("BCS " + str + "단계");
                setAnimationGaugeChart(Integer.parseInt(str));
            }
            @Override
            protected void doPreExecute() {
            }
        }.execute(call);
    }

    public void setAnimationGaugeChart(int bcs){
        if(bcs < 3){
            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_one_step);
        }else if(bcs > 3){
            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_three_step);
        }else{
            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_two_step);
        }
        binding.clockHands.startAnimation(animation);
    }

    private void initLineView() {
        ArrayList<String> test = new ArrayList<String>();
        for (int i = 0; i < randomint; i++) {
            test.add(String.valueOf(i));
        }
        binding.chartView.setBottomTextList(test);
        binding.chartView.setColorArray(new int[] {
                Color.parseColor("#F44336"), Color.parseColor("#9C27B0"),
                Color.parseColor("#2196F3"), Color.parseColor("#009688")
        });
        binding.chartView.setDrawDotLine(true);
        binding.chartView.setShowPopup(LineView.SHOW_POPUPS_NONE);
    }

    private void randomSet() {

        ArrayList<Integer> dataList3 = new ArrayList<>();
        float random = (float) (Math.random() * 9 + 1);
        random = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataList3.add((int) (Math.random() * random));
        }
        ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
        dataLists.add(dataList3);

        binding.chartView.setDataList(dataLists);

    }

    public void setServerData(){
        Call<List<Float>> call = NetRetrofit.getInstance().getRealTimeWeightService().getRealTimeList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID));
        new AbstractAsyncTaskOfList<Float>() {
            @Override
            protected void doPostExecute(List<Float> datas) {
                if(datas.size() > 0){
                    ArrayList<Integer> dataList = new ArrayList<>();
                    for(int i = 0; i < datas.size(); i++){
                        dataList.add(Math.round(datas.get(i)));
                    }
                    ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
                    dataLists.add(dataList);
                    binding.chartView.setDataList(dataLists);
                }
            }
            @Override
            protected void doPreExecute() {
            }
        }.execute(call);
    }

    private void init() {

        initLineView();
        setBcsMessage();
        setCurrentKg();
        setServerData();

       /* binding.weekCalendar.today;
        Button todaysDate = (Button) findViewById(R.id.today);
        Button selectedDate = (Button) findViewById(R.id.selectedDateButton);
        Button startDate = (Button) findViewById(R.id.startDate);*/
        /*todaysDate.setText(new DateTime().toLocalDate().toString() + " (Reset Button)");
        selectedDate.setText(new DateTime().plusDays(50).toLocalDate().toString()
                + " (Set Selected Date Button)");
        startDate.setText(new DateTime().plusDays(7).toLocalDate().toString()
                + " (Set Start Date Button)");*/
        binding.weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
               /* Toast.makeText(MainActivity.this, "You Selected " + dateTime.toString(), Toast
                        .LENGTH_SHORT).show();*/
            }

        });


        binding.weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {
               /* Toast.makeText(MainActivity.this, "Week changed: " + firstDayOfTheWeek +
                        " Forward: " + forward, Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    public void onClickResetGraph(View view){
        binding.clockHands.startAnimation(animation);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e("HJLEE", "WF " + item.getItemId());
        return false;
    }

    public void onClickFloatingBtn(View view){
        Intent intent = new Intent(getActivity(), WeightStatisticsActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        //finish();
    }

}
