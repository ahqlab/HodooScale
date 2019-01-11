package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public interface SettingList {

    interface View {

        public void setListviewAdapter(int badgeCount, List<SettingMenu> menus);
        void goLoginPage();
    }

    interface Presenter {

        void loadData(Context context);

        public void getSttingListMenu();

        void logout();

    }
}
