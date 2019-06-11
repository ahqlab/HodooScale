package com.animal.scale.hodoo.activity.meal.regist;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.detail.IngredientsOfMealActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.dialog.IngredientsOfMealDialog;
import com.animal.scale.hodoo.custom.view.MeterageCup;
import com.animal.scale.hodoo.custom.view.seekbar.ProgressItem;
import com.animal.scale.hodoo.databinding.ActivityMealRegistrationBinding;
import com.animal.scale.hodoo.db.DBHandler;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SearchHistory;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.MathUtil;
import com.animal.scale.hodoo.util.RER;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.util.ArrayList;

/**
 *
 */
public class MealRegistrationActivity extends BaseActivity<MealRegistrationActivity> implements MealRegistrationIn.View {

    ActivityMealRegistrationBinding binding;

    MealRegistrationIn.Presenter presenter;

    private ArrayList<ProgressItem> progressItemList;

    private ProgressItem mProgressItem;

    private String[] decimalArray = {".0", ".25", ".5", ".75"};

    private String[] unitArray;

    private String[] doubleUnitArray;

    private String[] singleUnitArray;

    private int feedId;

    private float darkGreySpan;

    private float rer;

    private DBHandler dbHandler;

    IngredientsOfMealDialog dialog;

    private PetAllInfos selectPet;

    private MealHistory mealHistory;

