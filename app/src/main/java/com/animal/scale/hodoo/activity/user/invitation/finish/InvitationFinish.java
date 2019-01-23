package com.animal.scale.hodoo.activity.user.invitation.finish;

public interface InvitationFinish {
    interface View {
        void showPopup( String title, String content );
        void cancelFinish( int result );
    }
    interface Presenter {
        void resend( String to );
        void cancel( String to );
    }
}
