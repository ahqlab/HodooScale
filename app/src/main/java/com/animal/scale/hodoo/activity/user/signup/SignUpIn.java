package com.animal.scale.hodoo.activity.user.signup;

import android.content.Context;

import com.animal.scale.hodoo.domain.Country;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

import org.json.JSONArray;

import java.util.List;

public interface SignUpIn {

    interface View{

        void goNextPage();

        void registUser();

        void registUserResult(ResultMessageGroup resultMessageGroup);

        void showPopup(String message);

        void sendEmail( String userEmail );

        void setProgress ( boolean state );

        void showCountry (List<Country> countries);
    }

    interface Presenter{

        void loadData(Context context);

        void registUser(User user);

        void userCertifiedMailSend (String toMail);

        void getAllCountry(int country);
    }
}
