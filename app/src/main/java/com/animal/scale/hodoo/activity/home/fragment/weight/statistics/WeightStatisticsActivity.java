package com.animal.scale.hodoo.activity.home.fragment.weight.statistics;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityWeightStatisticsBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.DateUtil;

public class WeightStatisticsActivity extends BaseActivity<WeightStatisticsActivity> implements WeightStatistics.View {

    ActivityWeightStatisticsBinding binding;

    WeightStatistics.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weight_statistics);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_statistic)));
        super.setToolbarColor();
        presenter = new WeightStatisticsPresenter(this, binding.chart1);
        presenter.initLoadData(WeightStatisticsActivity.this);
       // presenter.getDailyStatisticalData();
    }

    @Override
    protected BaseActivity<WeightStatisticsActivity> getActivityClass() {
        return WeightStatisticsActivity.this;
    }

    public void onClickDayBtn(View vIew) {
        binding.dayBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_pink_bg));
        binding.dayBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_midle_pink));
        binding.weekBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.weekBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.monthBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.monthBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.yearBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.yearBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
      //  presenter.getDailyStatisticalData();
    }

    public void onClickWeekBtn(View vIew) {
        binding.dayBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.dayBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.weekBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_pink_bg));
        binding.weekBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_midle_pink));
        binding.monthBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.monthBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.yearBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.yearBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        //presenter.getWeeklyStatisticalData();
    }

    public void onClickMonthBtn(View vIew) {
        binding.dayBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.dayBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.weekBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.weekBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.monthBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_pink_bg));
        binding.monthBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_midle_pink));
        binding.yearBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.yearBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
       // presenter.getMonthlyStatisticalData();
    }

    public void onClickYearBtn(View vIew) {
        binding.dayBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.dayBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.weekBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.weekBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.monthBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_white_bg));
        binding.monthBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_text_gray));
        binding.yearBtn.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.much_rounded_pink_bg));
        binding.yearBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.hodoo_midle_pink));
     //   presenter.getStatisticalDataByYear();
    }
}
