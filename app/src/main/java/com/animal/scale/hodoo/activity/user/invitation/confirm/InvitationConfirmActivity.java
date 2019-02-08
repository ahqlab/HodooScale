package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.invitation.InvitationActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityInvitationConfirmBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.BadgeUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class InvitationConfirmActivity extends BaseActivity<InvitationActivity> implements InvitationConfirm.View {
    private String TAG = InvitationConfirmActivity.class.getSimpleName();
    private ActivityInvitationConfirmBinding binding;
    private JSONObject data;
    private InvitationConfirm.Presenter presenter;

    public interface CustomDialogCallback {
        void onClick( DialogInterface dialog, int i );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotificationManager notifManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invitation_confirm);
        presenter = new InvitationConfirmPresenter(this);

        binding.setActivityInfo(new ActivityInfo(getString(R.string.invitation__tool_bar_title)));
        binding.setActivity(this);
        presenter.loadData(this);

        onNewIntent(getIntent());
    }

    @Override
    protected BaseActivity<InvitationActivity> getActivityClass() {
        return this;
    }

    public void btnClick ( View v ) {
        if ( v == binding.confirm ) {
            try {
                presenter.invitationApproval( data.getInt("toUserIdx"), data.getInt("fromUserIdx") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ( v == binding.cancel ) {
            try {
                presenter.invitationRefusal( data.getInt("toUserIdx"), data.getInt("fromUserIdx") );
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            finish();
        }
    }

    @Override
    public void showPopup(String title, String content, final CustomDialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        callback.onClick(dialog, i);
                    }
                });
        builder.show();
    }

    @Override
    public void showPopup(int title, int content, CustomDialogCallback callback) {
        showPopup( getString(title), getString(content), callback );
    }

    @Override
    public void clearBadge() {
        BadgeUtils.clearBadge(this);
        presenter.saveBadgeCount(0);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle extras = intent.getExtras();
        if(extras != null){
            try {
                data = new JSONObject(extras.getString("data"));
                binding.toUserEmailInfo.setText(data.getString("fromUserEmail") + getString(R.string.invitation_confirm__suffix));
                presenter.updateBadgeCount(data.getInt("toUserIdx"), data.getInt("fromUserIdx"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
    }
}
