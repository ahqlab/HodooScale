package com.animal.scale.hodoo.activity.user.invitation;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.animal.scale.hodoo.R;
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

    /**
     * 초대를 발송한다.
     *
     * @param to   초대 받을 대상의 유저 이메일 또는 고유 아이디값
     * @return
     * @description
    */
    @Override
    public void sendInvitation(final String to) {
        mView.setProgress(true);
        model.sendInvitation(to, model.getUserEmail(), new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                mView.setProgress(false);
                if ( result == InvitationActivity.SUCESS ) {
                    setInvitationData( to );
                    mView.goFinishPage();
                } else if (result == InvitationActivity.EXISTENCE_USER) {
                    mView.showPopup(R.string.invitation__invitation_error_title,  R.string.invitation__error_existence_user, new InvitationActivity.CustomDialogCallback() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            mView.goFinishPage();
                        }
                    });
                } else if ( result == InvitationActivity.NOT_TO_USER ) {
                    mView.showPopup(R.string.invitation__invitation_error_title, R.string.invitation__error_not_user, null);
                } else if ( result == InvitationActivity.NOT_TO_DEVICE ) {
                    mView.showPopup(R.string.invitation__invitation_error_title, R.string.invitation__error_not_device, null);
                } else if ( result == InvitationActivity.OVERLAB_INVITATION ) {
                    mView.showPopup(R.string.invitation__invitation_error_title, R.string.invitation__error_overlab_invitation, null);
                }
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void setInvitationData(String mail) {
        model.setInvitationUser(mail);
    }

    @Override
    public void removeAutoLogin() {
        model.removeAutoLogin();
    }
}
