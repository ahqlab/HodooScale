package com.animal.scale.hodoo.activity.wifi;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityFindHodoosBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class FindHodoosActivity extends BaseActivity<FindHodoosActivity> {

    ProgressBar mprogressBar;

    ActivityFindHodoosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_hodoos);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.find_hodoo_title)));
        binding.circleView.spin();
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<FindHodoosActivity> getActivityClass() {
        return FindHodoosActivity.this;
    }
}
