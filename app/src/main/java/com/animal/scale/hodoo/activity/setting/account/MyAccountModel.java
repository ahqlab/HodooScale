package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;

import com.animal.scale.hodoo.R;
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
        settingMenus.add(new SettingMenu(context.getString(R.string.log_out)));
        settingMenus.add(new SettingMenu(context.getString(R.string.change_user_account_info)));
        return settingMenus;
    }

    public void logout() {
        mSharedPrefManager.removeAllPreferences();

    }
}
