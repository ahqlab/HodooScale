package com.animal.scale.hodoo;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonNotificationModel;

public class MainPresenter implements Main.Presenter {
    private String TAG = MainPresenter.class.getSimpleName();
    private Main.View mView;
    private MainModel mModel;
    private CommonNotificationModel commonModel;
    public MainPresenter ( Main.View view ) {
        mView = view;
        mModel = MainModel.getInstance();

    }
    @Override
    public void initDate( Context context ) {
        mModel.loadData(context);
        commonModel = CommonNotificationModel.getInstance(context);
    }

    @Override
    public void getData() {
        if ( mModel.getAutoLoginData() > 0 ) {
            mView.goAutoLogin();
        }
    }
}
