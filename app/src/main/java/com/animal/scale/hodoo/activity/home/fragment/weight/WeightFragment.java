package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.databinding.DataBindingUtil;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.WeightStatistics;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.WeightStatisticsPresenter;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.FragmentWeightBinding;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.TextManager;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.listener.OnWeekChangeListener;

import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

public class WeightFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, WeightFragmentIn.View, WeightStatistics.View {

    FragmentWeightBinding binding;

    protected final String TAG = "HJLEE";

    private WeekCalendar weekCalendar;

    Animation animation;

    int randomint = 6;

    SharedPrefManager mSharedPrefManager;

    WeightFragmentIn.Presenter presenter;

    WeightStatistics.Presenter statisicsPresenter;

    private String country;

    public int bcs;
    private boolean refrashState = false;
    private String[] bcsArr;
    private long nowTime;
    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
    private View rotationView;
    int mBasicIdx = 0;

    public WeightFragment() {
    }

    public static WeightFragment newInstance() {
        return new WeightFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weight, container, false);
        binding.setActivity(this);

        mSharedPrefManager = SharedPrefManager.getInstance(getActivity());

        nowTime = System.currentTimeMillis();

        bcsArr = getResources().getStringArray(R.array.bcs_arr);
        binding.bcsSubscript.setText(getResources().getString(R.string.not_data));
        binding.lastRefresh.setText(getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)));

        binding.chartView.setNoDataText(getActivity().getString(R.string.weight_data_available));
        binding.chartView.setNoDataTextColor(getActivity().getResources().getColor(R.color.mainBlack));

        presenter = new WeightFragmentPresenter(this, binding.chartView);
        presenter.loadData(getActivity());

        binding.chart1.setNoDataText(getActivity().getString(R.string.weight_data_available));
        binding.chart1.setNoDataTextColor(getActivity().getResources().getColor(R.color.mainBlack));


        statisicsPresenter = new WeightStatisticsPresenter(this, binding.chart1);
        statisicsPresenter.initLoadData(getContext());
        statisicsPresenter.getDailyStatisticalData(TextManager.WEIGHT_DATA);
        //달력 init
        presenter.initWeekCalendar();
        country = mSharedPrefManager.getStringExtra(SharedPrefVariable.CURRENT_COUNTRY);
        if(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT).matches("")){
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }else{
        }
        return binding.getRoot();
    }

   //오늘차트
    public void serChartOfDay() {
        presenter.getDefaultData(DateUtil.getCurrentDatetime(), TextManager.WEIGHT_DATA);
    }

    //BCS or BCS DESC and TIP
    public void setBcsOrBscDescAndTip(int basicIdx) {
        mBasicIdx = basicIdx;
        presenter.getBcs(basicIdx);
    }

    public void setKg() {
        presenter.getLastCollectionData(DateUtil.getCurrentDatetime(), TextManager.WEIGHT_DATA);
    }

    @Override
    public void setAnimationGaugeChart(int bcs) {
        this.bcs = bcs;
       /* int checkBCS = 0;
        if (bcs < 3) {
            checkBCS = 0;
            //부족
        } else if (bcs > 3) {
            //초과
            checkBCS = 1;
        } else {
            checkBCS = 2;
            //적정
        }*/

        if (bcs > 0) {
            binding.bcsSubscript.setText(bcsArr[bcs - 1]);
            binding.bcsStep.setText(String.valueOf(bcs));
        } else {
            binding.bcsSubscript.setText(getResources().getString(R.string.not_data));
            binding.bcsStep.setText(String.valueOf(bcs));
        }
    }
    //여기 날짜도 들어가야함..
    @Override
    public void setLastCollectionData(RealTimeWeight d) {
        if ( d != null ) {
            if (!String.valueOf(d.getValue()).matches("")) {
                DecimalFormat fmt = new DecimalFormat("0.##");
                binding.weightView.setNumber(d.getValue());
                mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(d.getValue()));
            } else {
                binding.weightView.setNumber(0f);
                mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
            }
        } else {
            binding.weightView.setNumber(0f);
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }

        if (refrashState)
            rotationStop(rotationView);
    }

    @Override
    public void setLastCollectionDataOrSaveAvgWeight(RealTimeWeight d) {
        if (!String.valueOf(d.getValue()).matches("")) {
            DecimalFormat fmt = new DecimalFormat("0.##");
            binding.weightView.setNumber(d.getValue());
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(d.getValue()));
        } else {
            binding.weightView.setNumber(0f);
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }
        if (refrashState)
            rotationStop(rotationView);
    }

    @Override
    public void setTipMessageOfCountry(WeightTip weightTip) {
        binding.collapse.setTitle(weightTip.getTitle());
        binding.collapse.setContent(weightTip.getContent());
    }

    @Override
    public void initWeekCalendar() {
         /* binding.weekCalendar.today;
        Button todaysDate = (Button) findViewById(R.id.today);
        Button selectedDate = (Button) findViewById(R.id.selectedDateButton);
        Button startDate = (Button) findViewById(R.id.startDate);*/
        /*todaysDate.setText(new DateTime().toLocalDate().toString() + " (Reset Button)");
        selectedDate.setText(new DateTime().plusDays(50).toLocalDate().toString()  + " (Set Selected Date Button)");
        startDate.setText(new DateTime().plusDays(7).toLocalDate().toString() + " (Set Start Date Button)");*/
        binding.weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {
                DateTime dt = dateTime;
                DateTime now = new DateTime();
                String date = dt.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
                if (now.toDateTime().toString().compareTo(date) < 0) {
                } else {
                    presenter.getDefaultData(date, TextManager.WEIGHT_DATA);
                    presenter.getLastCollectionData(date, TextManager.WEIGHT_DATA);
                    presenter.setAnimationGaugeChart(bcs);
                }
            }
        });

        binding.weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {
                //Toast.makeText(getActivity(), "Week changed: " + firstDayOfTheWeek + " Forward: " + forward, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onClickResetGraph(View view) {
//        binding.clockHands.startAnimation(animation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void onClickFloatingBtn(View view) {
    }

    @Override
    public void setBcs(PetWeightInfo petWeightInfo) {
        presenter.setAnimationGaugeChart(petWeightInfo.getBcs());
        presenter.getTipMessageOfCountry(new WeightTip(country, petWeightInfo.getBcs()));
    }

    public void setBcs() {
        /*presenter.setAnimationGaugeChart(petWeightInfo.getBcs());
        presenter.getTipMessageOfCountry(new WeightTip(country, petWeightInfo.getBcs()));*/
    }

    public void onRootViewClick(View view) {
        /*((HomeActivity)getActivity()).showDropUp();*/
    }

    public void onRefreshClick(View v) {
        if (rotationView == null)
            rotationView = v;
        if (!refrashState) {
            rotationStart(v);
            /* 새로고침에 대한 데이터 처리 (s) */
            refreshData();
            /* 새로고침에 대한 데이터 처리 (e) */
        } else {
            rotationStop(v);
        }
    }

    private void refreshData() {
        nowTime = System.currentTimeMillis();
        binding.lastRefresh.setText(getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)));
        presenter.getBcs(mBasicIdx);
        presenter.getLastCollectionData(DateUtil.getCurrentDatetime(), TextManager.WEIGHT_DATA);
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

    @Override
    public void onStart() {
        //Kcal 로리 표시
//        presenter.getLastCollectionData(DateUtil.getCurrentDatetime());
//        presenter.initWeekCalendar();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.chartWrap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioId) {
                switch (radioId) {
                    case R.id.chart_day:
                        statisicsPresenter.getDailyStatisticalData(TextManager.WEIGHT_DATA);
                        break;
                    case R.id.chart_week:
                        statisicsPresenter.getWeeklyStatisticalData(TextManager.WEIGHT_DATA);
                        break;
                    case R.id.chart_month:
                        statisicsPresenter.getMonthlyStatisticalData(TextManager.WEIGHT_DATA);
                        break;
                    case R.id.chart_year:
                        statisicsPresenter.getStatisticalDataByYear(TextManager.WEIGHT_DATA);
                        break;
                }
            }
        });

    }
}
