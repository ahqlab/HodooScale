package com.animal.scale.hodoo.activity.meal.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationActivity;
import com.animal.scale.hodoo.activity.meal.search.MealSearchActivity;
import com.animal.scale.hodoo.activity.meal.update.MealUpdateActivity;
import com.animal.scale.hodoo.adapter.AdapterOfMealManager;
import com.animal.scale.hodoo.adapter.AdapterOfSearchFeed;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.seekbar.ProgressItem;
import com.animal.scale.hodoo.databinding.ActivityFeedListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealHistoryContent;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SearchHistory;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.MathUtil;
import com.animal.scale.hodoo.util.RER;

import java.util.ArrayList;
import java.util.List;

public class FeedListActivity extends BaseActivity<FeedListActivity> implements FeedListIn.View {

    SharedPrefManager mSharedPrefManager;

    ActivityFeedListBinding binding;

    FeedListIn.Presenter presenter;

    private ArrayList<ProgressItem> progressItemList;

    private ProgressItem mProgressItem;

    AdapterOfMealManager adapter;

    private float rer;

    private float calorie;

    private float darkGreySpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_list);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_feed)));

        mSharedPrefManager = SharedPrefManager.getInstance(FeedListActivity.this);

        super.setToolbarColor();
        presenter = new FeedListPresenter(this);
        presenter.loadData(FeedListActivity.this);
        //this.initSeekbar();
        presenter.getTodaySumCalorie(DateUtil.getCurrentDatetime());
    }

   /* public void initDataToSeekbar(float rer, float kcal) {
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
    }*/

    @Override
    public void initSeekbar() {
        //progressItemList = new ArrayList<ProgressItem>();
        //binding.seekBar.initData(progressItemList);
    }

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
            binding.seekBar.setProgress((int) mealHistory.getCalorie());
            binding.calorieIntake.setText(String.valueOf(mealHistory.getCalorie()));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setPetAllInfo(PetAllInfos petAllInfos) {
        rer = new RER(Float.parseFloat(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT)), petAllInfos.getFactor()).getRER();
        binding.seekBar.invalidate();
        presenter.getTodaySumCalorie(DateUtil.getCurrentDatetime());
    }



    @Override
    protected BaseActivity<FeedListActivity> getActivityClass() {
        return FeedListActivity.this;
    }

    @Override
    public void setProgress(boolean play) {
        if (play) {
            binding.loginProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loginProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setListView(List<MealHistoryContent> d) {
        adapter = new AdapterOfMealManager(this, d);
        binding.feedListview.setAdapter(adapter);
        binding.feedListview.setOnItemClickListener(onItemClickListener);
        binding.feedListview.setOnItemLongClickListener(onItemLongClickListener);
    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            deleteDlalog(adapter.getItem(i).getMealHistory().getHistoryIdx());
            return true;
        }
    };

    @Override
    public void deleteResult(Integer result) {
        if(result != 0){
            presenter.getPetAllInfo();
            presenter.getList(DateUtil.getCurrentDatetime());
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            MealHistoryContent item = (MealHistoryContent) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(getApplicationContext(), MealUpdateActivity.class);
            intent.putExtra("feedId", item.getFeed().getId());
            intent.putExtra("historyIdx", item.getMealHistory().getHistoryIdx());
            startActivity(intent);
        }
    };

    public void deleteDlalog(final int historyIdx) {
        final String[] values = new String[]{
                getResources().getString(R.string.delete)
        };
        super.showBasicOneBtnPopup(null, null)
                .setItems(values, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteMealHistory(historyIdx);
                        dialog.dismiss();
                    }
                }).show();
    }

    public void onClickFloatingBtn(View view) {
        Log.e(TAG, "onClickFloatingBtn");
        Intent intent = new Intent(getApplicationContext(), MealSearchActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        presenter.getPetAllInfo();
        presenter.getList(DateUtil.getCurrentDatetime());
        super.onResume();
    }
}
