package com.animal.scale.hodoo.activity.setting.account.info;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

public interface ChangeUserInfoIn {

    interface View{

        void setUserInfo(User user);

        void showResultPopup(Integer integer);
    }

    interface Presenter{

        void initLoadData(Context context);

        void initUserData();

        void updateBasicInfo(User user);

    }
}
