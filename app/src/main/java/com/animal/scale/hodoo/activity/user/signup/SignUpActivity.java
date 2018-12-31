package com.animal.scale.hodoo.activity.user.signup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.activity.wifi.WifiSearchActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.custom.view.input.CustomCommonEditText;
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

public class SignUpActivity extends BaseActivity<SignUpActivity> implements SignUpIn.View {

    ActivitySignUpBinding binding;

    boolean isEmailVailed = false;

    SignUpIn.Presenter presenter;

    boolean radioCheckState = false,
            emailState = false,
            pwState = false,
            pwCheckState = false,
            ninkState = false,
            countryState = false,
            agreeState = false;

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

        binding.email.editText.addTextChangedListener(new CommonTextWatcher(binding.email, this, CommonTextWatcher.EMAIL_TYPE, R.string.vailed_email, new CommonTextWatcher.CommonTextWatcherCallback() {
            @Override
            public void onChangeState(boolean state) {
                emailState = state;
                Log.e(TAG, String.format("emailState : %b", emailState));
                vaildation();
            }
        }));
        binding.password.editText.addTextChangedListener(new CommonTextWatcher(
                binding.password,
                binding.passwordCheck,
                this,
                CommonTextWatcher.JOIN_PW_TYPE,
                new int[]{
                        R.string.istyle_enter_the_password,
                        R.string.istyle_password_is_incorrect
                },
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        pwState = state;
                        Log.e(TAG, String.format("pwState : %b", pwState));
                        vaildation();
                    }
                }
        ));
        binding.passwordCheck.editText.addTextChangedListener(new CommonTextWatcher(
                binding.passwordCheck,
                binding.password,
                this,
                CommonTextWatcher.PWCHECK_TYPE,
                new int[]{
                        R.string.istyle_enter_your_confirmation_password,
                        R.string.istyle_password_is_incorrect
                },
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        pwCheckState = state;
                        if ( pwCheckState )
                            pwState = true;
                        Log.e(TAG, String.format("pwState : %b", pwCheckState));
                        vaildation();
                    }
                }
        ));
        binding.nickName.editText.addTextChangedListener(new CommonTextWatcher(
                binding.nickName,
                this,
                CommonTextWatcher.EMPTY_TYPE,
                R.string.istyle_enter_your_nik_name,
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        ninkState = state;
                        Log.e(TAG, String.format("ninkState : %b", ninkState));
                        vaildation();
                    }
                }
        ));
        binding.from.editText.addTextChangedListener(new CommonTextWatcher(
                binding.from,
                this,
                CommonTextWatcher.EMPTY_TYPE,
                0,
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        countryState = state;
                        Log.e(TAG, String.format("countryState : %b", countryState));
                        vaildation();
                    }
                }
        ));
        binding.from.editText.setFocusable(false);
        binding.from.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCountryEditTextClick(view);
            }
        });
        binding.radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if ( !radioCheckState ) {
                    radioCheckState = true;
                    vaildation();
                }

            }
        });
        setBtnEnable(false);
        binding.singupAgreeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                agreeState = b;
                vaildation();
            }
        });
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
//        if (!ValidationUtil.isValidEmail(binding.email.getText().toString())) {
//            //이메일 형식에 어긋납니다.
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_not_valid_email_format))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//            return;
//        } else if (ValidationUtil.isEmpty(binding.password)) {
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_the_password))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//            return;
//        } else if (ValidationUtil.isEmpty(binding.passwordCheck)) {
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_your_confirmation_password))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//        } else if (!binding.passwordCheck.getText().toString().matches(binding.password.getText().toString())) {
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_password_is_incorrect))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//        } else if (ValidationUtil.isEmpty(binding.nickName)) {
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_your_nik_name))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//        } else if (!binding.radioFemale.isChecked() && !binding.radioMale.isChecked()) {
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_select_gender))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//        } else if (ValidationUtil.isEmpty(binding.from)) {
//            super.showBasicOneBtnPopup(null, getString(R.string.istyle_enter_your_place_of_residence))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).show();
//        } else {

        presenter.userCertifiedMailSend("songchic2@gmail.com");

//        }
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



        Intent intent = new Intent(getApplicationContext(), SignUpFinishActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void registUser() {
        User user = binding.getUser();
        user.setEmail( binding.email.editText.getText().toString() );
        user.setPassword( binding.password.editText.getText().toString() );
        user.setNickname( binding.nickName.editText.getText().toString() );
        user.setCountry( binding.from.editText.getText().toString() );
        if (binding.radioFemale.isChecked()) {
            binding.getUser().setSex("FEMALE");
        } else if (binding.radioMale.isChecked()) {
            binding.getUser().setSex("MALE");
        }
        presenter.registUser(binding.getUser());
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


    public void onClickCountryEditTextClick(View view) {
        final String[] values = new String[]{
                this.getString(R.string.korea),
                this.getString(R.string.usa),
                this.getString(R.string.japan),
                this.getString(R.string.china)
        };
        AlertDialog.Builder builder = super.showBasicOneBtnPopup(getResources().getString(R.string.choice_country), null);
        builder.setTitle(getResources().getString(R.string.choice_country));
        // add a radio button list
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.from.editText.setText(values[which]);
                dialog.dismiss();
            }
        });
        // add OK and Cancel buttons
       /* builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // user clicked OK
            }
        });
        builder.setNegativeButton("Cancel", null);*/
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private boolean checkValidation( int type ) {
        return true;
    }
    private void setBtnEnable ( boolean state ) {
        binding.confirm.setEnabled(state);
        if ( binding.confirm.isEnabled() ) {
            binding.confirm.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.confirm.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }
    private void vaildation() {
        if (
                emailState &&
                pwState &&
                pwCheckState &&
                ninkState &&
                countryState &&
                radioCheckState &&
                agreeState
                ) {
            setBtnEnable(true);
        } else {
            setBtnEnable(false);
        }
    }
}
