package com.animal.scale.hodoo.activity.user.signup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivitySignUpFinishBinding;

public class SignUpFinishActivity extends BaseActivity<SignUpFinishActivity> {
    private ActivitySignUpFinishBinding binding;
    private String userEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_finish);
        binding.setActivity(this);
    }

    @Override
    protected BaseActivity<SignUpFinishActivity> getActivityClass() {
        return this;
    }

    public void goLogin(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userEmail = getIntent().getStringExtra(SharedPrefVariable.USER_EMAIL);
    }
}
