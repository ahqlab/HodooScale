package com.animal.scale.hodoo;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;

public class MainModel extends CommonModel {
    private SharedPrefManager mSharedPrefManager;
    public static MainModel getInstance (){
        return new MainModel();
    }
    @Override
    public void loadData(Context context) {
        super.loadData(context);
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public int getAutoLoginData () {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.AUTO_LOGIN);
    }
    public void setBadgeCount ( int count ) {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, count);
    }
}
