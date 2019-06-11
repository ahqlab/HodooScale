package com.animal.scale.hodoo.activity.user.invitation.finish;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.invitation.InvitationActivity;
import com.animal.scale.hodoo.common.CommonModel;

public class InvitationFinishPresenter implements InvitationFinish.Presenter {
    private InvitationFinish.View mView;
    private InvitationFinishModel mModel;
    public InvitationFinishPresenter (Context context, InvitationFinish.View view) {
        mView = view;
        mModel = new InvitationFinishModel(context);
    }

    /**
     * 초대를 재발송한다
     * @param context
     * @param to
     */
    @Override
    public void resend(final Context context, final String to) {
        mModel.resend(to, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
//                mView.setProgress(false);
                if ( result == InvitationActivity.SUCESS ) {
                    mView.showPopup(context.getString(R.string.invitation_finish__resend_alert_success_title), to + " " + context.getString(R.string.invitation_finish__resend_alert_success_suffix));
                } else if (result == InvitationActivity.EXISTENCE_USER) {
                    mView.showPopup(context.getString(R.string.invitation_finish__resend_alert_error_title),  context.getString(R.string.invitation__error_existence_user));
                } else if ( result == InvitationActivity.NOT_TO_USER ) {
                    mView.showPopup(context.getString(R.string.invitation_finish__resend_alert_error_title), context.getString(R.string.invitation__error_not_user));
                } else if ( result == InvitationActivity.NOT_TO_DEVICE ) {
                    mView.showPopup(context.getString(R.string.invitation_finish__resend_alert_error_title), context.getString(R.string.invitation__error_not_device));
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

    /**
     * 초대를 취소한다.
     * @param to
     */
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
