package com.animal.scale.hodoo.activity.user.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.device.regist.DeviceRegistActivity;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.disease.DiseaseInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.physique.PhysiqueInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.weight.WeightCheckActivity;
import com.animal.scale.hodoo.activity.user.agree.TermsOfServiceActivity;
import com.animal.scale.hodoo.activity.user.reset.password.send.SendCertificationNumberActivity;
import com.animal.scale.hodoo.activity.user.signup.SignUpFinishActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivityLoginBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.util.VIewUtil;
import com.animal.scale.hodoo.util.ValidationUtil;

public class LoginActivity extends BaseActivity<LoginActivity> implements Login.View {

    ActivityLoginBinding binding;

    Login.Presenter presenter;
    private boolean emailState = false;
    private boolean pwState = false;
    private boolean autoLoginState = false;

    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_login_title)));

        binding.setUser(new User(mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_EMAIL)));
        super.setToolbarColor();
        presenter = new LoginPresenter(this);
        presenter.initUserData(binding.getUser(), getApplicationContext());

        String countryCode = VIewUtil.getMyLocationCode(LoginActivity.this);
        mSharedPrefManager.putStringExtra(SharedPrefVariable.CURRENT_COUNTRY, countryCode);

        if ( getIntent().getIntExtra(SharedPrefVariable.AUTO_LOGIN, 0) > 0 ) {
            presenter.autoLogin();
        }

        User user = new User(mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_EMAIL));
        if(user != null){
            binding.email.editText.setText(user.getEmail());
        }
        if ( ValidationUtil.isValidEmail(binding.email.getText().toString()) ) {
            emailState = true;
            checkState();
        }

        binding.email.editText.addTextChangedListener(new CommonTextWatcher(binding.email, this, CommonTextWatcher.EMAIL_TYPE, R.string.vailed_email, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                if ( emailState != state )
                    emailState = state;
                checkState();
            }
        }));
        binding.password.editText.addTextChangedListener(new CommonTextWatcher(binding.password, this, CommonTextWatcher.EMPTY_TYPE, R.string.istyle_enter_the_password, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                if ( pwState != state )
                    pwState = state;
                checkState();
            }
        }));
        binding.autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean state) {
                autoLoginState = state;
            }
        });
        setBtnEnable(false);
    }

    @Override
    protected BaseActivity<LoginActivity> getActivityClass() {
        return LoginActivity.this;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK)){
            setProgress(false);
        }
        return super.onKeyDown(keyCode, event);
    }

    //onClick
    public void onClickLoginBtn(View view) {
        User user = new User();
        user.setEmail(binding.email.getText());
        user.setPassword(binding.password.getText());
        user.setPasswordCheck(binding.password.getText());

        if (ValidationUtil.isValidEmail(binding.email.getText().toString()) && !ValidationUtil.isEmpty(binding.password.getText())) {
            presenter.userValidationCheck(user);
        }
    }

    //onClick
    public void onClickCreateAccountBtn(View view) {
        goTermsOfServiceActivity();
    }

    @Override
    public void showPopup(String message) {
        super.showBasicOneBtnPopup(null, message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        ).show();
        setProgress(false);
    }

    @Override
    public void showPopup(String message, final LoginPresenter.OnDialogClickListener callback) {
        super.showBasicOneBtnPopup(null, message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callback.onPositiveClick(dialog, which);
                            }
                        }
                ).show();
        setProgress(false);
    }

    @Override
    public void setProgress(Boolean play) {
        if (play) {
            binding.loginProgress.setVisibility(View.VISIBLE);
        } else {
            binding.loginProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void goDeviceRegistActivity() {
        //token 등록
        mSharedPrefManager.putIntExtra(SharedPrefVariable.AUTO_LOGIN, 0);
        Intent intent = new Intent(getApplicationContext(), DeviceRegistActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goPetRegistActivity(int petIdx) {
        Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
        intent.putExtra("petIdx", petIdx);
        intent.putExtra("deleteAllPet", true);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goDiseasesRegistActivity(int petIdx) {
        Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
        intent.putExtra("petIdx", petIdx);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goPhysicalRegistActivity(int petIdx) {
        Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
        intent.putExtra("petIdx", petIdx);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goWeightRegistActivity(int petIdx) {
        Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
        intent.putExtra("petIdx", petIdx);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }


    @Override
    public void goTermsOfServiceActivity() {
        Intent intent = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void setAutoLoginState() {
        presenter.setAutoLogin( autoLoginState );
    }

    @Override
    public void setAutoLogin(boolean state) {
        binding.autoLogin.setChecked(state);
        autoLoginState = state;
    }

    @Override
    public void goEmailCertified() {
        Intent intent = new Intent(getApplicationContext(), SignUpFinishActivity.class);
        intent.putExtra(SharedPrefVariable.USER_EMAIL, binding.email.getText());
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }

    @Override
    public void setPassword(String pw) {
        binding.password.editText.setText(pw);
    }

    @Override
    public void setBtnState(boolean state) {
        if ( ValidationUtil.isValidEmail(binding.email.getText().toString()) ) {
            emailState = true;
        }
        if ( !ValidationUtil.isEmpty( binding.password.getText().toString() ) ) {
            pwState = true;
        }
        checkState();
    }

    @Override
    public void saveFcmToken() {
        User user = new User();
        user.setEmail( binding.email.getText().toString() );
        presenter.saveFCMToken(user);
    }

    public void onClickForgotPasswordBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), SendCertificationNumberActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }

    private void checkState () {
        setBtnEnable(emailState && pwState);
    }
    private void setBtnEnable ( boolean state ) {
        binding.signupBtn.setEnabled(state);
        if ( binding.signupBtn.isEnabled() ) {
            binding.signupBtn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.signupBtn.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }
}