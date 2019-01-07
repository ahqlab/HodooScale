package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.content.Context;
import android.content.DialogInterface;

import com.animal.scale.hodoo.common.CommonModel;

public class InvitationConfirmPresenter implements InvitationConfirm.Presenter {
    private InvitationConfirm.View mView;
    private InvitationConfirmModel mModel;
    public InvitationConfirmPresenter (InvitationConfirm.View view) {
        mView = view;
        mModel = new InvitationConfirmModel();
    }

    @Override
    public void loadData(Context context) {
        mModel.loadData(context);
    }

    @Override
    public void invitationApproval(int toUserIdx, int fromUserIdx) {
        mModel.invitationApproval(toUserIdx, fromUserIdx, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                if ( result > 0 ) {
                    mView.showPopup("초대완료", "초대가 완료되었습니다.", new InvitationConfirmActivity.CustomDialogCallback() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            mView.closeActivity();
                        }
                    });
                }

            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
