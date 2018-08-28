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
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivitySignUpBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.util.MyOwnBindingUtil;
import com.animal.scale.hodoo.util.ValidationUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends BaseActivity<SignUpActivity> {

    ActivitySignUpBinding binding;
    boolean isEmailVailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        super.setToolbarColor();
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.signup_title)));
        binding.setUser(new User());
        binding.setErrorMsg(getString(R.string.vailed_email));
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
        });
    }

    @Override
    protected BaseActivity<SignUpActivity> getActivityClass() {
        return SignUpActivity.this;
    }

    //ESP31
    public void onClickSubmitBtn(View view) {
        Log.e("HJLEE", "0");
        if(ValidationUtil.isEmpty(binding.email)){
            //이메일 형식에 어긋납니다.
            super.showBasicOneBtnPopup(getString(R.string.message), "이메일을 입력해주세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if (!isEmailVailed) {
            Log.e("HJLEE", "1");
            //이메일 형식에 어긋납니다.
            super.showBasicOneBtnPopup(getString(R.string.message), "이메일 형식에 어긋납니다.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        } else if (ValidationUtil.isEmpty(binding.password)) {
            Log.e("HJLEE", "2");
            super.showBasicOneBtnPopup(getString(R.string.message), "비밀번호입력")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
            return;
        } else if (ValidationUtil.isEmpty(binding.passwordCheck)) {
            Log.e("HJLEE", "3");
            super.showBasicOneBtnPopup(getString(R.string.message), "비밀번호 확인을 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (!binding.passwordCheck.getText().toString().matches(binding.password.getText().toString())) {
            Log.e("HJLEE", "4");
            super.showBasicOneBtnPopup(getString(R.string.message), "비밀번호가 맞지않습니다.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmpty(binding.nickName)) {
            Log.e("HJLEE", "5");
            super.showBasicOneBtnPopup(getString(R.string.message), "NICK NAME 을 입력하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (!binding.radioFemale.isChecked() && !binding.radioMale.isChecked()) {
            Log.e("HJLEE", "6");
            super.showBasicOneBtnPopup(getString(R.string.message), "성별을 선택하세요.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (ValidationUtil.isEmpty(binding.from)) {
            Log.e("HJLEE", "7");
            super.showBasicOneBtnPopup(getString(R.string.message), "거주지를 입력하세요.")
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
            sendServer();
        }
    }

    public void sendServer() {
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
    }

    public void goNextPage() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

}
