package com.animal.scale.hodoo.activity.home.fragment.weight;

        import android.content.res.TypedArray;
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
        import android.view.animation.AnimationUtils;
        import android.view.animation.RotateAnimation;
        import android.widget.Toast;

        import com.animal.scale.hodoo.R;
        import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
        import com.animal.scale.hodoo.common.SharedPrefManager;
        import com.animal.scale.hodoo.databinding.FragmentWeightBinding;
        import com.animal.scale.hodoo.domain.PetWeightInfo;
        import com.animal.scale.hodoo.domain.RealTimeWeight;
        import com.animal.scale.hodoo.util.DateUtil;
        import com.robinhood.ticker.TickerUtils;

        import org.joda.time.DateTime;
        import org.joda.time.format.DateTimeFormat;

        import java.text.DecimalFormat;
        import java.text.SimpleDateFormat;
        import java.util.Date;

        import noman.weekcalendar.WeekCalendar;
        import noman.weekcalendar.listener.OnDateClickListener;
        import noman.weekcalendar.listener.OnWeekChangeListener;

public class WeightFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener , WeightFragmentIn.View{

    protected final String TAG = "HJLEE";

    private WeekCalendar weekCalendar;

    FragmentWeightBinding binding;

    Animation animation;

    int randomint = 6;

    SharedPrefManager mSharedPrefManager;

    WeightFragmentIn.Presenter presenter;

    public int bcs;
    private boolean refrashState = false;
    private String[] bcsArr;
    private long nowTime;
    private SimpleDateFormat lastRefreshSdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

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
//        binding.chartView.setNoDataText("Description that you want");

        /*칼로리 숫자 초기설정 (s)*/
        binding.tickerView.setCharacterLists(TickerUtils.provideNumberList());
        binding.tickerView.setText( String.valueOf(0));
        /*칼로리 숫자 초기설정 (e)*/

        bcsArr = getResources().getStringArray(R.array.bcs_arr);
        binding.bcsSubscript.setText(bcsArr[0]);
        nowTime = System.currentTimeMillis();
        binding.lastRefresh.setText( getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)) );


        presenter = new WeightFragmentPresenter(this, binding.chartView);
        presenter.loadData(getActivity());
//        //Kcal 로리 표시
//        presenter.getLastCollectionData(DateUtil.getCurrentDatetime());
//        presenter.initWeekCalendar();
        /*((HomeActivity)getActivity()).binding.appBarNavigation.petImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               *//* Log.e("HJLEE", "height : " + ((HomeActivity)getActivity()).binding.appBarNavigation.petCustomSpinner.getHeight());
                if(((HomeActivity)getActivity()).binding.appBarNavigation.petCustomSpinner.getHeight() == 0){
                    Log.e("HJLEE", "DOWN");
                    ((HomeActivity)getActivity()).showDropdown();
                }else{
                    Log.e("HJLEE", "UP");
                    ((HomeActivity)getActivity()).showDropUp();
                }*//*
            }
        });*/
        return binding.getRoot();
    }
    /* Call from the Home Activity */
    //차트를 그린다.. 동적 로딩 OK
    public void drawChart(){
        presenter.getDefaultData(DateUtil.getCurrentDatetime());
    }
    //BCS 를
    public void setBcsMessage(int basicIdx) {
        presenter.getBcs(basicIdx);
    }

    @Override
    public void setAnimationGaugeChart(int bcs){
        this.bcs = bcs;
        if(bcs < 2){
            //부족
//            binding.graphBg.setBackgroundResource(R.drawable.weight_middle_thin_469_266);
//            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_one_step);
        } else if(bcs > 2){
            //초과
//            binding.graphBg.setBackgroundResource(R.drawable.weight_middle_overweight_469_266);
//            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_three_step);
        } else {
            //적정
//            binding.graphBg.setBackgroundResource(R.drawable.weight_middle_ideal_469_266);
//            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_two_step);
        }
        Log.e(TAG, String.format("bcs : %d", bcs));
        binding.bcsSubscript.setText(bcsArr[bcs - 1]);
        binding.bcsStep.setText( String.valueOf(bcs) );
//        binding.clockHands.startAnimation(animation);
    }

    //여기 날짜도 들어가야함..
    @Override
    public void setLastCollectionData(RealTimeWeight d) {
//        if(d != null){
//            DecimalFormat fmt = new DecimalFormat("0.##");
//            String decimal =  fmt.format(d.getValue());
//            binding.currentKg.setText(decimal + "Kg");
//        }else{
//            binding.currentKg.setText("0 Kg");
//        }
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
                String date = dt.toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
                presenter.getDefaultData(date);
                //setBcsMessage(info.getPet().getBasic());
                //weightFragment.drawChart();
                presenter.getLastCollectionData(date);
                presenter.setAnimationGaugeChart(bcs);
            }
        });

        binding.weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, boolean forward) {
                Toast.makeText(getActivity(), "Week changed: " + firstDayOfTheWeek + " Forward: " + forward, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClickResetGraph(View view){
//        binding.clockHands.startAnimation(animation);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void onClickFloatingBtn(View view){
        /*Log.e("HJLEE", "height : " + ((HomeActivity)getActivity()).binding.appBarNavigation.petCustomSpinner.getHeight());
        if(((HomeActivity)getActivity()).binding.appBarNavigation.petCustomSpinner.getHeight() == 0){
            Log.e("HJLEE", "DOWN");
            ((HomeActivity)getActivity()).showDropdown();
        }else{
            Log.e("HJLEE", "UP");
            ((HomeActivity)getActivity()).showDropUp();
        }*/
    }

    @Override
    public void setBcs(PetWeightInfo petWeightInfo) {
//        binding.myBcsStep.setText("BCS " + (petWeightInfo.getBcs() + 1) + "단계");
        presenter.setAnimationGaugeChart(petWeightInfo.getBcs());
    }

    public void onRootViewClick(View view){
        /*((HomeActivity)getActivity()).showDropUp();*/
    }
    public void onRefreshClick(View v) {
        Log.e(TAG, "refrash click");
        if ( !refrashState ) {
            RotateAnimation rotate = new RotateAnimation(
                    0, 360,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );

            rotate.setDuration(900);
            rotate.setRepeatCount(Animation.INFINITE);
            v.startAnimation(rotate);

            /* 새로고침에 대한 데이터 처리 (s) */

            nowTime = System.currentTimeMillis();
            binding.lastRefresh.setText( getString(R.string.last_sync_refresh_str) + " " + lastRefreshSdf.format(new Date(nowTime)) );

            /* 새로고침에 대한 데이터 처리 (e) */
            refrashState = true;
        } else {
            v.clearAnimation();
            v.animate().cancel();
            refrashState = false;
        }
    }

    @Override
    public void onStart() {
        //Kcal 로리 표시
//        presenter.getLastCollectionData(DateUtil.getCurrentDatetime());
//        presenter.initWeekCalendar();

        final int testNumberMax = 14;
        for (float i = 0; i < testNumberMax; i++) {
            Log.e(TAG, String.format("%.2f", i));
            binding.tickerView.setText( String.format("%.2f", i) );
        }
        binding.tickerView.setText( String.valueOf(4.36f));

        binding.weightView.setNumber(28.3f);


        super.onStart();
    }
}
