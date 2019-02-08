package com.animal.scale.hodoo.activity.test;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;

public class TestPresenter implements Test.Presenter {
    private Test.View mView;
    private TestModel mModel;
    TestPresenter ( Test.View view ) {
        mView = view;
        mModel = new TestModel();
    }

    @Override
    public void initData(Context context) {
        mModel.loadData(context);
    }

    @Override
    public void sendNoti() {
        mModel.sendNoti("ww@ww.com", new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {

            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
