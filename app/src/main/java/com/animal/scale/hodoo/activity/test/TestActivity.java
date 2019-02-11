package com.animal.scale.hodoo.activity.test;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.animal.scale.hodoo.base.BaseActivity;

public class TestActivity extends BaseActivity<TestActivity> implements Test.View {
    private Test.Presenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TestPresenter(this);
        presenter.initData(this);

    }

    @Override
    protected BaseActivity<TestActivity> getActivityClass() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.sendNoti();
    }
}
