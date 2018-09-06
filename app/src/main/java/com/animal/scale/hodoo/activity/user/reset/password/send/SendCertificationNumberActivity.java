package com.animal.scale.hodoo.activity.user.reset.password.send;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySendCertificationNumberBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class SendCertificationNumberActivity extends BaseActivity<SendCertificationNumberActivity> {

    ActivitySendCertificationNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_certification_number);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("비밀번호찾기"));
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<SendCertificationNumberActivity> getActivityClass() {
        return SendCertificationNumberActivity.this;
    }
}
