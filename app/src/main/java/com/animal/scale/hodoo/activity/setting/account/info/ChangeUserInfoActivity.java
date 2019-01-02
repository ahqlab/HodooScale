package com.animal.scale.hodoo.activity.setting.account.info;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.change.password.ChangePasswordActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivityChangeUserInfoBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;

public class ChangeUserInfoActivity extends BaseActivity<ChangeUserInfoActivity> implements ChangeUserInfoIn.View {

    ActivityChangeUserInfoBinding binding;

    ChangeUserInfoIn.Presenter presenter;

    boolean
            ninkState = false,
            countryState = false;


    private String[] country;
    private int selectCountry = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_user_info);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.change_user_info_title)));
        super.setToolbarColor();
        presenter = new ChangeUserInfoPresenter(this);

        binding.password.editText.setClickable(true);
        binding.password.editText.setCursorVisible(false);
        binding.password.editText.setFocusable(false);
        binding.password.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPasswordEditTextClick(view);
            }
        });
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

        binding.country.editText.addTextChangedListener(new CommonTextWatcher(
                binding.country,
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
        binding.country.editText.setFocusable(false);
        binding.country.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCountryEditTextClick(view);
            }
        });
        country = getResources().getStringArray(R.array.country);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initLoadData(getApplicationContext());
        presenter.initUserData();
    }

    private void onClickPasswordEditTextClick(View view) {
        super.showBasicOneBtnPopup(null, getString(R.string.question_of_change_the_password))
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }

    private void onClickCountryEditTextClick(View view) {

        AlertDialog.Builder builder = super.showBasicOneBtnPopup(getResources().getString(R.string.choice_country), null);
        builder.setTitle(getResources().getString(R.string.choice_country));
        // add a radio button list
        builder.setItems(country, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.country.editText.setText(country[which]);
                dialog.dismiss();
                selectCountry = which;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    protected BaseActivity<ChangeUserInfoActivity> getActivityClass() {
        return ChangeUserInfoActivity.this;
    }

    public void onChangePasswordBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    public void onConfirmBtn(View view) {
        String nickName =  binding.nickName.editText.getText().toString();

        binding.getDomain().setNickname(nickName);
        binding.getDomain().setCountry(selectCountry);
        Log.e("HJLEE", binding.getDomain().toString());
        presenter.updateBasicInfo(binding.getDomain());
        /*Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();*/
    }

    private void setBtnEnable(boolean state) {
        binding.confirm.setEnabled(state);
        if (binding.confirm.isEnabled()) {
            binding.confirm.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.confirm.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }

    private void vaildation() {
        if (ninkState && countryState) {
            setBtnEnable(true);
        } else {
            setBtnEnable(false);
        }
    }

    @Override
    public void setUserInfo(User user) {
        binding.password.editText.setText(user.getPassword());
        binding.nickName.editText.setText(user.getNickname());
        binding.country.editText.setText(country[user.getCountry()]);
        binding.setDomain(user);
    }

    @Override
    public void showResultPopup(Integer integer) {
        if (integer == 1) {
            super.showBasicOneBtnPopup(null, getString(R.string.edit_success))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }
    }
}
