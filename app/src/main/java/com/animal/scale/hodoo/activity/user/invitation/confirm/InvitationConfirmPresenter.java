package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.content.Context;
import android.content.DialogInterface;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.user.group.list.UserGroupListActivity;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.constant.HodooConstant;

public class InvitationConfirmPresenter implements InvitationConfirm.Presenter {
    private InvitationConfirm.View mView;
    private InvitationConfirmModel mModel;
    private CommonNotificationModel notificationModel;

    public InvitationConfirmPresenter (InvitationConfirm.View view) {
        mView = view;
        mModel = new InvitationConfirmModel();
    }

    @Override
    public void loadData(Context context) {
        mModel.loadData(context);
        notificationModel = CommonNotificationModel.getInstance(context);
    }

    @Override
    public void updateBadgeCount( int to, int from ) {
        int count = mModel.updateBadgeCount(to, from);
        if ( count > 0 ) {
            saveBadgeCount(count);
        } else {
            mView.clearBadge();

        }
    }

    @Override
    public void invitationApproval(final int toUserIdx, final int fromUserIdx) {
        mModel.setInvitationState(HodooConstant.ACCEPT_TYPE, toUserIdx, fromUserIdx, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                if ( result != null ) {
                    if ( result > 0 ) {
                        notificationModel.removeInvitationUser(toUserIdx, fromUserIdx);
                        if ( UserGroupListActivity.ACCEPT_TYPE == HodooConstant.ACCEPT_TYPE ) {
                            mModel.invitationApproval(toUserIdx, fromUserIdx, new CommonModel.DomainCallBackListner<Integer>() {
                                @Override
                                public void doPostExecute(Integer result) {
                                    if ( result > 0 ) {
                                        mView.showPopup(R.string.invitation_confirm__fin_title, R.string.invitation_confirm__fin_suffix, new InvitationConfirmActivity.CustomDialogCallback() {
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

                                @Override
                                public void doCancelled() {

                                }
                            });
                        } else {

                        }

                    } else {

                    }
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
    public void invitationRefusal(final int to, final int from) {
        mModel.setInvitationState(HodooConstant.DECLINE_TYPE, to, from, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                if ( result != null ) {
                    if ( result > 0 ) {
                        notificationModel.removeInvitationUser(to, from);
                        mView.closeActivity();
                    }
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
    public void saveBadgeCount( int count ) {
        mModel.saveBadgeCount(count);
    }


}
