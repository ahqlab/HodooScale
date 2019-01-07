package com.animal.scale.hodoo.activity.user.reset.password.send;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.FinPasswordResponse;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

public interface SendCertificationNumberIn {

    interface View{

        void sendResult(CommonResponce<User> userCommonResponce);

        public void setProgress(Boolean play);
    }

    interface Presenter{

        void loadData(Context context);

        void sendTempPassword(User user);
    }
}
