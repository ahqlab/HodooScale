package com.animal.scale.hodoo.activity.user.invitation.finish;

public interface InvitationFinish {
    interface View {
        void showPopup( String title, String content );
    }
    interface Presenter {
        void resend( String to );
    }
}
