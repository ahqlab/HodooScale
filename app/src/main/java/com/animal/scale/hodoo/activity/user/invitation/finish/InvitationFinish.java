package com.animal.scale.hodoo.activity.user.invitation.finish;

import android.content.Context;

public interface InvitationFinish {
    interface View {
        void showPopup( String title, String content );
        void cancelFinish( int result );
    }
    interface Presenter {
        void resend(Context context, String to );
        void cancel( String to );
    }
}
