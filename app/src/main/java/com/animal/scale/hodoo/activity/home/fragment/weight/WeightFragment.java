package com.animal.scale.hodoo.activity.home.fragment.weight;

        import android.databinding.DataBindingUtil;
        import android.os.Build;
        import android.os.Bundle;
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
        import android.widget.Toast;

        import com.animal.scale.hodoo.R;
        import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
        import com.animal.scale.hodoo.common.SharedPrefManager;
        import com.animal.scale.hodoo.databinding.FragmentWeightBinding;
        import com.animal.scale.hodoo.domain.PetWeightInfo;
        import com.animal.scale.hodoo.domain.RealTimeWeight;
        import com.animal.scale.hodoo.util.DateUtil;

        import org.joda.time.DateTime;
        import org.joda.time.format.DateTimeFormat;

        import java.text.DecimalFormat;

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
        binding.chartView.setNoDataText("Description that you want");
        presenter = new WeightFragmentPresenter(this, binding.chartView);
        presenter.loadData(getActivity());
        //Kcal 로리 표시
        presenter.getLastCollectionData(DateUtil.getCurrentDatetime());
        presenter.initWeekCalendar();
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
            binding.graphBg.setBackgroundResource(R.drawable.weight_middle_thin_469_266);
            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_one_step);
        }else if(bcs > 2){
            binding.graphBg.setBackgroundResource(R.drawable.weight_middle_overweight_469_266);
            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_three_step);
        }else{
            binding.graphBg.setBackgroundResource(R.drawable.weight_middle_ideal_469_266);
            animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_two_step);
        }
        binding.clockHands.startAnimation(animation);
    }

    //여기 날짜도 들어가야함..
    @Override
    public void setLastCollectionData(RealTimeWeight d) {
        if(d != null){
            DecimalFormat fmt = new DecimalFormat("0.##");
            String decimal =  fmt.format(d.getValue());
            binding.currentKg.setText(decimal + "Kg");
        }else{
            binding.currentKg.setText("0 Kg");
        }
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
        binding.clockHands.startAnimation(animation);
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
        binding.myBcsStep.setText("BCS " + (petWeightInfo.getBcs() + 1) + "단계");
        presenter.setAnimationGaugeChart(petWeightInfo.getBcs());
    }

    public void onRootViewClick(View view){
        /*((HomeActivity)getActivity()).showDropUp();*/
    }

    @Override
    public void onStart() {
        //Kcal 로리 표시
        presenter.getLastCollectionData(DateUtil.getCurrentDatetime());
        presenter.initWeekCalendar();
        super.onStart();
    }
}
