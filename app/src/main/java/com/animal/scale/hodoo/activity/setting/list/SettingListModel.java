package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class SettingListModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public List<SettingMenu> getSettingList() {
        final List<SettingMenu> settingMenus = new ArrayList<SettingMenu>();
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_account_icon_50_50, context.getString(R.string.istyle_setting_menu_my_account)));
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_notice_icon_50_50, context.getString(R.string.istyle_setting_menu_notice)));
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_introduce_icon_50_50, context.getString(R.string.istyle_setting_menu_info_fo_hodoo_scale)));
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_data_reset_icon_50_50, context.getString(R.string.istyle_setting_menu_reset_data)));
        settingMenus.add(new SettingMenu(R.drawable.device_setting_middle_icon_50_50, context.getString(R.string.istyle_setting_menu_setting_device)));
        settingMenus.add(new SettingMenu(R.drawable.setting_user_icon_50_50, context.getString(R.string.istyle_setting_menu_management_user_group)));
        settingMenus.add(new SettingMenu(R.drawable.setting_pet_icon_50_50, context.getString(R.string.istyle_setting_menu_management_pet)));
        return settingMenus;
    }
}
