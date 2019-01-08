package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.content.Context;

public interface InvitationConfirm {
    interface View {
        void showPopup ( String title, String content, InvitationConfirmActivity.CustomDialogCallback callback );
        void closeActivity ();
    }
    interface Presenter {
        void loadData(Context context);
        void invitationApproval( int toUserIdx, int fromUserIdx );
    }
}
