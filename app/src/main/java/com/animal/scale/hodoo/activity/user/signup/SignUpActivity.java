package com.animal.scale.hodoo.activity.user.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.activity.wifi.WifiSearchActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySignUpBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.MyOwnBindingUtil;
import com.animal.scale.hodoo.util.ValidationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity<SignUpActivity> implements SignUpIn.View{

    ActivitySignUpBinding binding;

    boolean isEmailVailed = false;

    SignUpIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        super.setToolbarColor();
        presenter = new SignUpPresenter(this);
        presenter.loadData(SignUpActivity.this);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.signup_title)));
        binding.setUser(new User());
     /*   binding.setErrorMsg(getString(R.string.vailed_email));
        binding.setEmailRule(new MyOwnBindingUtil.StringRule() {
            @Override
            public boolean validate(Editable s) {
                if (!ValidationUtil.isValidEmail(s.toString())) {
                    isEmailVailed = false;
                    return false;
                } else {
                    isEmailVailed = true;
                    return true;
                }
            }
        });*/
    }

    @Override
    protected BaseActivity<SignUpActivity> getActivityClass() {
        return SignUpActivity.this;
    }

    //ESP31
    public void onClickSubmitBtn(View view) {
        if(ValidationUtil.isEmpty(binding.email)){
            super.showBasicOneBtnPopup(null,  getString(R.string.istyle_enter_the_email))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if (!ValidationUtil.isValidEmail(binding.email.getText().toString())) {
            //이메일 형식에 어긋납니다.
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_not_valid_email_format))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        } else if (ValidationUtil.isEmpty(binding.password)) {
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_the_password))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        } else if (ValidationUtil.isEmpty(binding.passwordCheck)) {
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_your_confirmation_password))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (!binding.passwordCheck.getText().toString().matches(binding.password.getText().toString())) {
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_password_is_incorrect))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmpty(binding.nickName)) {
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_your_nik_name))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (!binding.radioFemale.isChecked() && !binding.radioMale.isChecked()) {
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_select_gender))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmpty(binding.from)) {
            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_your_place_of_residence))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            if(binding.radioFemale.isChecked()){
                binding.getUser().setSex("FEMALE");
            }else if(binding.radioMale.isChecked()){
                binding.getUser().setSex("MALE");
            }
            presenter.registUser(binding.getUser());
        }
    }

   /* public void sendServer() {
        Call<ResultMessageGroup> result = NetRetrofit.getInstance().getUserService().registUser(binding.getUser());
        result.enqueue(new Callback<ResultMessageGroup>() {
            @Override
            public void onResponse(Call<ResultMessageGroup> call, Response<ResultMessageGroup> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if((int) Double.parseDouble(response.body().getDomain().toString()) == 1){
                            goNextPage();
                        }else{
                            Toast.makeText(getApplicationContext(), getString(R.string.user_regist_failed_message), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.user_regist_failed_message), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResultMessageGroup> call, Throwable t) {
            }
        });
    }*/

    @Override
    public void goNextPage() {
        Intent intent = new Intent(getApplicationContext(), WifiSearchActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void registUserResult(ResultMessageGroup resultMessageGroup) {

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
}
