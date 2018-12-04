package com.animal.scale.hodoo.activity.setting.account.info;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.change.password.ChangePasswordActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityChangeUserInfoBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;

public class ChangeUserInfoActivity extends BaseActivity<ChangeUserInfoActivity> implements ChangeUserInfoIn.View{

    ActivityChangeUserInfoBinding binding;

    ChangeUserInfoIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_user_info);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.change_user_info_title)));
        super.setToolbarColor();
        presenter = new ChangeUserInfoPresenter(this);
        presenter.initLoadData(getApplicationContext());
        presenter.initUserData();
    }

    @Override
    protected BaseActivity<ChangeUserInfoActivity> getActivityClass() {
        return ChangeUserInfoActivity.this;
    }

    public void onChangePasswordBtn(View view){
        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    public void onConfirmBtn(View view){
        Log.e("HJLEE", binding.getDomain().toString());
        presenter.updateBasicInfo(binding.getDomain());
        /*Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();*/
    }

    @Override
    public void setUserInfo(User user) {
        binding.setDomain(user);
    }

    @Override
    public void showResultPopup(Integer integer) {
        if(integer == 1){
            super.showBasicOneBtnPopup(getString(R.string.message), getString(R.string.edit_success))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }else{

        }
    }
}
