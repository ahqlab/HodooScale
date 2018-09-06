package com.animal.scale.hodoo.activity.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityTestBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;

public class TestActivity extends BaseActivity<TestActivity> {

    ActivityTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("타이틀"));
        User user = new User();
        user.setNickname("하이");
        binding.setUser(user);
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<TestActivity> getActivityClass() {
        return TestActivity.this;
    }
}
