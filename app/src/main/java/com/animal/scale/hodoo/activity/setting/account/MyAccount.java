package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;

import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public interface MyAccount {

    public static final int LOGOUT = 0;
    public static final int CHANGE_USER_INFO = 1;

    interface View {

        public void setListviewAdapter(List<SettingMenu> menus);

        public void goLoginPage();

        public void goChangePasswordPage();

    }

    interface Presenter {

        public void getSttingListMenu();

        public void initLoadData(Context context);

        public void logout();

        public void changePassword();
    }
}
