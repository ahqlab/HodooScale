package com.animal.scale.hodoo.activity.meal.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationActivity;
import com.animal.scale.hodoo.adapter.AdapterOfSearchFeed;
import com.animal.scale.hodoo.adapter.AdapterOfSearchHistory;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityMealSearchBinding;
import com.animal.scale.hodoo.db.DBHandler;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SearchHistory;
import com.animal.scale.hodoo.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class MealSearchActivity extends BaseActivity<MealSearchActivity> implements MealSearchIn.View, TextWatcher {

    ActivityMealSearchBinding binding;

    MealSearchIn.Presenter presenter;

    private float rer;

    private float calorie;

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_search);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_title)));
        dbHandler = new DBHandler(this);
        presenter = new MealSearchPresenter(this);
        presenter.loadData(MealSearchActivity.this);
        Intent intent = getIntent();
        binding.feedSearch.addTextChangedListener(new CustomAutoCompleateTextChageListner(this));
        binding.feedListview.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), MealRegistrationActivity.class);
            if(adapterView.getItemAtPosition(i) instanceof SearchHistory){
                SearchHistory feed = (SearchHistory) adapterView.getItemAtPosition(i);
                intent.putExtra("feedId", feed.getFeedIdx());
            }else if(adapterView.getItemAtPosition(i) instanceof AutoCompleateFeed){
                AutoCompleateFeed feed = (AutoCompleateFeed) adapterView.getItemAtPosition(i);
                intent.putExtra("feedId", feed.getId());
            }
            startActivity(intent);
            overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
            finish();
        }
    };

    @Override
    protected BaseActivity<MealSearchActivity> getActivityClass() {
        return MealSearchActivity.this;
    }

    @Override
    public void setProgress(boolean b) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void onClickConfirmBtn(View view) {
        /*Intent intent = new Intent(getApplicationContext(), MealTestActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();*/
    }

    public void onListBtnClick(SearchHistory feed) {
        dbHandler.delete(feed.getId());
    }
}
