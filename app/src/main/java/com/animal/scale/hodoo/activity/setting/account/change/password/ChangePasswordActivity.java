package com.animal.scale.hodoo.activity.setting.account.change.password;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityChangePasswordBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordActivity> implements ChangePassword.View {


    ActivityChangePasswordBinding binding;

    ChangePassword.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_change_password)));
        super.setToolbarColor();
        presenter = new ChangePasswordPresenter(this);
    }

    @Override
    protected BaseActivity<ChangePasswordActivity> getActivityClass() {
        return ChangePasswordActivity.this;
    }

    //onCLick
    public void onClickSubmitBtn(View view) {

    }
}
