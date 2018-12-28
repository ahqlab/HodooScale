package com.animal.scale.hodoo.activity.user.reset.password.send;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.reset.password.confirm.ConfirmCertificationNumberActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivitySendCertificationNumberBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.ValidationUtil;

public class SendCertificationNumberActivity extends BaseActivity<SendCertificationNumberActivity> {

    ActivitySendCertificationNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_certification_number);
        binding.setActivity(this);
//        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_find_password)));
        super.setToolbarColor();
        binding.email.editText.addTextChangedListener(new CommonTextWatcher(binding.email, this, CommonTextWatcher.EMAIL_TYPE, R.string.vailed_email, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                checkState();
            }
        }));
    }

    @Override
    protected BaseActivity<SendCertificationNumberActivity> getActivityClass() {
        return SendCertificationNumberActivity.this;
    }

    public void onClickSendBtn(View view){
        Intent intent = new Intent(getApplicationContext(), ConfirmCertificationNumberActivity.class);
        intent.putExtra("email", binding.email.editText.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
    private void checkState () {
        if ( !ValidationUtil.isValidEmail(binding.email.editText.getText().toString())) {
            setBtnEnable(false);
        } else {
            setBtnEnable(true);
        }
    }
    private void setBtnEnable ( boolean state ) {
        binding.sendBtn.setEnabled(state);
        if ( binding.sendBtn.isEnabled() ) {
            binding.sendBtn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.sendBtn.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkState();
    }
}
