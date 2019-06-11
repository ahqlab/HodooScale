package com.animal.scale.hodoo.activity.home.fragment.weight;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.WeightStatistics;
import com.animal.scale.hodoo.activity.home.fragment.weight.statistics.WeightStatisticsPresenter;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.dialog.WeightDialog;
import com.animal.scale.hodoo.databinding.FragmentWeightBinding;
import com.animal.scale.hodoo.domain.HodooIndex;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.WeightGoalChart;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.MathUtil;
import com.animal.scale.hodoo.util.TextManager;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.listener.OnWeekChangeListener;

import static com.animal.scale.hodoo.R.style.AppTheme;
import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

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
    private boolean refrashState = true;
    private String[] bcsArr;
    private long nowTime;
    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
    private View rotationView;
    int mBasicIdx = 0;
    private PetAllInfos selectPet;

    private String calendarDate = "";

    private boolean realTimeMode = true;

    private int currentChart = 0;

    private WeightDialog weightDialog;

    private HodooApplication app;

    private DatePickerDialog picker;

    Calendar weekCal;

    String currentWeekCalendarDate;
    String currentWeekCalendarMonth;
    String currentWeekCalendarYear;

    private int unitIdx;

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
        //binding.lastRefresh.setText(getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)));

        //binding.chartView.setNoDataText(getActivity().getString(R.string.weight_data_available));
        //binding.chartView.setNoDataTextColor(getActivity().getResources().getColor(R.color.mainBlack));

        //presenter = new WeightFragmentPresenter(this, binding.chartView);
        presenter = new WeightFragmentPresenter(this);
        presenter.loadData(getActivity());

        binding.chart1.setNoDataText(getActivity().getString(R.string.weight_data_available));
        binding.chart1.setNoDataTextColor(getActivity().getResources().getColor(R.color.mainBlack));

        statisicsPresenter = new WeightStatisticsPresenter(this, binding.chart1);
        statisicsPresenter.initLoadData(getContext());
        statisicsPresenter.getDailyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarDate != null ? currentWeekCalendarDate : DateUtil.getCurrentDatetime()));
        //달력 init

        country = mSharedPrefManager.getStringExtra(SharedPrefVariable.CURRENT_COUNTRY);
        if (mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT).matches("")) {
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }

        if (getArguments() != null) {
            if (getArguments().getBoolean("push"))
                setCalendar();
            selectPet = (PetAllInfos) getArguments().getSerializable("selectPet");
            if (null != selectPet) {
                setBcsOrBscDescAndTip(selectPet);
                serChartOfDay();
                presenter.getTipMessageOfCountry(new WeightTip(country, selectPet.getPetWeightInfo().getBcs()));
                setWeight();
                //presenter.getTipMessageOfCountry();
            }
        }
        return binding.getRoot();
    }

    public void getTipMessageOfCountry(PetAllInfos selectPet) {
        if (((HodooApplication) getActivity().getApplication()).isExperienceState())
            return;
        if (selectPet == null)
            return;
        presenter.getTipMessageOfCountry(new WeightTip(country, selectPet.getPetWeightInfo().getBcs()));
    }

    //오늘차트
    public void serChartOfDay() {
        //presenter.getDefaultData(DateUtil.getCurrentDatetime(), TextManager.WEIGHT_DATA);
    }

    //BCS or BCS DESC and TIP
    public void setBcsOrBscDescAndTip(PetAllInfos petAllInfos) {
        if (((HodooApplication) getActivity().getApplication()).isExperienceState())
            return;

        if (petAllInfos == null)
            return;
        selectPet = petAllInfos;
        setBcsAndBcsDesc(petAllInfos.getPetWeightInfo().getBcs());
    }

    //오늘의 평균 체중
    public void setKg() {
        presenter.getLastCollectionData(HomeActivity.getCalendarDate().equals("") ? DateUtil.getCurrentDatetime() : HomeActivity.getCalendarDate(), TextManager.WEIGHT_DATA);
    }

    //
    public void refrashChart() {
        switch (currentChart) {
            case 0:
                statisicsPresenter.getDailyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarDate != null ? currentWeekCalendarDate : DateUtil.getCurrentDatetime()));
                break;
            case 1:
                statisicsPresenter.getWeeklyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarDate != null ? currentWeekCalendarDate : DateUtil.getCurrentDatetime()), (currentWeekCalendarYear != null ? currentWeekCalendarYear : DateUtil.getCurrentYear()),(currentWeekCalendarMonth != null ? currentWeekCalendarMonth : DateUtil.getCurrentMonth()) );
                break;
            case 2:
                statisicsPresenter.getMonthlyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarMonth != null ? currentWeekCalendarMonth : DateUtil.getCurrentMonth()));
                break;
        }
    }

    @Override
    public void setWeightGoal(HodooIndex d) {
        //목표체중
        float minGoal = d.getMinWeightStd();
        float maxGoal = d.getMaxWeightStd();

        DecimalFormat df = new DecimalFormat("#.#");
        String minGoalStr = df.format( unitIdx == 1 ? MathUtil.kgTolb(minGoal) : minGoal );
        String maxGoalStr = df.format( unitIdx == 1 ? MathUtil.kgTolb(maxGoal) : maxGoal );
        binding.goal.setText(minGoalStr + getResources().getStringArray(R.array.weight_unit)[unitIdx] + " ~ " + maxGoalStr + getResources().getStringArray(R.array.weight_unit)[unitIdx]);
        binding.weightGoalDesc.setText(d.getHodooIndexDesc());
        /*if (goal == 0) {
            binding.goal.setText((int) goal + "Kg");
        } else {
            DecimalFormat df = new DecimalFormat("#.#");
            String result = df.format( unitIdx == 1 ? MathUtil.kgTolb(goal) : goal );
            String result = df.format( unitIdx == 1 ? MathUtil.kgTolb(goal) : goal );
            binding.goal.setText(result + getResources().getStringArray(R.array.weight_unit)[unitIdx]);
        }*/
    }


    @Override
    public void setLastCollectionData(RealTimeWeight d) {
        if (d != null) {
            DecimalFormat fmt = new DecimalFormat("0.#");
            binding.weightView.setNumber(d.getValue());
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(d.getValue()));
        } else {
            binding.weightView.setNumber(0f);
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }
    }

    @Override
    public void setBcsAndBcsDesc(int bcs) {
        if (((HodooApplication) getActivity().getApplication()).isExperienceState())
            return;
        Log.e("HJLEE", selectPet.toString());
        float currentWeight = Float.parseFloat(selectPet.getPetPhysicalInfo().getWeight());
//        int bodyFat = selectPet.getPetUserSelectionQuestion().getBodyFat();
        int petType = selectPet.getPetBasicInfo().getPetType();
        presenter.getWeightGoal(selectPet.getPet().getPetIdx());

        setWeight();

        //this.bcs = bcs;
        //if (bcs < 3) {checkBCS = 0; //부족 } else if (bcs > 3) { //초과 checkBCS = 1; } else { checkBCS = 2; //적정 }*/

        //int tempBcs = bcs;
        //if ( bcs >= 20 ) { //완전히 로직을 바꾸기전 임시로 분기문으로 처리
        //    this.bcs = bcs = bcs / 10 - 1;
        //    if ( selectPet != null )
        //        selectPet.getPetWeightInfo().setBcs(bcs);
        //}

        //if (bcs > 0) {
        //    binding.bcsSubscript.setText(bcsArr[bcs - 1]);
        //    binding.bcsStep.setText(String.valueOf(tempBcs));
        //} else {
        //    binding.bcsSubscript.setText(getResources().getString(R.string.not_data));
        //    binding.bcsStep.setText(String.valueOf(tempBcs));
        // }
    }


    public void testRefrashKg() {
        binding.weightView.setNumber(0);
    }

    @Override
    public void setLastCollectionDataOrSaveAvgWeight(RealTimeWeight d) {
        //if ( realTimeMode ) {
        if (d != null) {
            DecimalFormat fmt = new DecimalFormat("0.##");
            binding.weightView.setNumber(d.getValue());
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(d.getValue()));
        } else {
            binding.weightView.setNumber(0f);
            mSharedPrefManager.putStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT, String.valueOf(0));
        }

        /*} else {
            if ( HomeActivity.selectPet != null )
                if ( HomeActivity.selectPet.petPhysicalInfo != null )
                  binding.weightView.setNumber(Float.parseFloat(HomeActivity.selectPet.petPhysicalInfo.getWeight()));
        }
        if (refrashState)
            rotationStop(rotationView);*/
    }

    @Override
    public void setTipMessageOfCountry(WeightTip weightTip) {
        HomeActivity.setWeightTip(weightTip);
        //binding.collapse.setTitle(weightTip.getTitle());
        // binding.collapse.setContent(weightTip.getContent());
    }

    @Override
    public void setCalendar() {
        binding.weekCalendar.setSelectedDate(DateTime.now());
    }


    @Override
    public void initWeekCalendar() {


        weekCal = Calendar.getInstance();
        DateFormat formatter1 = DateFormat.getDateInstance(DateFormat.DEFAULT);
        formatter1.setTimeZone(weekCal.getTimeZone());
        String formatted1 = formatter1.format(weekCal.getTime());
        binding.thisYear.setText(formatted1);

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

                currentWeekCalendarDate = dt.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
                currentWeekCalendarMonth = dt.toString(DateTimeFormat.forPattern("MM"));
                currentWeekCalendarYear = dt.toString(DateTimeFormat.forPattern("yyyy"));

                weekCal = dateTime.toCalendar(Locale.getDefault());
                DateFormat formatter1 = DateFormat.getDateInstance(DateFormat.DEFAULT);
                formatter1.setTimeZone(weekCal.getTimeZone());
                String formatted1 = formatter1.format(weekCal.getTime());
                binding.thisYear.setText(formatted1);

                HomeActivity.setCalendarDate(currentWeekCalendarDate);
                if (now.toDateTime().toString().compareTo(currentWeekCalendarDate) < 0) {
                } else {
                    presenter.getLastCollectionData(currentWeekCalendarDate, TextManager.WEIGHT_DATA);
                    presenter.setBcsAndBcsDesc(bcs);
                    refrashChart();
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
        if (HomeActivity.mWeightTip == null)
            presenter.getTipMessageOfCountry(new WeightTip(country, petWeightInfo.getBcs()));
        if (HomeActivity.mWeightTip != null)
            if (!country.equals(HomeActivity.mWeightTip.getLanguage()))
                presenter.getTipMessageOfCountry(new WeightTip(country, petWeightInfo.getBcs()));
        setKg();
    }

    /*
     * 새로고침 버튼을 클릭했을 경우(현재 사용하지 않음)
     *
     * @param View
     * @return 
    */
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
        //binding.lastRefresh.setText(getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)));
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
                if (v != null) {
                    v.clearAnimation();
                    v.animate().cancel();
                }
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

//        setWeight();
        app = (HodooApplication) getActivity().getApplication();
        binding.chartWrap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioId) {
                if (app.isExperienceState())
                    return;

                switch (radioId) {
                    case R.id.chart_day:
                        currentChart = 0;
                        statisicsPresenter.getDailyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarDate != null ? currentWeekCalendarDate : DateUtil.getCurrentDatetime()));
                        break;
                    case R.id.chart_week:
                        currentChart = 1;
                        statisicsPresenter.getWeeklyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarDate != null ? currentWeekCalendarDate : DateUtil.getCurrentDatetime()), (currentWeekCalendarYear != null ? currentWeekCalendarYear : DateUtil.getCurrentYear()),(currentWeekCalendarMonth != null ? currentWeekCalendarMonth : DateUtil.getCurrentMonth()) );
                        break;
                    case R.id.chart_month:
                        currentChart = 2;
                        statisicsPresenter.getMonthlyStatisticalData(TextManager.WEIGHT_DATA, (currentWeekCalendarMonth != null ? currentWeekCalendarMonth : DateUtil.getCurrentMonth()));
                        break;
                /*    case R.id.chart_year:
                        statisicsPresenter.getStatisticalDataByYear(TextManager.WEIGHT_DATA);
                        break;*/
                }
                if (currentChart == 1)
                    binding.weekRateWrap.setVisibility(View.VISIBLE);
                else
                    binding.weekRateWrap.setVisibility(View.GONE);
            }
        });

        presenter.initWeekCalendar();
        /*if ( HomeActivity.mWeightTip != null ) {
            //Log.e(TAG, "country : " + country + " HomeActivity.mWeightTip.getLanguage() : " + HomeActivity.mWeightTip.getLanguage());
            if (country.equals(HomeActivity.mWeightTip.getLanguage()))
                setTipMessageOfCountry(HomeActivity.mWeightTip);
        }*/
        setKg();
        //setWeightGoal();
    }

    /*
     * 화면에 몸무게를 출력한다.
     *
     * @param 
     * @return 
    */
    private void setWeight() {
        unitIdx = presenter.getWeightUnit();
        float petWeight = Float.valueOf(selectPet.getPet().getFixWeight());
        float compareWeight = Float.parseFloat(selectPet.getPetPhysicalInfo().getWeight()) - petWeight;
        DecimalFormat df = new DecimalFormat("#.#");
        String[] unitArr = getResources().getStringArray(R.array.weight_unit);
        binding.nowWeight.setText(
                Float.parseFloat(
                        selectPet.getPetPhysicalInfo().getWeight()) != 0 ?
                        (unitIdx == 1 ? String.valueOf( df.format(MathUtil.kgTolb( (Float.parseFloat(selectPet.getPetPhysicalInfo().getWeight()))  )) ) : selectPet.getPetPhysicalInfo().getWeight()) + getResources().getStringArray(R.array.weight_unit)[unitIdx]  :
                        "-");
        binding.petWeight.setText(df.format( unitIdx == 1 ? MathUtil.kgTolb(petWeight) : petWeight ) + getResources().getStringArray(R.array.weight_unit)[unitIdx]);
        binding.compareWeight.setText(compareWeight > 0 ? "+" + df.format( unitIdx == 1 ? MathUtil.kgTolb(compareWeight) : compareWeight ) : df.format( unitIdx == 1 ? MathUtil.kgTolb(compareWeight) : compareWeight ));

        /* 체중 */
        String fixWeight = binding.petWeight.getText().toString();
        fixWeight = fixWeight.replace(getResources().getStringArray(R.array.weight_unit)[unitIdx], "");
        if ( unitIdx == 1 )
            fixWeight = String.valueOf( MathUtil.kgTolb( Float.parseFloat(fixWeight)  ) );
        if (Float.parseFloat(fixWeight) == 0)
            binding.petWeight.setText(selectPet.getPetPhysicalInfo().getWeight() + getResources().getStringArray(R.array.weight_unit)[unitIdx]);
        presenter.getWeekRate();
        binding.editWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weightDialog = new WeightDialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen, selectPet, new WeightDialog.KeypadCallback() {
                    @Override
                    public void keypadCallback(PetAllInfos petAllInfos) {
                        if (DEBUG) Log.e(TAG, "debug");
                        petAllInfos.getPetPhysicalInfo().setId(selectPet.pet.getPhysical());
                        presenter.updatePhysical(petAllInfos.petPhysicalInfo);
                    }
                });
            }
        });
    }

    /*
     * 몸무게 수정 완료버튼을 클릭했을때 사용
     *
     * @param PetPhysicalInfo
     * @return
    */
    @Override
    public void physicalUpdateDone(PetPhysicalInfo result) {
        if (result != null) {
            weightDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle(getResources().getString(R.string.change_compleate))
                    .setMessage(getResources().getString(R.string.change_compleate_message))
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            refrashChart();
                        }
                    });
            builder.create().show();
            selectPet.setPetPhysicalInfo(result);
            setWeight();
        }
    }

    /*
     * 전 주 대비 감량 비율을 출력
     *
     * @param float rate 감량비
     * @return
    */
    @Override
    public void setWeekRate(float rate) {
        if (rate > 0)
            binding.rateArrow.setRotation(-180);
        else
            binding.rateArrow.setRotation(0);
        binding.weekRate.setText(String.format("%.2f", rate) + "%");
    }

    public void showDatePickerDialog(View view) {
        int day = weekCal.get(Calendar.DAY_OF_MONTH);
        int month = weekCal.get(Calendar.MONTH);
        int year = weekCal.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        view.setMaxDate(Calendar.getInstance().getTimeInMillis());

                        DateTime dialogSelectedDate = new DateTime(year,  (monthOfYear + 1) , dayOfMonth, 12, 12);

                        weekCal = dialogSelectedDate.toCalendar(Locale.getDefault());
                        DateFormat formatter1 = DateFormat.getDateInstance(DateFormat.DEFAULT);
                        formatter1.setTimeZone(weekCal.getTimeZone());
                        String formatted1 = formatter1.format(weekCal.getTime());
                        binding.thisYear.setText(formatted1);


                        binding.weekCalendar.setSelectedDate( dialogSelectedDate );

                        currentWeekCalendarDate = dialogSelectedDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
                        currentWeekCalendarMonth = dialogSelectedDate.toString(DateTimeFormat.forPattern("MM"));
                        currentWeekCalendarYear = dialogSelectedDate.toString(DateTimeFormat.forPattern("yyyy"));

                        refrashChart();

                    }
                }, year, month, day);
        picker.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        picker.show();
    }

}
