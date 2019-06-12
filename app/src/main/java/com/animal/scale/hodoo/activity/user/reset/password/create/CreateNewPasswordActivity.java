package com.animal.scale.hodoo.activity.user.reset.password.create;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityCreateNewPasswordBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

/**
 * 삭제
 */
public class CreateNewPasswordActivity extends BaseActivity<CreateNewPasswordActivity> {
    //사용안함 2019/01/07 이형준
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
