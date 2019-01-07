package com.animal.scale.hodoo.activity.user.invitation.confirm;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.invitation.InvitationActivity;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityInvitationConfirmBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

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

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        int badgeCount = sharedPrefManager.getIntExtra(SharedPrefVariable.BADGE_COUNT);
        if ( badgeCount > 0 ) {
            BadgeUtils.clearBadge(this);
            sharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, 0);
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invitation_confirm);
        presenter = new InvitationConfirmPresenter(this);

        binding.setActivityInfo(new ActivityInfo("회원 초대"));
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
            finish();
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
                binding.toUserEmailInfo.setText(data.getString("fromUserEmail") + "님의 초대입니다.");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
