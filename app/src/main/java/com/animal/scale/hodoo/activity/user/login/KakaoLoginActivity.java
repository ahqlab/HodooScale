package com.animal.scale.hodoo.activity.user.login;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.KakaoAuthItem;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KakaoLoginActivity extends AppCompatActivity  implements View.OnClickListener{


    private String TAG = "KakaoLoginActivity";
    // private LoginButton btnKakaoLogin;
    private Button btnLogin;
    private Button btnLogout;
    private Button btnProfile;
    private SessionCallback callback;
    private UserManagement userManagement;



    private List<AuthType> getAuthTypes() {
        final List<AuthType> availableAuthTypes = new ArrayList<>();
        if (Session.getCurrentSession().getAuthCodeManager().isTalkLoginAvailable()) {
            availableAuthTypes.add(AuthType.KAKAO_TALK);
        }
        if (Session.getCurrentSession().getAuthCodeManager().isStoryLoginAvailable()) {
            availableAuthTypes.add(AuthType.KAKAO_STORY);
        }
        availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);

        AuthType[] authTypes = KakaoSDK.getAdapter().getSessionConfig().getAuthTypes();
        if (authTypes == null || authTypes.length == 0 || (authTypes.length == 1 && authTypes[0] == AuthType.KAKAO_LOGIN_ALL)) {
            authTypes = AuthType.values();
        }
        availableAuthTypes.retainAll(Arrays.asList(authTypes));

        // 개발자가 설정한 것과 available 한 타입이 없다면 직접계정 입력이 뜨도록 한다.
        if(availableAuthTypes.size() == 0){
            availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);
        }

        return availableAuthTypes;
    }

    private KakaoAuthItem[] createAuthItemArray(final List<AuthType> authTypes) {
        final List<KakaoAuthItem> kakaoAuthItemList = new ArrayList<KakaoAuthItem>();
        if(authTypes.contains(AuthType.KAKAO_TALK)) {
            kakaoAuthItemList.add(new KakaoAuthItem(com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account, com.kakao.usermgmt.R.drawable.talk, com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account_tts, AuthType.KAKAO_TALK));
        }
        if(authTypes.contains(AuthType.KAKAO_STORY)) {
            kakaoAuthItemList.add(new KakaoAuthItem(com.kakao.usermgmt.R.string.com_kakao_kakaostory_account, com.kakao.usermgmt.R.drawable.story, com.kakao.usermgmt.R.string.com_kakao_kakaostory_account_tts, AuthType.KAKAO_STORY));
        }
        if(authTypes.contains(AuthType.KAKAO_ACCOUNT)){
            kakaoAuthItemList.add(new KakaoAuthItem(com.kakao.usermgmt.R.string.com_kakao_other_kakaoaccount, com.kakao.usermgmt.R.drawable.account, com.kakao.usermgmt.R.string.com_kakao_other_kakaoaccount_tts, AuthType.KAKAO_ACCOUNT));
        }

        return kakaoAuthItemList.toArray(new KakaoAuthItem[kakaoAuthItemList.size()]);
    }

    @SuppressWarnings("deprecation")
    private ListAdapter createLoginAdapter(final KakaoAuthItem[] authKakaoAuthItems) {
    /*
      가능한 auth type들을 유저에게 보여주기 위한 준비.
     */
        return new ArrayAdapter<KakaoAuthItem>(
                this,
                android.R.layout.select_dialog_item,
                android.R.id.text1, authKakaoAuthItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(com.kakao.usermgmt.R.layout.layout_login_item, parent, false);
                }
                ImageView imageView = convertView.findViewById(com.kakao.usermgmt.R.id.login_method_icon);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setImageDrawable(getResources().getDrawable(authKakaoAuthItems[position].getIcon(), getContext().getTheme()));
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(authKakaoAuthItems[position].getIcon()));
                }
                TextView textView = convertView.findViewById(com.kakao.usermgmt.R.id.login_method_text);
                textView.setText(authKakaoAuthItems[position].getTextId());
                return convertView;
            }
        };
    }

    /**
     * 실제로 유저에게 보여질 dialog 객체를 생성한다.
     * @param authKakaoAuthItems 가능한 AuthType들의 정보를 담고 있는 KakaoAuthItem array
     * @param adapter Dialog의 list view에 쓰일 adapter
     * @return 로그인 방법들을 팝업으로 보여줄 dialog
     */
    private Dialog createLoginDialog(final KakaoAuthItem[] authKakaoAuthItems, final ListAdapter adapter) {
        final Dialog dialog = new Dialog(this, com.kakao.usermgmt.R.style.LoginDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(com.kakao.usermgmt.R.layout.layout_login_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setGravity(Gravity.CENTER);
        }

//        TextView textView = (TextView) dialog.findViewById(R.id.login_title_text);
//        Typeface customFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/KakaoOTFRegular.otf");
//        if (customFont != null) {
//            textView.setTypeface(customFont);
//        }

        ListView listView = dialog.findViewById(com.kakao.usermgmt.R.id.login_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final AuthType authType = authKakaoAuthItems[position].getAuthType();
                if (authType != null) {
                    openSession(authType);
                }
                dialog.dismiss();
            }
        });

        Button closeButton = dialog.findViewById(com.kakao.usermgmt.R.id.login_close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public void openSession(final AuthType authType) {
        Session.getCurrentSession().open(authType, this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_login);

        btnLogin = findViewById(R.id.btn_login);
        btnLogout = findViewById(R.id.btn_logout);
        btnProfile = findViewById(R.id.btn_profile);

        btnLogin.setOnClickListener(KakaoLoginActivity.this);
        btnLogout.setOnClickListener(this);
        btnProfile.setOnClickListener(this);

        callback = new SessionCallback();

        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // Session.getCurrentSession().open(AuthType.KAKAO_TALK, KakaoLoginActivity.this);
                // btnKakaoLogin.callOnClick();
                final List<AuthType> authTypes = getAuthTypes();
                if (authTypes.size() == 1) {
                    Session.getCurrentSession().open(authTypes.get(0), this);
                } else {
                    final KakaoAuthItem[] authKakaoAuthItems = createAuthItemArray(authTypes);
                    ListAdapter adapter = createLoginAdapter(authKakaoAuthItems);
                    final Dialog dialog = createLoginDialog(authKakaoAuthItems, adapter);
                    dialog.show();
                }
                break;
            case R.id.btn_logout:
                Log.d(TAG, "btnLogout clicked");
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
                        Toast.makeText(KakaoLoginActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCompleteLogout() {
                        Toast.makeText(KakaoLoginActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_profile:
                requestMe();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.d(TAG, "onSessionOpen");
            if (Session.getCurrentSession().getTokenInfo() != null) {
                Toast.makeText(
                        KakaoLoginActivity.this,
                        "Logged in!\ntoken: " + Session.getCurrentSession().getAccessToken(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Log.d(TAG, "onSessionOpenFailed");
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }

    private void requestMe() {
        Log.d(TAG, "requestMe");
        UserManagement.getInstance().requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Log.e(TAG, message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e(TAG, "seesionClosed");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Toast.makeText(KakaoLoginActivity.this, "userProfile!\n" + userProfile.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNotSignedUp() {
                Log.e(TAG, "NotSignedUp");
            }
        });
    }
}
