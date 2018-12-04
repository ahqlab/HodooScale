package com.animal.scale.hodoo.activity.test;

import android.content.Context;

public class TestPresenter implements TestImpl.Presenter {

    private TestImpl.View mView;
    private TestModel mModel;
    public TestPresenter ( TestImpl.View v ) {
        mView = v;
        mModel = new TestModel();
    }
    @Override
    public void workModelTest(Context context) {
        mModel.workModelTest(context);
    }
}
