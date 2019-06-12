package com.animal.scale.hodoo.activity.wifi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySearchFailedBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

/**
 * 삭제
 */
public class SearchFailedActivity extends BaseActivity<SearchFailedActivity> {

    ActivitySearchFailedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_failed);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.find_hodoo_title)));
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<SearchFailedActivity> getActivityClass() {
        return SearchFailedActivity.this;
    }
}
