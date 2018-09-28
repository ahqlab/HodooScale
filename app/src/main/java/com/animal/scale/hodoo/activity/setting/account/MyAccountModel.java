package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;

import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class MyAccountModel {

    public SharedPrefManager mSharedPrefManager;

    Context context;

    public void initLoadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public List<SettingMenu> getSettingList() {
        final List<SettingMenu> settingMenus = new ArrayList<SettingMenu>();
        settingMenus.add(new SettingMenu("로그아웃"));
        settingMenus.add(new SettingMenu("사용자 정보 변경"));
        return settingMenus;
    }

    public void logout() {
        mSharedPrefManager.removeAllPreferences();

    }
}
