package com.animal.scale.hodoo.activity.user.reset.password.confirm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.activity.user.reset.password.create.CreateNewPasswordActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityConfirmCertificationNumberBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

/**
 * 비밀번호 임시 발송 안내 Activity
 */
public class ConfirmCertificationNumberActivity extends BaseActivity<ConfirmCertificationNumberActivity> {

    ActivityConfirmCertificationNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_confirm_certification_number);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_certification_number);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_find_password)));

        super.setToolbarColor();

        String email = getIntent().getStringExtra("email") + getString(R.string.confirm_certification_number__send_email_suffix);
        String[] split = email.split("@");

        SpannableStringBuilder ssb = new SpannableStringBuilder(email);
        ssb.setSpan(new ForegroundColorSpan(Color.parseColor("#bbbff8")), 0, split[0].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.successMsg.setText(ssb);

    }

    @Override
    protected BaseActivity<ConfirmCertificationNumberActivity> getActivityClass() {
        return ConfirmCertificationNumberActivity.this;
    }

    public void onClickConfirmBtn(View vIew) {
        finish();
    }
}
