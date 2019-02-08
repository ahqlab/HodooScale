package com.animal.scale.hodoo.activity.user.invitation.finish;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.animal.scale.hodoo.activity.user.invitation.InvitationActivity;
import com.animal.scale.hodoo.common.CommonModel;

public class InvitationFinishPresenter implements InvitationFinish.Presenter {
    private InvitationFinish.View mView;
    private InvitationFinishModel mModel;
    public InvitationFinishPresenter (Context context, InvitationFinish.View view) {
        mView = view;
        mModel = new InvitationFinishModel(context);
    }

    @Override
    public void resend(final String to) {
        mModel.resend(to, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
//                mView.setProgress(false);
                if ( result == InvitationActivity.SUCESS ) {
                    mView.showPopup("사용자 초대 완료", to + "님에게 초대 메세지를 발송했습니다.");
                } else if (result == InvitationActivity.EXISTENCE_USER) {
                    mView.showPopup("사용자 초대 에러",  "이미 초대가 완료되었습니다.\n로그인창으로 이동합니다.");
                } else if ( result == InvitationActivity.NOT_TO_USER ) {
                    mView.showPopup("사용자 초대 에러", "초대하실 회원이 없습니다.");
                } else if ( result == InvitationActivity.NOT_TO_DEVICE ) {
                    mView.showPopup("사용자 초대 에러", "초대하신 회원이 디바이스를 가지고 있지 않습니다.");
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
    public void cancel(String to) {
        mModel.cancel(to, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                mView.cancelFinish(integer);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
