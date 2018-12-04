package com.animal.scale.hodoo.activity.meal.update;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.detail.IngredientsOfMealActivity;
import com.animal.scale.hodoo.activity.meal.detail.IngredientsOfMealModel;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.seekbar.ProgressItem;
import com.animal.scale.hodoo.databinding.ActivityMealUpdateBinding;
import com.animal.scale.hodoo.db.DBHandler;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SearchHistory;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.RER;

import java.util.ArrayList;

public class MealUpdateActivity extends BaseActivity<MealUpdateActivity> implements MealUpdateIn.View {

    ActivityMealUpdateBinding binding;

    MealUpdateIn.Presenter presenter;

    private ArrayList<ProgressItem> progressItemList;

    private ProgressItem mProgressItem;

    private String[] decimalArray = {".0", ".25", ".5", ".75"};

    private String[] unitArray;

    private String[] doubleUnitArray = {"g", getString(R.string.cup)};

    private String[] singleUnitArray = {getString(R.string.ea)};

    private int feedId;

    private int historyIdx;

    private MealHistoryContent mealHistoryContent;

    private float darkGreySpan;

    private float rer;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_update);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.food)));
        presenter = new MealUpdatePresenter(this);
        presenter.loadData(this);
        presenter.getPetAllInfo();
        feedId = intent.getIntExtra("feedId", 0);
        presenter.getFeedInfo(feedId);
        historyIdx = intent.getIntExtra("historyIdx", 0);
        presenter.getThisHistory(historyIdx);
        dbHandler = new DBHandler(this);
        progressItemList = new ArrayList<ProgressItem>();
        binding.seekBar.initData(progressItemList);
    }

    private void setNumberPicker(NumberPicker numberPicker) {
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(1000);
        numberPicker.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
    }

    private void setDecimalNumberPicker(NumberPicker numberPicker) {
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(3);
        numberPicker.setValue(3);
        numberPicker.setDisplayedValues(decimalArray);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
    }

    private void setStringNumberPicker(NumberPicker numberPicker, Feed feed) {
        numberPicker.setMinValue(0);
        if (feed.getTag().equals("D")) {
            numberPicker.setMaxValue(1);
            numberPicker.setDisplayedValues(doubleUnitArray);
            unitArray = doubleUnitArray;
        } else if (feed.getTag().equals("S")) {
            numberPicker.setMaxValue(0);
            numberPicker.setDisplayedValues(singleUnitArray);
            unitArray = singleUnitArray;
        }
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
    }

    @Override
    protected BaseActivity<MealUpdateActivity> getActivityClass() {
        return MealUpdateActivity.this;
    }

    public void initDataToSeekbar(float rer, float kcal) {
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) (((rer * 0.7) / kcal) * 100);
        mProgressItem.color = R.color.seek_bar_gray;
        progressItemList.add(mProgressItem);

        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) (((rer * 0.3) / kcal) * 100);
        mProgressItem.color = R.color.grey;
        progressItemList.add(mProgressItem);

        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (darkGreySpan / kcal) * 100;
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);

        binding.seekBar.invalidate();
    }

    public void initDataToSeekbar(float rer) {
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (float) (((rer * 0.7) / rer) * 100);
        mProgressItem.color = R.color.seek_bar_gray;
        progressItemList.add(mProgressItem);
        // greyspan

        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (darkGreySpan / rer) * 100;
        mProgressItem.color = R.color.grey;
        progressItemList.add(mProgressItem);

        binding.seekBar.invalidate();
    }

    @Override
    public void setFeedInfo(Feed feed) {
        binding.setDomain(feed);
        setNumberPicker(binding.jungsu);
        setDecimalNumberPicker(binding.umsu);
        setStringNumberPicker(binding.unit, feed);

    }

    @Override
    public void setInsertResult(Integer result) {
        if (result == 1) {
            dbHandler.insertFeed(new SearchHistory(binding.getDomain().getName(), mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), binding.getDomain().getId(), DateUtil.getCurrentDatetime()));
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setPetAllInfo(PetAllInfos petAllInfos) {
        rer = new RER(5, petAllInfos.getFactor()).getRER();
        presenter.getTodaySumCalorie();
    }

    @Override
    public void setTodaySumCalorie(MealHistory mealHistory) {
        if (mealHistory != null) {
            if (rer > mealHistory.getCalorie()) {
                binding.seekBar.setMax((int) rer);
                initDataToSeekbar(rer);
            } else {
                binding.seekBar.setMax((int) mealHistory.getCalorie());
                initDataToSeekbar(rer, mealHistory.getCalorie());
            }
            binding.seekBar.setProgress((int) mealHistory.getCalorie());
        } else {
            binding.seekBar.setMax((int) rer);
            initDataToSeekbar(rer);
            binding.seekBar.setProgress(0);
        }
        binding.seekBar.setEnabled(true);
    }

    @Override
    public void setThisHistory(MealHistory mealHistory) {
        binding.setHistory(mealHistory);
        String[] array = String.valueOf(mealHistory.getCalorie()).split("\\.");
        binding.jungsu.setValue(extractIntegerFromFloat(mealHistory.getCalorie()));
        int nagativeValue = findDecimalArrayIndexFromNumberPicker(decimalArray, extractNegativeNumberFromFloat(mealHistory.getCalorie()));
        if(nagativeValue != -1){
            binding.umsu.setValue(nagativeValue);
        }else{
            binding.umsu.setValue(0);
        }
        if(binding.getDomain().getTag().equals("D")){
            binding.unit.setValue(mealHistory.getUnitIndex());
        }else if(binding.getDomain().getTag().equals("S")){
            binding.unit.setValue(0);
        }
    }

    @Override
    public void setUpdateResult(int result) {
        if (result == 1) {
            finish();
        }
    }

    public void onClickDetailBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), IngredientsOfMealActivity.class);
        intent.putExtra("feedId", feedId);
        startActivity(intent);
    }

    public void onClickSaveBtn(View view) {
        StringBuilder AmountOfFeed = new StringBuilder();
        AmountOfFeed.append(binding.jungsu.getValue());
        AmountOfFeed.append(decimalArray[binding.umsu.getValue()]);

        MealHistory mealHistory = MealHistory.builder()
                .historyIdx(binding.getHistory().getHistoryIdx())
                .calorie(Float.parseFloat(AmountOfFeed.toString()))
                .unitIndex(binding.unit.getValue())
                .unitString(unitArray[binding.unit.getValue()])
                .feedIdx(feedId)
                .petIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX))
                .groupId(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE))
                .userIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID))
                .build();
        presenter.updateMeal(mealHistory);
    }

    public int findDecimalArrayIndexFromNumberPicker(String arr[] , String s) {
        for (int i=0; i < arr.length; i++)
            if(arr[i].matches("." + s))
                return i;

        return -1;
    }


    public String extractNegativeNumberFromFloat(float calorie){
        String[] array = String.valueOf(calorie).split("\\.");
        if(array.length > 0){
            return array[array.length - 1];
        }
        return null;
    }
    public int extractIntegerFromFloat(float calorie){
        String[] array = String.valueOf(calorie).split("\\.");
        if(array.length > 0){
            return Integer.parseInt(array[0]);
        }
       return Integer.parseInt(String.valueOf(calorie));
    }
}
