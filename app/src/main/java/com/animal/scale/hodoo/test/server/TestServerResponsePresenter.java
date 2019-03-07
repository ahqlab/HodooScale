package com.animal.scale.hodoo.test.server;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;

public class TestServerResponsePresenter implements TestServerResponseIn.Presenter {

    private TestServerResponseIn.View mView;
    private TestServerResponseModel mModel;

    TestServerResponsePresenter(TestServerResponseIn.View view ) {
        mView = view;
        mModel = new TestServerResponseModel();
    }

    @Override
    public void loadData(Context context) {
        mModel.loadData(context);
    }

    /*@Override
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
    }*/
}
