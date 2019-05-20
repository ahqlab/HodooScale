package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.content.Context;

<<<<<<< HEAD
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.MealTip;
import com.animal.scale.hodoo.domain.RealTimeWeight;
import com.animal.scale.hodoo.domain.User;

public interface WelcomeHomeIn {

    interface View{

        void setSnsLoginResult(CommonResponce<User> commonResponce);

        void goInvitationActivity();

        public void showPopup(String message);

        void setBtnState(boolean b);

        void setProgress(boolean b);

        void selectTheNextAction();

        void setServerError();
    }
    interface Presenter{

        void loadData(Context context);

        void doSnsLogin(User user);

        void doLogin(User user);
    }

=======
/**
 * Created by SongSeokwoo on 2019-05-08.
 */
public interface WelcomeHomeIn {
    interface Presenter {
        void initDate(Context context);
        void removeAllPref();
    }
    interface View {

    }
>>>>>>> refs/remotes/origin/master
}
