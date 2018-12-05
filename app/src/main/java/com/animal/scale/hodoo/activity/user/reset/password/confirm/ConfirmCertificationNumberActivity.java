package com.animal.scale.hodoo.activity.user.reset.password.confirm;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.reset.password.create.CreateNewPasswordActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityConfirmCertificationNumberBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class ConfirmCertificationNumberActivity extends BaseActivity<ConfirmCertificationNumberActivity> {

    ActivityConfirmCertificationNumberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_confirm_certification_number);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm_certification_number);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_find_password)));
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<ConfirmCertificationNumberActivity> getActivityClass() {
        return ConfirmCertificationNumberActivity.this;
    }

    public void onClickConfirmBtn(View vIew){
       Intent intent = new Intent(getApplicationContext(), CreateNewPasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }
}
