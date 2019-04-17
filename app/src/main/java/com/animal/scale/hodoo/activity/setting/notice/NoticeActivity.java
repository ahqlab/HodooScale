package com.animal.scale.hodoo.activity.setting.notice;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityNoticeBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;

public class NoticeActivity extends BaseActivity<NoticeActivity> implements NoticeIn.View{

    ActivityNoticeBinding binding;

    NoticeIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.activity_title_notice)));
        super.setToolbarColor();
        presenter  = new NoticePresenter(NoticeActivity.this);
        presenter.loadModel(NoticeActivity.this);
    }

    @Override
    protected BaseActivity<NoticeActivity> getActivityClass() {
        return NoticeActivity.this;
    }
}
