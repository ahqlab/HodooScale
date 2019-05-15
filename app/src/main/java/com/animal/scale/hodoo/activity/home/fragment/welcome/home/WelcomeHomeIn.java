package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.content.Context;

/**
 * Created by SongSeokwoo on 2019-05-08.
 */
public interface WelcomeHomeIn {
    interface Presenter {
        void initDate(Context context);
        void removeAllPref();
    }
    interface View {

    }
}