    private float resultCalorie = 0;
    private float calorieInit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_registration);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getResources().getString(R.string.food)));
        doubleUnitArray = new String[]{"g", this.getResources().getString(R.string.cup)};
        singleUnitArray = new String[]{this.getResources().getString(R.string.ea)};

        presenter = new MealRegistrationPresenter(this);
        presenter.loadData(this);
        feedId = intent.getIntExtra("feedId", 0);
        selectPet = (PetAllInfos) intent.getSerializableExtra("selectPet");
        setPetAllInfo();
        presenter.getFeedInfo(feedId);

        dbHandler = new DBHandler(this);
        progressItemList = new ArrayList<ProgressItem>();
        //binding.seekBar.initData(progressItemList);
    }

    /**
     * 사료량을 입력하기 위한 넘버 피커 셋팅
     * 지금은 사용안함.
     * @param numberPicker
     */
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
    /**
     * 사료량을 입력하기 위한 넘버 피커 셋팅
     * 지금은 사용안함.
     * @param numberPicker
     */
    private void setDecimalNumberPicker(NumberPicker numberPicker) {
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(3);
        numberPicker.setDisplayedValues(decimalArray);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Log.e("HJLEE", picker.getValue());
                //(feed.calculationCalories * 0.01) * mear_history.calorie
            }
        });
    }
    /**
     * 단위를 입력하기 위한 피커
     * 지금은 사용안함.
     * @param numberPicker
     */
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
                Log.e("HJLEE", "StringNumber : " + picker.getValue());
            }
        });
    }

    @Override
    protected BaseActivity<MealRegistrationActivity> getActivityClass() {
        return MealRegistrationActivity.this;
    }

    /**
     * 사용안함.
     * @param feed
     */
    @Override
    public void setFeedInfo(Feed feed) {
        binding.setDomain(feed);
        setNumberPicker(binding.jungsu);
        setDecimalNumberPicker(binding.umsu);
        setStringNumberPicker(binding.unit, feed);

        dialog = IngredientsOfMealDialog.newInstance(feed);

    }

    /**
     * 사료등록결과
     * DB(SQLight)에 입력한다. (최근검색어를 위해)
     *
     * @param result
     */
    @Override
    public void setInsertResult(Integer result) {
        if (result == 1)
            dbHandler.insertFeed(new SearchHistory(binding.getDomain().getName(), mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), binding.getDomain().getId(), DateUtil.getCurrentDatetime()));
        finish();
    }

    /**
     * RER 을 보여준다.
     */
    public void setPetAllInfo() {
        rer = new RER(Float.parseFloat(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT)), selectPet.getFactor()).getRER();
        binding.rer.setText(MathUtil.DecimalCut(rer) + "kcal\n(" + getResources().getString(R.string.recommend) + ")");
    }


    /**
     * 오늘 섭취한 총 칼로리
     * @param mealHistory
     */
    @Override
    public void setTodaySumCalorie(MealHistory mealHistory) {
        this.mealHistory = mealHistory;
        if (mealHistory != null) {
            if (rer > mealHistory.getCalorie()) {
                binding.seekBar.setMax((int) rer);
                binding.rer.setText(MathUtil.DecimalCut(rer) + "kcal" + "\n(" + getResources().getString(R.string.recommend) + ")");
                binding.rer2.setText("/" + MathUtil.DecimalCut(rer) + "kcal");
                //initDataToSeekbar(rer);
            } else {
                binding.seekBar.setMax((int) mealHistory.getCalorie());
                binding.rer.setText(MathUtil.DecimalCut(rer) + "kcal" + "\n(" + getResources().getString(R.string.recommend) + ")");
                binding.rer2.setText("/" + MathUtil.DecimalCut(rer) + "kcal");
                //initDataToSeekbar(rer, mealHistory.getCalorie());
            }
            ObjectAnimator.ofInt(binding.seekBar, "progress", (int) mealHistory.getCalorie())
                    .setDuration(300)
                    .start();
            binding.calorieIntake.setText(MathUtil.DecimalCut(mealHistory.getCalorie()));
            calorieInit = mealHistory.getCalorie();
        } else {
            binding.seekBar.setMax((int) rer);
            binding.rer.setText(MathUtil.DecimalCut(rer) + "kcal" + "\n(" + getResources().getString(R.string.recommend) + ")");
            binding.rer2.setText("/" + MathUtil.DecimalCut(rer) + "kcal");
            //initDataToSeekbar(rer);
            binding.seekBar.setProgress(0);
            binding.calorieIntake.setText("0");
            calorieInit = 0;
        }

        binding.seekBar.setEnabled(true);
    }


    /**
     * 1. 사료의 영양분을 볼 수 있는 버튼 클릭
     * 2. 디테일 정보 다이얼로그 출력
     * @param view
     */
    public void onClickDetailBtn(View view) {
        dialog.show(getFragmentManager(), "dialog");
    }

    /*private void visualOfDinamicGraph(int value) {
        ObjectAnimator.ofInt(binding.seekBar, "progress", (int) mealHistory.getCalorie(), (int) mealHistory.getCalorie() + value)
                .setDuration(300)
                .start();
    }*/

    /**
     * meterageCup 을 설정한 후 Save 버튼 클릭 시 호출
     * @param view
     */
    public void onClickSaveBtn(View view) {
        StringBuilder AmountOfFeed = new StringBuilder();
        AmountOfFeed.append(binding.meterageCup.getValue());
        AmountOfFeed.append(decimalArray[binding.umsu.getValue()]);

        MealHistory mealHistory = MealHistory.builder()
                .calorie(Float.parseFloat(AmountOfFeed.toString()))
                .unitIndex(binding.unit.getValue())
                .unitString(unitArray[0])
                .feedIdx(feedId)
                .petIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX))
                .groupId(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE))
                .userIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID))
                .build();
        presenter.saveMeal(mealHistory);
    }

    /**
     * 변화 되는 부분 Refrash
     */
    @Override
    protected void onResume() {
        super.onResume();
        presenter.getTodaySumCalorie(DateUtil.getCurrentDatetime());
//        binding.meterageCup.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.mainRed), 80); //컬러 지정시 사용
//        binding.meterageCup.setMeterageNumber(new int[] {
//                0, 10, 20, 30, 40, 50
//        });
        binding.meterageCup.setCallback(new MeterageCup.TouchCallback() {
            @Override
            public void onResult(int value) {
                float changeCalorie = binding.getDomain().getCalculationCalories() * value / 100;
                float result;

                resultCalorie = changeCalorie + calorieInit;
                resultCalorie = changeCalorie + calorieInit;
                if ( resultCalorie == calorieInit )
                    result = calorieInit;
                else
                    result = resultCalorie;
                binding.calorieIntake.setText( MathUtil.DecimalCut( result ) );
                binding.seekBar.setProgress((int) result);
            }
        });
    }
}
