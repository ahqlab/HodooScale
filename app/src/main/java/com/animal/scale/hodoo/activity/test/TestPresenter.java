package com.animal.scale.hodoo.activity.test;

import android.content.Context;

public class TestPresenter implements Test.Presenter {

    private Test.View view;

    private TestModel model;


    TestPresenter ( Test.View view ) {
        this.view = view;
        this.model = new TestModel();
    }

    @Override
    public void loadModel(Context context) {
        model.loadData(context);
    }


}
