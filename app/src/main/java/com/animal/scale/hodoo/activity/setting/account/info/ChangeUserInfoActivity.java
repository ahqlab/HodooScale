package com.animal.scale.hodoo.activity.setting.account.info;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.change.password.ChangePasswordActivity;
import com.animal.scale.hodoo.activity.user.login.KakaoLoginActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.input.CommonTextWatcher;
import com.animal.scale.hodoo.databinding.ActivityChangeUserInfoBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Country;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.util.VIewUtil;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 유저의 계정정보를 변경한다.
 */
public class ChangeUserInfoActivity extends BaseActivity<ChangeUserInfoActivity> implements ChangeUserInfoIn.View {

    ActivityChangeUserInfoBinding binding;

    ChangeUserInfoIn.Presenter presenter;

    boolean
            ninkState = false,
            countryState = false;

    private String[] country;
    private int selectCountry = 1;
    private List<Country> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_user_info);
        binding.setActivity(this);
//        binding.setActivityInfo(new ActivityInfo(getString(R.string.change_user_info_title)));
        super.setToolbarColor();
        presenter = new ChangeUserInfoPresenter(this);
        //Conponent 컨트롤
        binding.email.editText.setFocusable(false);
        binding.email.editText.setEnabled(false);

        binding.password.editText.setClickable(true);
        binding.password.editText.setEnabled(false);
        binding.password.editText.setCursorVisible(false);
        binding.password.editText.setFocusable(false);
        binding.password.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPasswordEditTextClick(view);
            }
        });
        binding.nickName.editText.addTextChangedListener(new CommonTextWatcher(
                binding.nickName,
                this,
                CommonTextWatcher.EMPTY_TYPE,
                R.string.istyle_enter_your_nik_name,
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        ninkState = state;
                        Log.e(TAG, String.format("ninkState : %b", ninkState));
                        vaildation();
                    }
                }
        ));

        binding.country.editText.addTextChangedListener(new CommonTextWatcher(
                binding.country,
                this,
                CommonTextWatcher.EMPTY_TYPE,
                0,
                new CommonTextWatcher.CommonTextWatcherCallback() {
                    @Override
                    public void onChangeState(boolean state) {
                        countryState = state;
                        Log.e(TAG, String.format("countryState : %b", countryState));
                        vaildation();
                    }
                }
        ));
        binding.country.editText.setFocusable(false);
        binding.country.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCountryEditTextClick(view);
            }
        });
        for (int i = 0; i < binding.checkBoxWrap.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) binding.checkBoxWrap.getChildAt(i);
            final int position = i;
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position) {
                        case 0 :
                            ((CheckBox)binding.checkBoxWrap.getChildAt(0)).setChecked(true);
                            ((CheckBox)binding.checkBoxWrap.getChildAt(1)).setChecked(false);
                            break;
                        case 1:
                            ((CheckBox)binding.checkBoxWrap.getChildAt(0)).setChecked(false);
                            ((CheckBox)binding.checkBoxWrap.getChildAt(1)).setChecked(true);
                            break;
                    }
                }
            });
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.initLoadData(getApplicationContext());
        presenter.getCountry(VIewUtil.getLocationCode(this));

    }

    /**
     * password 변경시 확인 메시지
     * @param view
     */
    private void onClickPasswordEditTextClick(View view) {
        super.showBasicOneBtnPopup(null, getString(R.string.question_of_change_the_password))
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intent);
                    }
                }).show();
    }


    /**
     * 국가 변경시 나오는 Dialog
     * @param view
     */
    private void onClickCountryEditTextClick(View view) {

        AlertDialog.Builder builder = super.showBasicOneBtnPopup(getResources().getString(R.string.choice_country), null);
        builder.setTitle(getResources().getString(R.string.choice_country));
        // add a radio button list
        builder.setItems(country, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                binding.country.editText.setText(country[which]);
                dialog.dismiss();
                selectCountry = countries.get(which).getId();
            }
        });
        AlertDialog dialog = builder.create();
        ListView listView = dialog.getListView();
        listView.setDivider(new ColorDrawable(Color.parseColor("#e1e1e1")));
        listView.setDividerHeight(2);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_rect_white_radius_10);
        dialog.show();
    }


    @Override
    protected BaseActivity<ChangeUserInfoActivity> getActivityClass() {
        return ChangeUserInfoActivity.this;
    }


    /**
     * 비밀번호 변경 페이지 이동
     * @param view
     */
    public void onClickResetPassword(View view){
        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    /*public void onChangePasswordBtn(View view) {

    }
*/

    /**
     * 회원정보 변경 수행
     * @param view
     */
    public void onConfirmBtn(View view) {

        if ( ((HodooApplication) getApplication()).isExperienceState() ) {
            finish();
            return;
        }


        String nickName =  binding.nickName.editText.getText().toString();
        binding.getDomain().setNickname(nickName);
        binding.getDomain().setCountry(selectCountry);
        //Log.e("HJLEE", binding.getDomain().toString());

        for (int i = 0; i < binding.checkBoxWrap.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) binding.checkBoxWrap.getChildAt(i);
            if ( checkBox.isChecked() )
                binding.getDomain().setSex(checkBox.getTag().toString());
        }
        presenter.updateBasicInfo(binding.getDomain());
        /*Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();*/
    }

    /**
     * 버튼 상태 변경
     * @param state
     */
    private void setBtnEnable(boolean state) {
        binding.confirm.setEnabled(state);
        if (binding.confirm.isEnabled()) {
            binding.confirm.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        } else {
            binding.confirm.setTextColor(ContextCompat.getColor(this, R.color.mainRed));
        }
    }

    private void vaildation() {
        if (ninkState && countryState) {
            setBtnEnable(true);
        } else {
            setBtnEnable(false);
        }
    }

    /**
     * 유저 정보 서버 Callback
     * @param user
     * @Description 서버에서 받은 정보를 Component에 셋팅한다.
     */
    @Override
    public void setUserInfo(User user) {
        if ( user != null ) {
            binding.password.editText.setText(user.getPassword());
            binding.nickName.editText.setText(user.getNickname());
            if ( user.getCountry() > 0 )
                binding.country.editText.setText(country[user.getCountry() - 1]);
            else {
                binding.country.editText.setText(country[0]);
            }
            selectCountry = user.getCountry();
            binding.email.editText.setText(user.getEmail());
            binding.setDomain(user);
        }
    }

    @Override
    public void showResultPopup(Integer integer) {
        if (integer == 1) {
            super.showBasicOneBtnPopup(null, getString(R.string.edit_success))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).show();
        }
    }

    /**
     * 국가를 셋팅한다.
     * @param country
     */
    @Override
    public void setCountry(List<Country> country) {
        String[] countreis = new String[country.size()];
        for (int i = 0; i < country.size(); i++) {
            countreis[i] = country.get(i).getName();
        }
        countries = country;
        this.country = countreis;
        presenter.initUserData();
    }

    /**
     * 메인페이지로 이동
     */
    @Override
    public void goLoginPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, true);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);

        finishAffinity();
        finish();
    }

    /**
     * SNS 회원탈퇴 및 회원가입 로직
     * @param v
     */
    public void snsClick ( View v ) {
        switch( v.getId() ) {
            case R.id.sns_logout :
                if ( ((HodooApplication) getApplication()).isSnsLoginState() ) {
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Log.d(TAG, "sessionClosed!!\n" + errorResult.toString());
                        }
                        @Override
                        public void onNotSignedUp() {
                            Log.d(TAG, "NotSignedUp!!");
                        }
                        @Override
                        public void onSuccess(Long result) {
                            ((HodooApplication) getApplication()).setSnsLoginState(false);
                            presenter.logout();
//                        Toast.makeText(KakaoLoginActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCompleteLogout() {
//                        Toast.makeText(KakaoLoginActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    presenter.logout();
                }


                break;
            case R.id.sns_withdraw :
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
                break;
        }
    }
}
