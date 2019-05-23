package com.animal.scale.hodoo.activity.setting.account.info;

import android.content.Context;

import com.animal.scale.hodoo.domain.Country;
import com.animal.scale.hodoo.domain.User;

import org.json.JSONArray;

import java.util.List;

public interface ChangeUserInfoIn {

    interface View{

        void setUserInfo(User user);

        void showResultPopup(Integer integer);

        void setCountry(List<Country> country);

        void goLoginPage();
    }

    interface Presenter{

        void initLoadData(Context context);

        void initUserData();

        void updateBasicInfo(User user);

        void getCountry ( int language );

        void logout();

    }
}
