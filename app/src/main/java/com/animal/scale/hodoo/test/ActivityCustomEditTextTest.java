package com.animal.scale.hodoo.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityCustomEditTextTestBinding;

public class ActivityCustomEditTextTest extends BaseActivity<ActivityCustomEditTextTest> {

    ActivityCustomEditTextTestBinding binding;

    TextWatcher textWatcher;


    public static ActivityCustomEditTextTest activityCustomEditTextTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_custom_edit_text_test);
        binding.setActivity(this);
        //binding.sex.editText.addTextChangedListener(new EmailTextWatcher(binding.sex));
    }


    @Override
    protected BaseActivity<ActivityCustomEditTextTest> getActivityClass() {
        return ActivityCustomEditTextTest.this;
    }

    public void onBtnClick(View view){
        //binding.commonEditText.setErrorMessage("이메일 형식에 맞지 않습니다.");
    }
}
