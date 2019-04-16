package com.animal.scale.hodoo.activity.setting.notice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;

public class NoticeActivity extends BaseActivity<NoticeActivity> {

    /*ActivityNoticeBinding binding;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
    }

    @Override
    protected BaseActivity<NoticeActivity> getActivityClass() {
        return NoticeActivity.this;
    }
}
