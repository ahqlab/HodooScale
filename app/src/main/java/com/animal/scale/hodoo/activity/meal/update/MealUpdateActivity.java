package com.animal.scale.hodoo.activity.meal.update;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.dialog.IngredientsOfMealDialog;
import com.animal.scale.hodoo.custom.view.MeterageCup;
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
import com.animal.scale.hodoo.util.MathUtil;
import com.animal.scale.hodoo.util.RER;

import java.util.ArrayList;

public class MealUpdateActivity extends BaseActivity<MealUpdateActivity> implements MealUpdateIn.View {

    ActivityMealUpdateBinding binding;

    MealUpdateIn.Presenter presenter;

    private ArrayList<ProgressItem> progressItemList;

    private ProgressItem mProgressItem;

    private String[] decimalArray = {".0", ".25", ".5", ".75"};

    private String[] unitArray;

    private String[] doubleUnitArray;

    private String[] singleUnitArray;

    private int feedId;

    private int historyIdx;

    private MealHistoryContent mealHistoryContent;

    private float darkGreySpan;

    private float rer;

    private DBHandler dbHandler;

    IngredientsOfMealDialog dialog;

    private PetAllInfos selectPet;

    private float resultCalorie = 0;
    private float calorieInit = 0;
    private float calorieVal = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        feedId = intent.getIntExtra("feedId", 0);
        historyIdx = intent.getIntExtra("historyIdx", 0);
        selectPet = (PetAllInfos) intent.getSerializableExtra("selectPet");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_update);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.food)));
        super.setToolbarColor();

        doubleUnitArray = new String[]{"g", this.getResources().getString(R.string.cup)};
        singleUnitArray = new String[]{this.getResources().getString(R.string.ea)};

        presenter = new MealUpdatePresenter(this);
        presenter.loadData(this);
        setPetAllInfo();
        //presenter.getPetAllInfo();
        presenter.getFeedInfo(feedId);
        presenter.getThisHistory(historyIdx);

        dbHandler = new DBHandler(this);
        progressItemList = new ArrayList<ProgressItem>();
        //binding.seekBar.initData(progressItemList);
    }

    /**
     * Intent로 받은 SelectPet 에 대한 RER을 구한다.
     */
    private void setPetAllInfo() {
        rer = new RER(Float.parseFloat(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT)), selectPet.getFactor()).getRER();
        binding.rer.setText(MathUtil.DecimalCut(rer) + "kcal\n(" + getResources().getString(R.string.recommend) + ")");
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
        numberPicker.setValue(3);
        numberPicker.setDisplayedValues(decimalArray);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });
    }
    /**
     * 단위를 입력하기 위한 피커 셋팅
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
            }
        });
    }

    @Override
    protected BaseActivity<MealUpdateActivity> getActivityClass() {
        return MealUpdateActivity.this;
    }

    /**
     * 사용안함.
     * @param rer
     * @param kcal
     */
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
    /**
     * 사용안함.
     * @param rer
     * @param kcal
     */
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
        if (result == 1) {
            dbHandler.insertFeed(new SearchHistory(binding.getDomain().getName(), mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), binding.getDomain().getId(), DateUtil.getCurrentDatetime()));
            finish();
        }
    }

    /**
     * 오늘 섭취한 총 칼로리
     * @param mealHistory
     */
    @Override
    public void setTodaySumCalorie(MealHistory mealHistory) {
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
            float cal = binding.getDomain().getCalculationCalories()  * calorieVal / 100;
            calorieInit = calorieInit - cal;
        } else {
            binding.seekBar.setMax((int) rer);
            binding.rer.setText(MathUtil.DecimalCut(rer) + "kcal" + "\n(" + getResources().getString(R.string.recommend) + ")");
            binding.rer2.setText("/" + MathUtil.DecimalCut(rer) + "kcal");
            //initDataToSeekbar(rer);
            binding.seekBar.setProgress(0);
            binding.calorieIntake.setText("0");
        }

        binding.seekBar.setEnabled(true);
    }

    /**
     * 컵과  MealHistory 객체에 값을 셋팅한다.
     * @param mealHistory
     */
    @Override
    public void setThisHistory(MealHistory mealHistory) {
        binding.setHistory(mealHistory);
        String[] array = String.valueOf(mealHistory.getCalorie()).split("\\.");
        binding.meterageCup.setValue( extractIntegerFromFloat(mealHistory.getCalorie()) );
        calorieVal = mealHistory.getCalorie();


        binding.jungsu.setValue(extractIntegerFromFloat(mealHistory.getCalorie()));
        int nagativeValue = findDecimalArrayIndexFromNumberPicker(decimalArray, extractNegativeNumberFromFloat(mealHistory.getCalorie()));
        if (nagativeValue != -1) {
            binding.umsu.setValue(nagativeValue);
        } else {
            binding.umsu.setValue(0);
        }
        if (binding.getDomain().getTag().equals("D")) {
            binding.unit.setValue(mealHistory.getUnitIndex());
        } else if (binding.getDomain().getTag().equals("S")) {
            binding.unit.setValue(0);
        }
    }

    /**
     * 업데이트 결과
     * 1이면 성공 종료한다.
     * @param result
     */
    @Override
    public void setUpdateResult(int result) {
        if (result == 1) {
            finish();
        }
    }

    /**
     * 디테일 버튼 클릭
     * @param view
     */
    public void onClickDetailBtn(View view) {
        dialog.show(getFragmentManager(), "dialog");
    }

    /**
     * 업데이트 버튼 클릭
     * @param view
     */
    public void onClickSaveBtn(View view) {
        StringBuilder AmountOfFeed = new StringBuilder();
        AmountOfFeed.append(binding.meterageCup.getValue());
        AmountOfFeed.append(decimalArray[binding.umsu.getValue()]);

        MealHistory mealHistory = MealHistory.builder()
                .historyIdx(binding.getHistory().getHistoryIdx())
                .calorie(Float.parseFloat(AmountOfFeed.toString()))
                .unitIndex(binding.unit.getValue())
                .unitString(unitArray[0])
                .feedIdx(feedId)
                .petIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX))
                .groupId(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE))
                .userIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID))
                .build();
        presenter.updateMeal(mealHistory);
    }

    public int findDecimalArrayIndexFromNumberPicker(String arr[], String s) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].matches("." + s))
                return i;

        return -1;
    }

    public String extractNegativeNumberFromFloat(float calorie) {
        String[] array = String.valueOf(calorie).split("\\.");
        if (array.length > 0) {
            return array[array.length - 1];
        }
        return null;
    }

    public int extractIntegerFromFloat(float calorie) {
        String[] array = String.valueOf(calorie).split("\\.");
        if (array.length > 0) {
            return Integer.parseInt(array[0]);
        }
        return Integer.parseInt(String.valueOf(calorie));
    }

    /**
     * 변경 항목 리프레쉬
     */
    @Override
    protected void onResume() {
        presenter.getTodaySumCalorie(DateUtil.getCurrentDatetime());
        super.onResume();
        binding.meterageCup.setCallback(new MeterageCup.TouchCallback() {
            @Override
            public void onResult(int value) {
                float changeCalorie = binding.getDomain().getCalculationCalories() * value / 100;
                resultCalorie = changeCalorie + calorieInit;
                float result = 0;
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
