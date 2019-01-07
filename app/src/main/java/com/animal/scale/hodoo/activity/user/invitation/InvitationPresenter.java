package com.animal.scale.hodoo.activity.user.invitation;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;

public class InvitationPresenter implements Invitation.Presenter {
    private String TAG = InvitationPresenter.class.getSimpleName();
    private Invitation.View mView;
    private InvitationModel model;

    public InvitationPresenter( Invitation.View view ) {
        mView = view;
        model = new InvitationModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void sendInvitation(final String to) {
        mView.setProgress(true);
        model.sendInvitation(to, model.getUserEmail(), new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                mView.setProgress(false);
                if ( result == InvitationActivity.SUCESS ) {
                    Log.e(TAG, "전송 완료");
                    mView.showPopup("사용자 초대 완료", to + "님에게 초대 메세지를 발송했습니다.", new InvitationActivity.CustomDialogCallback() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
//                            mView.goLoginPage();
                        }
                    });
                } else if ( result == InvitationActivity.NOT_TO_USER ) {
                    mView.showPopup("사용자 초대 에러", "초대하실 회원이 없습니다.", null);
                }
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
