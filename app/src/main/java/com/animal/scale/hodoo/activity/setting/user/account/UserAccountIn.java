package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

import java.util.List;

public interface UserAccountIn {

    interface View {

        void showPopup(String title, String message);

        void setAdapter(int idx, List<User> data);
    }

    interface Presenter {

        void initUserData(Context applicationContext);

        void getData();
    }
}
