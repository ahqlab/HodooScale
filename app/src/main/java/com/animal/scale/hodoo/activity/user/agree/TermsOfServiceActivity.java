package com.animal.scale.hodoo.activity.user.agree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.signup.SignUpActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityTermsOfServiceBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

import butterknife.ButterKnife;

public class TermsOfServiceActivity extends BaseActivity<TermsOfServiceActivity> {

    ActivityTermsOfServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_of_service);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.agree_terms)));
        super.setToolbarColor();
    }

    @Override
    protected BaseActivity<TermsOfServiceActivity> getActivityClass() {
        return TermsOfServiceActivity.this;
    }

    public void onClickCompleateBtn(View view){
        if(isCheckStatus()){
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
            finish();
        }else{
            AlertDialog.Builder builder = super.showBasicOneBtnPopup("Message",getString(R.string.istyle_agree_to_the_terms));
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    public boolean isCheckStatus(){
        if(binding.personalInformationCheck.isChecked() &&  binding.termsOfUseCheck.isChecked()){
            return true;
        }
        return false;
    }
    public void onClickCancleBtn(View view){
        finish();
    }
}
