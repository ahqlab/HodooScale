package com.animal.scale.hodoo.activity.setting.account.change.password;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

public interface ChangePassword {

    interface View {

        void userPasswordUpdateResult(Integer integer);
    }

    interface Presenter {

        void changeUserPassword(User user);

        void initLoadData(Context context);
    }
}
