package com.animal.scale.hodoo.activity.user.invitation.finish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivityInvitationFinishBinding;

public class InvitationFinishActivity extends BaseActivity<InvitationFinishActivity> implements InvitationFinish.View {
    private ActivityInvitationFinishBinding binding;
    private InvitationFinish.Presenter presenter;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invitation_finish);
        binding.setActivity(this);
        presenter = new InvitationFinishPresenter(this, this);
    }

    @Override
    protected BaseActivity<InvitationFinishActivity> getActivityClass() {
        return this;
    }

    public void reSend ( View view ) {
        presenter.resend(this, intent.getStringExtra(HodooConstant.INVITATION_EMAIL_KEY));
    }
    public void cancel ( View v ) {
        presenter.cancel( intent.getStringExtra(HodooConstant.INVITATION_EMAIL_KEY) );
    }

    @Override
    public void showPopup(String title, String content) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void cancelFinish(int result) {
        if ( result > 0 ) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.invitation_finish__alert_cancel_title)
                    .setMessage(R.string.invitation_finish__alert_cancel_content)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            finish();
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    }
}
