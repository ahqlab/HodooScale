package com.animal.scale.hodoo.activity.user.reset.password.create;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityCreateNewPasswordBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class CreateNewPasswordActivity extends BaseActivity<CreateNewPasswordActivity> {

    ActivityCreateNewPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_password);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_find_password)));
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<CreateNewPasswordActivity> getActivityClass() {
        return CreateNewPasswordActivity.this;
    }


    public void onClickCompleateBtn(View view){

    }
}
