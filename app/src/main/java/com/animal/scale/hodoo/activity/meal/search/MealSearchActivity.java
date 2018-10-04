package com.animal.scale.hodoo.activity.meal.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.list.FeedListActivity;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationActivity;
import com.animal.scale.hodoo.activity.meal.test.MealTestActivity;
import com.animal.scale.hodoo.activity.user.agree.TermsOfServiceActivity;
import com.animal.scale.hodoo.adapter.AdapterOfFeed;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityMealSearchBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

import java.util.ArrayList;
import java.util.List;

public class MealSearchActivity extends BaseActivity<MealSearchActivity> implements MealSearchIn.View, TextWatcher {

    ActivityMealSearchBinding binding;

    MealSearchIn.Presenter presenter;
    AdapterOfFeed adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meal_search);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_title)));
        presenter = new MealSearchPresenter(this);
        presenter.loadData(MealSearchActivity.this);
        presenter.getAllFeed();
    }

    @Override
    protected BaseActivity<MealSearchActivity> getActivityClass() {
        return MealSearchActivity.this;
    }

    @Override
    public void setProgress(boolean b) {

    }

    @Override
    public void setFeedList(List<AutoCompleateFeed> d) {
        ArrayList<AutoCompleateFeed> list = new ArrayList<AutoCompleateFeed>();
        adapter = new  AdapterOfFeed(getApplicationContext(), list);
        binding.feedSearch.addTextChangedListener(new CustomAutoCompleateTextChageListner(this));
        binding.feedListview.setAdapter(adapter);
        binding.feedListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AutoCompleateFeed feed = (AutoCompleateFeed) adapterView.getItemAtPosition(i);
                Log.e("HJLEE", feed.toString());
            }
        });
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

    public void onClickConfirmBtn(View view){
        Intent intent = new Intent(getApplicationContext(), MealTestActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }
}
