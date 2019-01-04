package com.animal.scale.hodoo.activity.user.invitation;

import android.content.Context;

public interface Invitation {
    interface View {
        void showPopup(String title, String content, InvitationActivity.CustomDialogCallback callback);
        void setProgress( boolean state );
        void goLoginPage();
    }
    interface Presenter {
        void loadData (Context context);
        void sendInvitation( String to );
    }
}
