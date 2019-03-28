package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.databinding.DataBindingUtil;
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
import android.widget.RadioGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.WeightStatistics;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.WeightStatisticsPresenter;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.FragmentWeightBinding;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.PetAllInfos;
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

public class WeightFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, WeightFragmentIn.View, WeightStatistics.View {

    FragmentWeightBinding binding;

    protected final String TAG = "HJLEE";

    private WeekCalendar weekCalendar;

    Animation animation;

    String country;

    int randomint = 6;

    SharedPrefManager mSharedPrefManager;

    WeightFragmentIn.Presenter presenter;

    WeightStatistics.Presenter statisicsPresenter;

    public int bcs;
    private boolean refrashState = false;
    private String[] bcsArr;
    private long nowTime;
    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
    private View rotationView;
    int mBasicIdx = 0;
    private PetAllInfos selectPet;

    private String calendarDate = "";

    private boolean realTimeMode = false;

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

        country = mSharedPrefManager.getStringExtra(SharedPrefVariable.CURRENT_COUNTRY);
        if(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT).matches("")){
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }

        if ( getArguments() != null ) {
            if ( getArguments().getBoolean("push") )
                setCalendar();
            selectPet = (PetAllInfos) getArguments().getSerializable("selectPet");
            if (null != selectPet){
                setBcsOrBscDescAndTip(selectPet);
                serChartOfDay();
                presenter.getTipMessageOfCountry(new WeightTip(country, selectPet.getPetWeightInfo().getBcs()));
                //presenter.getTipMessageOfCountry();
            }
        }
        return binding.getRoot();
    }

    public void getTipMessageOfCountry(PetAllInfos selectPet) {
        presenter.getTipMessageOfCountry(new WeightTip(country, selectPet.getPetWeightInfo().getBcs()));
    }

   //오늘차트
    public void serChartOfDay() {
        presenter.getDefaultData(DateUtil.getCurrentDatetime(), TextManager.WEIGHT_DATA);
    }

    //BCS or BCS DESC and TIP
    public void setBcsOrBscDescAndTip(PetAllInfos petAllInfos) {
        setBcsAndBcsDesc(petAllInfos.getPetWeightInfo().getBcs());
    }
    //오늘의 평균 체중
    public void setKg() {
        presenter.getLastCollectionData(HomeActivity.getCalendarDate().equals("") ? DateUtil.getCurrentDatetime() : HomeActivity.getCalendarDate(), TextManager.WEIGHT_DATA);
    }

    @Override
    public void setBcsAndBcsDesc(int bcs) {
        this.bcs = bcs;
        //if (bcs < 3) {checkBCS = 0; //부족 } else if (bcs > 3) { //초과 checkBCS = 1; } else { checkBCS = 2; //적정 }*/
        if (bcs > 0) {
            binding.bcsSubscript.setText(bcsArr[bcs - 1]);
            binding.bcsStep.setText(String.valueOf(bcs));
        } else {
            binding.bcsSubscript.setText(getResources().getString(R.string.not_data));
            binding.bcsStep.setText(String.valueOf(bcs));
        }
    }


    public void testRefrashKg(){
        binding.weightView.setNumber(0);
    }

    @Override
    public void setLastCollectionDataOrSaveAvgWeight(RealTimeWeight d) {
        if ( realTimeMode ) {
            if ( d != null ) {
                DecimalFormat fmt = new DecimalFormat("0.##");
                binding.weightView.setNumber(d.getValue());
                mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(d.getValue()));
            } else {
                binding.weightView.setNumber(0f);
                mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
            }
        } else {
            if ( HomeActivity.selectPet != null )
                if ( HomeActivity.selectPet.petPhysicalInfo != null )
                  binding.weightView.setNumber(Integer.parseInt(HomeActivity.selectPet.petPhysicalInfo.getWeight()));
        }

        if (refrashState)
            rotationStop(rotationView);
    }

    @Override
    public void setTipMessageOfCountry(WeightTip weightTip) {
        HomeActivity.setWeightTip(weightTip);
        binding.collapse.setTitle(weightTip.getTitle());
        binding.collapse.setContent(weightTip.getContent());
    }

    @Override
    public void setCalendar() {
        binding.weekCalendar.setSelectedDate( DateTime.now() );
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
                HomeActivity.setCalendarDate(date);
                if (now.toDateTime().toString().compareTo(date) < 0) {
                } else {
                    presenter.getDefaultData(date, TextManager.WEIGHT_DATA);
                    presenter.getLastCollectionData(date, TextManager.WEIGHT_DATA);
                    presenter.setBcsAndBcsDesc(bcs);
                }
            }
        });

        binding.weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void setBcs(PetWeightInfo petWeightInfo) {
        presenter.setBcsAndBcsDesc(petWeightInfo.getBcs());
        if ( HomeActivity.mWeightTip == null )
            presenter.getTipMessageOfCountry(new WeightTip(country, petWeightInfo.getBcs()));
        if ( HomeActivity.mWeightTip != null )
            if ( !country.equals(HomeActivity.mWeightTip.getLanguage()) )
                presenter.getTipMessageOfCountry(new WeightTip(country, petWeightInfo.getBcs()));
        setKg();
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
        /* 리프레쉬 할때 BCS 까지 변경 될 필요는 없을꺼 같음 */
        //presenter.getBcs(mBasicIdx);
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
        super.onStart();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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

        presenter.initWeekCalendar();
        /*if ( HomeActivity.mWeightTip != null ) {
            //Log.e(TAG, "country : " + country + " HomeActivity.mWeightTip.getLanguage() : " + HomeActivity.mWeightTip.getLanguage());
            if (country.equals(HomeActivity.mWeightTip.getLanguage()))
                setTipMessageOfCountry(HomeActivity.mWeightTip);
        }*/
        setKg();
    }
}
