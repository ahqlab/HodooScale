package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

public interface Login {

    interface View {

        public void showPopup(String message);

        public void setProgress(Boolean play);

        public void goDeviceRegistActivity();

        public void goPetRegistActivity(int petIdx);

        public void goDiseasesRegistActivity(int petIdx);

        public void goPhysicalRegistActivity(int petIdx);

        public void goWeightRegistActivity(int petIdx);

        public void goHomeActivity();

        public void goTermsOfServiceActivity();

    }

    interface Presenter {

        public void initUserData(User user, Context context);

        public void sendServer();

        public void userValidationCheck(User user);

        public void saveUserSharedValue(User user);

        public void checkRegistrationStatus();
    }
}
