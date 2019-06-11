package com.animal.scale.hodoo.activity.setting.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.info.ChangeUserInfoActivity;
import com.animal.scale.hodoo.adapter.AdapterOfMyAccountList;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.CommonListener;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityMyAccountBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.util.List;

public class MyAccountActivity extends BaseActivity<MyAccountActivity> implements MyAccount.View {

    ActivityMyAccountBinding binding;

    MyAccount.Presenter presenter;

    AdapterOfMyAccountList adapter;

    public SharedPrefManager mSharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_account);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_my_account)));
        super.setToolbarColor();
        presenter = new MyAccountPresenter(this);
        presenter.initLoadData(getApplicationContext());
        presenter.getSttingListMenu();
    }

    @Override
    protected BaseActivity<MyAccountActivity> getActivityClass() {
        return MyAccountActivity.this;
    }

    @Override
    public void setListviewAdapter(List<SettingMenu> menus) {
        adapter = new AdapterOfMyAccountList(MyAccountActivity.this, menus);
        binding.settingListview.setAdapter(adapter);
        binding.settingListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == MyAccount.LOGOUT){
                    presenter.initUserData();
                } else if(position == MyAccount.CHANGE_USER_INFO){
                    presenter.changePassword();
                }  else if ( position == MyAccount.WITHDRAW ) {
                    if ( ((HodooApplication) getApplicationContext()).isExperienceState() )
                        return;

                    if ( !((HodooApplication) getApplication()).isSnsLoginState() )
                        presenter.checkGroupCount(getApplicationContext());
                    else
                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                Log.e("HJLEE", "onFailure : " + errorResult.toString());
                            }

                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                Log.e("HJLEE", "onSessionClosed : " + errorResult.toString());
                            }

                            @Override
                            public void onNotSignedUp() {
                                Log.e("HJLEE", "onNotSignedUp : ");
                            }

                            @Override
                            public void onSuccess(Long userId) {
                                Log.e("HJLEE", "onSuccess : " + userId);
                                ((HodooApplication) getApplication()).setSnsLoginState(false);
                                presenter.logout();
                            }
                        });
                }
            }
        });
    }

    @Override
    public void goLoginPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, true);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);

        finishAffinity();
        finish();
    }

    @Override
    public void goChangePasswordPage() {
        Intent intent = new Intent(getApplicationContext(), ChangeUserInfoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }

    @Override
    public void showPopup( String message ) {
        super.showBasicOneBtnPopup(getString(R.string.message), message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                ).show();
    }

    @Override
    public void singleShowPopup(String message, final CommonListener.PopupClickListener clickListener) {
        super.showBasicOneBtnPopup(getString(R.string.message), message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clickListener.onPositiveClick(dialog, which);
                            }
                        }
                ).show();
    }
    @Override
    public void showPopup(String message, final CommonListener.PopupClickListener clickListener) {
        super.showBasicOneBtnPopup(getString(R.string.message), message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clickListener.onPositiveClick(dialog, which);
                            }
                        }
                )
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clickListener.onNegativeClick(dialogInterface, i);
                    }
                }).show();
    }
}
