package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonListener;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

public interface UserAccountIn {

    interface View {

        void setEditBtn();

        void showPopup(String title, String message);

        void showSinglePopup(String title, String message);

        void showPopup(String title, String message, CommonListener.PopupClickListener listener);

        void setAdapter(int idx, List<User> data);

    }

    interface Presenter {

        void initUserData(Context applicationContext);

        void getData();

        void withdrawGroup( User user );

        void getAccessType();
    }
}
