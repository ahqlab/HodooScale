package com.animal.scale.hodoo.activity.user.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityLoginBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.activity.home.HomeActivity;
import com.animal.scale.hodoo.activity.pet.regist.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.user.signup.TermsOfServiceActivity;
import com.animal.scale.hodoo.util.MyOwnBindingUtil;
import com.animal.scale.hodoo.util.ValidationUtil;

public class LoginActivity extends BaseActivity<LoginActivity> implements Login.View {

    ActivityLoginBinding binding;

    Login.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("LOGIN"));
        binding.setUser(new User(mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_ID)));
        binding.setErrorMsg(getString(R.string.vailed_email));
        binding.setEmailRule(new MyOwnBindingUtil.StringRule() {
            @Override
            public boolean validate(Editable s) {
                if (!ValidationUtil.isValidEmail(s.toString())) {
                    return false;
                } else {
                    return true;
                }
            }
        });
        presenter = new LoginPresenter(this);
        presenter.initUserData(binding.getUser(), getApplicationContext());
    }

    @Override
    protected BaseActivity<LoginActivity> getActivityClass() {
        return LoginActivity.this;
    }

    //onClick
    public void onClickLoginBtn(View view) {
        presenter.userValidationCheck(binding.getUser());
    }

    //onClick
    public void onClickCreateAccountBtn(View view) {
        goTermsOfServiceActivity();
    }

    @Override
    public void showPopup(String message) {
        super.showBasicOneBtnPopup(getString(R.string.message), message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                ).show();
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
    public void goRegistActivity() {
        Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void goTermsOfServiceActivity() {
        Intent intent = new Intent(getApplicationContext(), TermsOfServiceActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }
}