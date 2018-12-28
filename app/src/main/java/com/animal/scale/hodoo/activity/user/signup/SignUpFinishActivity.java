package com.animal.scale.hodoo.activity.user.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.base.BaseActivity;

public class SignUpFinishActivity extends BaseActivity<SignUpFinishActivity> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_finish);
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
}
