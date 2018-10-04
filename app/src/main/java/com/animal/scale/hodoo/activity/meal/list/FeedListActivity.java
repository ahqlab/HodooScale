package com.animal.scale.hodoo.activity.meal.list;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.search.AutoCompleateFeed;
import com.animal.scale.hodoo.adapter.AdapterOfFeed;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityFeedListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Feed;

import java.util.List;

public class FeedListActivity extends BaseActivity<FeedListActivity> implements FeedListIn.View{

    ActivityFeedListBinding binding;

    FeedListIn.Presenter presenter;

    AdapterOfFeed adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed_list);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("사료"));
        presenter = new FeedListPresenter(this);
        presenter.loadData(FeedListActivity.this);
        presenter.getAllFeed();

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
    public void setFeedList(List<AutoCompleateFeed> d) {
        adapter = new  AdapterOfFeed(FeedListActivity.this, d);
        binding.feedListview.setAdapter(adapter);
    }
}
