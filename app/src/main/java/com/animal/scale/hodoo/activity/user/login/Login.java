package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

public interface Login {

    interface View {

        public void showPopup(String message);

        public void showPopup(String message, LoginPresenter.OnDialogClickListener callback);

        public void setProgress(Boolean play);

        public void goDeviceRegistActivity();

        public void goPetRegistActivity(int petIdx);

        public void goDiseasesRegistActivity(int petIdx);

        public void goPhysicalRegistActivity(int petIdx);

        public void goWeightRegistActivity(int petIdx);

        public void goHomeActivity();

        public void goTermsOfServiceActivity();

        void setAutoLoginState();

        void setAutoLogin( boolean state );

        void goEmailCertified();

        void setPassword( String pw );

        void setBtnState( boolean state );

        void saveFcmToken();

        void selectTheNextAction();

        void setServerError();

        void goInvitationActivity();
    }

    interface Presenter {

        public void initUserData(User user, Context context);

        public void sendServer(User user);

        public void userValidationCheck(User user);

        public void saveUserSharedValue(User user);

        public void checkRegistrationStatus();

        void saveFCMToken(User user);

        void setAutoLogin( boolean state );

        void autoLogin ();
    }
}
