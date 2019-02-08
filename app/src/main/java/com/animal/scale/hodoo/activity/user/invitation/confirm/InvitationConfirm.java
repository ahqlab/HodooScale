package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.content.Context;

public interface InvitationConfirm {
    interface View {
        void showPopup ( String title, String content, InvitationConfirmActivity.CustomDialogCallback callback );
        void showPopup ( int title, int content, InvitationConfirmActivity.CustomDialogCallback callback );
        void clearBadge();
        void closeActivity ();
    }
    interface Presenter {
        void loadData(Context context);
        void updateBadgeCount( int to, int from );
        void invitationApproval( int toUserIdx, int fromUserIdx );
        void invitationRefusal ( int to, int from );
        void saveBadgeCount( int count );
    }
}
