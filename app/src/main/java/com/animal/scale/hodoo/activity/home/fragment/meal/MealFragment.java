package com.animal.scale.hodoo.activity.home.fragment.meal;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.list.FeedListActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.mpchart.RadarMarkerView;
import com.animal.scale.hodoo.databinding.FragmentMealLayoutBinding;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.RER;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import noman.weekcalendar.WeekCalendar;

public class MealFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, MealFragmentIn.View {

    SharedPrefManager mSharedPrefManager;

    private WeekCalendar weekCalendar;

    FragmentMealLayoutBinding binding;

    MealFragmentIn.Presenter presenter;

    private RadarChart chart;

    protected Typeface tfRegular;

    protected Typeface tfLight;

    private boolean refrashState = false;

    private long nowTime;

    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

    private float rer;

    public MealFragment() {
    }

    public static MealFragment newInstance() {
        return new MealFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal_layout, container, false);
        binding.setActivity(this);

        mSharedPrefManager = SharedPrefManager.getInstance(getActivity());

        presenter = new MealFragmentPresenter(this);
        presenter.loadData(getActivity());
        presenter.initRaderChart();

        nowTime = System.currentTimeMillis();
        binding.lastRefresh.setText(getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)));

        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        //binding.calorieBar.setPro

        return binding.getRoot();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void onClickRegistBtn(View view) {
        Intent intent = new Intent(getActivity(), FeedListActivity.class);
        startActivity(intent);
    }

    @Override
    public void setProgress(Boolean play) {
        if (play) {
            binding.loginProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loginProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void initRaderChart() {
        chart = binding.chart1;
        chart.setBackgroundColor(Color.rgb(255, 255, 255));
        //chart.setScaleX(1.2f);
        //chart.setScaleY(1.2f);
        chart.getDescription().setEnabled(false);

        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(getActivity(), R.layout.radar_markerview);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        setData();

        //chart.animateXY(1400, 1400, com.animal.scale.hodoo.custom.animation.Easing.EaseInBack);
        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private final String[] mActivities = new String[]{getString(R.string.istyle_crude_protein), getString(R.string.istyle_crude_pat), getString(R.string.istyle_crude_fiber), getString(R.string.istyle_crude_ash), getString(R.string.istyle_carbohydrate)};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.LTGRAY);

        YAxis yAxis = chart.getYAxis();
        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        chart.getLegend().setEnabled(false);
       /* Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);*/
    }

    public void setData() {
        presenter.getRadarChartData(DateUtil.getCurrentDatetime());
    }

    @Override
    public void setRadarChartData(Feed feed) {
        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        entries1.add(new RadarEntry(100));
        entries1.add(new RadarEntry(100));
        entries1.add(new RadarEntry(100));
        entries1.add(new RadarEntry(100));
        entries1.add(new RadarEntry(100));

        if (feed != null) {
            entries2.add(new RadarEntry(feed.getCrudeProtein() / feed.getId()));
            entries2.add(new RadarEntry(feed.getCrudeFat() / feed.getId()));
            entries2.add(new RadarEntry(feed.getCrudeFiber() / feed.getId()));
            entries2.add(new RadarEntry(feed.getCrudeAsh() / feed.getId()));
            entries2.add(new RadarEntry(feed.getCarbohydrate() / feed.getId()));
        } else {
            entries2.add(new RadarEntry(0));
            entries2.add(new RadarEntry(0));
            entries2.add(new RadarEntry(0));
            entries2.add(new RadarEntry(0));
            entries2.add(new RadarEntry(0));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Recommanded");
        set1.setColor(Color.rgb(159, 159, 159));
        set1.setFillColor(Color.rgb(235, 235, 235));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(1f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "Pet Taken");
        set2.setColor(Color.rgb(237, 121, 112));
        set2.setFillColor(Color.rgb(236, 180, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(1f);
        set2.setDrawHighlightCircleEnabled(true);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setPetAllInfo(PetAllInfos petAllInfos) {
        rer = new RER(Float.parseFloat(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT)) , petAllInfos.getFactor()).getRER();
        binding.calorieBar.invalidate();
        presenter.getTodaySumCalorie();
    }

    @Override
    public void setTodaySumCalorie(MealHistory mealHistory) {
        if (mealHistory != null) {
            if (rer > mealHistory.getCalorie()) {
                binding.calorieBar.setMax((int) rer);
                binding.rer.setText(String.valueOf(rer) + "kcal\n(" + getResources().getString(R.string.recommend) + ")");
                //initDataToSeekbar(rer);
            } else {
                binding.calorieBar.setMax((int) mealHistory.getCalorie());
                binding.rer.setText(String.valueOf(rer) + "kcal\n(" + getResources().getString(R.string.recommend) + ")");
                //initDataToSeekbar(rer, mealHistory.getCalorie());
            }
            binding.calorieBar.setProgress((int) mealHistory.getCalorie());
            //binding.calorieIntake.setText((int) mealHistory.getCalorie() + "kcal");
            binding.calorieView.setNumber(mealHistory.getCalorie());
        } else {
            binding.calorieBar.setMax((int) rer);
            binding.rer.setText(String.valueOf(rer) + "kcal\n(" + getResources().getString(R.string.recommend) + ")");
            //initDataToSeekbar(rer);
            binding.calorieBar.setProgress(0);
            //binding.calorieIntake.setText(0 + "kcal");
            binding.calorieView.setNumber(0);
        }
        binding.calorieBar.setEnabled(true);
    }

    public void onRefreshClick(View v) {
        if (!refrashState) {
            rotationStart(v);
            /* 새로고침에 대한 데이터 처리 (s) */
            refreshData();

            //데이터를 가져오기전에 임시 정지 처리
            rotationStop(v);
            /* 새로고침에 대한 데이터 처리 (e) */

        } else {
            rotationStop(v);
        }
    }
    private void refreshData () {
        nowTime = System.currentTimeMillis();
        binding.lastRefresh.setText(getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)));
    }

    private void rotationStart(View v) {
        RotateAnimation rotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(900);
        rotate.setRepeatCount(Animation.INFINITE);
        v.startAnimation(rotate);
        refrashState = true;
    }

    private void rotationStop(final View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.clearAnimation();
                v.animate().cancel();
                refrashState = false;
            }
        }, 2000);
    }

    public void onClickFloatingBtn(View v) {
        Intent intent = new Intent(getActivity(), FeedListActivity.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        presenter.initRaderChart();
        //calorie_view
        binding.calorieView.setNumber(0);
        super.onStart();
    }

    @Override
    public void onResume() {
//        binding.calorieScrollview
        presenter.getPetAllInfo();
        super.onResume();
    }
}
