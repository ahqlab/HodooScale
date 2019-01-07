package com.animal.scale.hodoo.activity.user.reset.password.send;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivitySendCertificationNumberBinding;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.util.ValidationUtil;

public class SendCertificationNumberActivity extends BaseActivity<SendCertificationNumberActivity> implements SendCertificationNumberIn.View{

    ActivitySendCertificationNumberBinding binding;

    SendCertificationNumberIn.Presenter presenter;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_send_certification_number);
        binding.setActivity(this);
//        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_find_password)));
        super.setToolbarColor();
        sharedPrefManager =  SharedPrefManager.getInstance(this);
        presenter = new SendCertificationNumberPresenter(this);
        presenter.loadData(SendCertificationNumberActivity.this);
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
        String email = binding.email.editText.getText().toString();
        String password = sharedPrefManager.getStringExtra(SharedPrefVariable.USER_PASSWORD);
        User user = new User();
        user.setUserIdx(sharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID));
        user.setEmail(email);
        user.setPassword(password);
        presenter.sendTempPassword(user);
        /*Intent intent = new Intent(getApplicationContext(), ConfirmCertificationNumberActivity.class);
        intent.putExtra("email", binding.email.editText.getText().toString());
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);*/
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

    @Override
    public void sendResult(ResultMessageGroup userCommonResponce) {
        Log.e("HJLEE", "userCommonResponce : " + userCommonResponce);
    }

    @Override
    public void setProgress(Boolean play) {
        if (play) {
            binding.loginProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loginProgress.setVisibility(View.GONE);
        }
    }
}
