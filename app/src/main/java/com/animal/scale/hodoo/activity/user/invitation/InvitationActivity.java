package com.animal.scale.hodoo.activity.user.invitation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.invitation.finish.InvitationFinishActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivityInvitationBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.util.ValidationUtil;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class InvitationActivity extends BaseActivity<InvitationActivity> implements Invitation.View {

    public static int NOT_TO_DEVICE = -2;
    public static int NOT_TO_USER = -1;
    public static int ERROR = 0;
    public static int SUCESS = 1;
    public static int EXISTENCE_USER = 2;
    public static int OVERLAB_INVITATION = 3;

    private ActivityInvitationBinding binding;
    private Invitation.Presenter presenter;

    public interface CustomDialogCallback {
        void onClick( DialogInterface dialog, int i );
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_invitation);
        binding.setActivity(this);
        presenter = new InvitationPresenter(this);
        presenter.loadData(this);

        binding.setActivityInfo(new ActivityInfo(getString(R.string.invitation__tool_bar_title)));
        binding.email.editText.addTextChangedListener(new CommonTextWatcher(
                binding.email,
                this,
                CommonTextWatcher.EMPTY_TYPE,
                R.string.invitation__content,
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        validation();
                    }
                }
        ));
    }

    @Override
    protected BaseActivity<InvitationActivity> getActivityClass() {
        return this;
    }
    public void sendInvition(View view) {
        presenter.sendInvitation( binding.email.editText.getText().toString() );
    }
    private void validation() {
        setBtnEnable(!ValidationUtil.isEmpty(binding.email.editText.getText().toString()));
    }
    private void setBtnEnable ( boolean state ) {
        binding.confirm.setEnabled(state);
        if ( binding.confirm.isEnabled() ) {
            binding.confirm.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.confirm.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }

    @Override
    public void showPopup(String title, String content, final CustomDialogCallback callback) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (callback != null)
                            callback.onClick(dialog,i);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showPopup(int title, int content, final CustomDialogCallback callback) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(content)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (callback != null)
                            callback.onClick(dialog,i);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    @Override
    public void setProgress(boolean state) {
        binding.progress.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void goFinishPage() {
//        mSharedPrefManager.removeAllPreferences();
        presenter.removeAutoLogin();
        Intent intent = new Intent(this, InvitationFinishActivity.class);
        intent.putExtra(HodooConstant.INVITATION_EMAIL_KEY, binding.email.editText.getText().toString());
        intent.putExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, true);
        startActivity(intent);
        finish();
    }
}
