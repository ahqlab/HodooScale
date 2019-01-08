package com.animal.scale.hodoo.activity.setting.account.change.password;

import android.content.DialogInterface;
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
import com.animal.scale.hodoo.databinding.ActivityChangePasswordBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordActivity> implements ChangePassword.View {

    public SharedPrefManager mSharedPrefManager;

    ActivityChangePasswordBinding binding;

    ChangePassword.Presenter presenter;

    boolean
            oldPasswordStatus = false,
            newPasswordStatus = false,
            confirmPasswordStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_change_password)));
        mSharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());
        super.setToolbarColor();

        presenter = new ChangePasswordPresenter(this);
        presenter.initLoadData(getApplicationContext());
        setBtnEnable(false);
        binding.oldPassword.editText.addTextChangedListener(new CommonTextWatcher(
                binding.oldPassword,
                this,
                CommonTextWatcher.OLD_PWCHECK_TYPE,
                R.string.not_mathed_old_password,
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        oldPasswordStatus = state;
                        Log.e(TAG, String.format("oldPassword : %b", oldPasswordStatus));
                        vaildation();
                    }
                }
        ));
        binding.newPassword.editText.addTextChangedListener(new CommonTextWatcher(
                binding.newPassword,
                binding.cofirmPassword,
                this,
                CommonTextWatcher.JOIN_PW_TYPE,
                new int[]{
                        R.string.istyle_enter_the_password,
                        R.string.istyle_password_is_incorrect
                },
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        newPasswordStatus = state;
                        Log.e(TAG, String.format("newPassword : %b", newPasswordStatus));
                        vaildation();
                    }
                }
        ));
        binding.cofirmPassword.editText.addTextChangedListener(new CommonTextWatcher(
                binding.newPassword,
                binding.cofirmPassword,
                this,
                CommonTextWatcher.PWCHECK_TYPE,
                new int[]{
                        R.string.istyle_enter_the_password,
                        R.string.istyle_password_is_incorrect
                },
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        confirmPasswordStatus = state;
                        Log.e(TAG, String.format("cofirmPassword : %b", confirmPasswordStatus));
                        vaildation();
                    }
                }
        ));
    }

    private void setBtnEnable(boolean state) {
        binding.submit.setEnabled(state);
        if (binding.submit.isEnabled()) {
            binding.submit.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.submit.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }

    private void vaildation() {
        if (oldPasswordStatus && newPasswordStatus && confirmPasswordStatus) {
            setBtnEnable(true);
        } else {
            setBtnEnable(false);
        }
    }

    @Override
    protected BaseActivity<ChangePasswordActivity> getActivityClass() {
        return ChangePasswordActivity.this;
    }

    //onCLick
    public void onClickSubmitBtn(View view) {
        User user = new User();
        user.setPassword(binding.newPassword.editText.getText().toString());
        user.setUserIdx(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID));
        presenter.changeUserPassword(user);
    }

    @Override
    public void userPasswordUpdateResult(Integer integer) {
        if (integer == 1) {
            super.showBasicOneBtnPopup(null, getString(R.string.password_edit_success))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mSharedPrefManager.putStringExtra(SharedPrefVariable.USER_PASSWORD, binding.newPassword.editText.getText().toString());
                            finish();
                        }
                    }
                ).show();
        }
    }
}
