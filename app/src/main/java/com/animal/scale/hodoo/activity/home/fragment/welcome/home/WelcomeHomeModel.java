package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;

/**
 * Created by SongSeokwoo on 2019-05-08.
 */
public class WelcomeHomeModel extends CommonModel {
    private SharedPrefManager sharedPrefManager;
    public void initData(Context context) {
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public void removeAllPref() {
        sharedPrefManager.removeAllPreferences();
    }
}
