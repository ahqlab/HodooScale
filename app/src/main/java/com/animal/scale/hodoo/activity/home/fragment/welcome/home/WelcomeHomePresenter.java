package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.content.Context;

/**
 * Created by SongSeokwoo on 2019-05-08.
 */
public class WelcomeHomePresenter implements WelcomeHomeIn.Presenter {
    private WelcomeHomeIn.View view;
    private WelcomeHomeModel model;
    WelcomeHomePresenter ( WelcomeHomeIn.View view ) {
        this.view = view;
        model = new WelcomeHomeModel();
    }

    @Override
    public void initDate(Context context) {
        model.initData(context);
    }

    @Override
    public void removeAllPref() {
        model.removeAllPref();
    }
}
