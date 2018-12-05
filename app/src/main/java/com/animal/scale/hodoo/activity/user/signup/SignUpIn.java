package com.animal.scale.hodoo.activity.user.signup;

import android.content.Context;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

public interface SignUpIn {

    interface View{

        void goNextPage();

        void registUserResult(ResultMessageGroup resultMessageGroup);

        void showPopup(String message);
    }

    interface Presenter{

        void loadData(Context context);

        void registUser(User user);
    }
}
