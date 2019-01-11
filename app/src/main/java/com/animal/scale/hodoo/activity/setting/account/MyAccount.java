package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonListener;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

public interface MyAccount {

    public static final int LOGOUT = -1;
    public static final int CHANGE_USER_INFO = 0;
    public static final int WITHDRAW = 1;

    interface View {

        public void setListviewAdapter(List<SettingMenu> menus);

        public void goLoginPage();

        public void goChangePasswordPage();

        void showPopup( String message );

        void showPopup(String message, CommonListener.PopupClickListener clickListener);

    }

    interface Presenter {

        public void getSttingListMenu();

        public void initLoadData(Context context);

        public void logout();

        public void changePassword();

        void initUserData();

        void saveFCMToken ( User user );

        void withdraw();
    }
}
