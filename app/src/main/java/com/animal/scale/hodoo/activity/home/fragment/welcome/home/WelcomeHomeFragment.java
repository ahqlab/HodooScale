package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.device.regist.DeviceRegistActivity;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.activity.user.signup.SignUpActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.FragmentWelcomeHomeBinding;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.message.ResultMessage;
import com.animal.scale.hodoo.util.VIewUtil;
import com.kakao.auth.AccessTokenCallback;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WelcomeHomeFragment extends Fragment implements WelcomeHomeIn.View {

    private FragmentWelcomeHomeBinding binding;
    private static final String CONNECTION_CONFIRM_CLIENT_URL = "http://clients3.google.com/generate_204";
    Intent intent;
    // fot kakao
    private SessionCallback callback;

    WelcomeHomeIn.Presenter presenter;

    SharedPrefManager sharedPrefManager;

    /**
     * sns 회원가입 결과
     * @param commonResponce
     */
    @Override
    public void setSnsLoginResult(CommonResponce<User> commonResponce) {
        if(commonResponce.getDomain() != null){
            ((HodooApplication) getActivity().getApplication()).setSnsLoginState(true);
            //로그인을 진행한다.
            presenter.doLogin(commonResponce.getDomain());
        }else{
            Toast.makeText(getActivity(), "서버와의 연결이 원활하지 않습니다. 네트워크 상태를 확인하세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void goInvitationActivity() {
    }

    @Override
    public void showPopup(String message) {
    }

    @Override
    public void setBtnState(boolean b) {

    }

    @Override
    public void setProgress(boolean b) {

    }

    @Override
    public void selectTheNextAction() {
        sharedPrefManager.putIntExtra(SharedPrefVariable.AUTO_LOGIN, 0);
        Intent intent = new Intent(getActivity(), DeviceRegistActivity.class);
        intent.putExtra(HodooConstant.LOGIN_PET_REGIST, true);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        //finish();
    }

    @Override
    public void setServerError() {

    }


    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        getActivity().finish();
    }


    private static class KakaoAuthItem {
        final int textId;
        public final int icon;
        final int contentDescId;
        final AuthType authType;

        KakaoAuthItem(final int textId, final Integer icon, final int contentDescId, final AuthType authType) {
            this.textId = textId;
            this.icon = icon;
            this.contentDescId = contentDescId;
            this.authType = authType;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_home, container, false);
        binding.setFragment(this);
        binding.progressLoader.setVisibility(View.GONE);
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        presenter = new WelcomeHomePresenter(this);
        presenter.loadData(getContext());

        callback = new SessionCallback();

        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        return binding.getRoot();
    }

    public static WelcomeHomeFragment newInstance() {
        return new WelcomeHomeFragment();
    }

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
        if (availableAuthTypes.size() == 0) {
            availableAuthTypes.add(AuthType.KAKAO_ACCOUNT);
        }

        return availableAuthTypes;
    }

    /**
     * 가능한 로그인 개체들을 보여준다.
     * @param authTypes
     * @return
     */
    private KakaoAuthItem[] createAuthItemArray(final List<AuthType> authTypes) {
        final List<KakaoAuthItem> kakaoAuthItemList = new ArrayList<KakaoAuthItem>();
        if (authTypes.contains(AuthType.KAKAO_TALK)) {
            kakaoAuthItemList.add(new KakaoAuthItem(com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account, com.kakao.usermgmt.R.drawable.talk, com.kakao.usermgmt.R.string.com_kakao_kakaotalk_account_tts, AuthType.KAKAO_TALK));
        }
        if (authTypes.contains(AuthType.KAKAO_STORY)) {
            kakaoAuthItemList.add(new KakaoAuthItem(com.kakao.usermgmt.R.string.com_kakao_kakaostory_account, com.kakao.usermgmt.R.drawable.story, com.kakao.usermgmt.R.string.com_kakao_kakaostory_account_tts, AuthType.KAKAO_STORY));
        }
        if (authTypes.contains(AuthType.KAKAO_ACCOUNT)) {
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
                getActivity(),
                android.R.layout.select_dialog_item,
                android.R.id.text1, authKakaoAuthItems) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getContext()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(com.kakao.usermgmt.R.layout.layout_login_item, parent, false);
                }
                ImageView imageView = convertView.findViewById(com.kakao.usermgmt.R.id.login_method_icon);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView.setImageDrawable(getResources().getDrawable(authKakaoAuthItems[position].icon, getContext().getTheme()));
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(authKakaoAuthItems[position].icon));
                }
                TextView textView = convertView.findViewById(com.kakao.usermgmt.R.id.login_method_text);
                textView.setText(authKakaoAuthItems[position].textId);
                return convertView;
            }
        };
    }

    /**
     * 실제로 유저에게 보여질 dialog 객체를 생성한다.
     *
     * @param authKakaoAuthItems 가능한 AuthType들의 정보를 담고 있는 KakaoAuthItem array
     * @param adapter            Dialog의 list view에 쓰일 adapter
     * @return 로그인 방법들을 팝업으로 보여줄 dialog
     */
    private Dialog createLoginDialog(final KakaoAuthItem[] authKakaoAuthItems, final ListAdapter adapter) {
        final Dialog dialog = new Dialog(getActivity(), com.kakao.usermgmt.R.style.LoginDialog);
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
                final AuthType authType = authKakaoAuthItems[position].authType;
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
        Session.getCurrentSession().open(authType, getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    /**
     * 버튼에 대한 이벤트 처리를 한다.
     *
     * //@param View v    클릭한 대상 뷰
     * @return
    */
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.signup_btn:
                intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
                //onClickUnlink();
                break;
            case R.id.login_btn:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_kakao_login:
                final List<AuthType> authTypes = getAuthTypes();
                if (authTypes.size() == 1) {
                    Session.getCurrentSession().open(authTypes.get(0), getActivity());
                } else {
                    final KakaoAuthItem[] authKakaoAuthItems = createAuthItemArray(authTypes);
                    ListAdapter adapter = createLoginAdapter(authKakaoAuthItems);
                    final Dialog dialog = createLoginDialog(authKakaoAuthItems, adapter);
                    dialog.show();
                }
                break;
//            case R.id.experience_btn:
//                intent = new Intent(getContext(), HomeActivity.class);
//                intent.putExtra(SharedPrefVariable.EXPERIENCE_KEY, true);
//                startActivity(intent);
//                break;
        }
    }

    /**
     * 카카오 세션정보 콜벡 리스너
     */
    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            Log.d("HJLEE", "onSessionOpen");
            if (Session.getCurrentSession().getTokenInfo() != null) {
                Toast.makeText(getActivity(), "Logged in!\ntoken: " + Session.getCurrentSession().getAccessToken(), Toast.LENGTH_SHORT).show();
                //로그인 되어있음.
                requestMe();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    /**
     * 카카오 정보 보여주는 함수
     */
    public void requestMe() {
        List<String> keys = new ArrayList<>();
        keys.add("properties.nickname");
        keys.add("properties.profile_image");
        keys.add("kakao_account.email");
        keys.add("kakao_account.birthday");
        keys.add("kakao_account.gender");
        keys.add("kakao_account.age_range");

        UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {

            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                //세션이 닫혀 실패한 경우로 에러 결과를 받습니다. 재로그인 / 토큰발급이 필요합니다.
                //redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                if (result.getKakaoAccount().getEmail() != null) {

                    User user = new User();
                    OptionalBoolean isEmail = result.getKakaoAccount().hasEmail();
                    OptionalBoolean isGender = result.getKakaoAccount().hasGender();
                    if ( isEmail.getBoolean() )
                        user.setEmail(result.getKakaoAccount().getEmail());
                    if ( isGender.getBoolean() )
                        if ( result.getKakaoAccount().getGender() != null )
                            user.setSex(result.getKakaoAccount().getGender().getValue().toUpperCase());

                    user.setNickname(result.getNickname());
                    user.setCountry(VIewUtil.getLocationCode(getActivity()));

                    user.setSnsToken(Session.getCurrentSession().getAccessToken());
                    user.setLoginType(1);
                    user.setSnsId(String.valueOf(result.getId()));
                    user.setUserCode(1);
                    Toast.makeText(getActivity(), "Logged in!\n정보: " + user.toString(), Toast.LENGTH_SHORT).show();
                    //회원가입을 진행한다. 만약 회원가입이 되어있고, sns Token 이 변경되었다면, 업데이트 시킨다.
                    presenter.doSnsLogin(user);
                } else {
                    handleScopeError(result);
                }
            }
        });
    }


    private void handleScopeError(final MeV2Response result) {
        List<String> neededScopes = new ArrayList<>();
        if (result.getKakaoAccount().needsScopeAccountEmail()) {
            neededScopes.add("account_email");
        }
        if (result.getKakaoAccount().needsScopeGender()) {
            neededScopes.add("gender");
        }
    /*    if (account.needsScopeGender()) {
            neededScopes.add("birthday");
        }*/
        Session.getCurrentSession().updateScopes(this, neededScopes, new
                AccessTokenCallback() {
                    @Override
                    public void onAccessTokenReceived(AccessToken accessToken) {
                        // 유저에게 성공적으로 동의를 받음. 토큰을 재발급 받게 됨.
                        //회원가입 성공
                        //서버로 데이터를 보낸다.
                       /* User user = new User();
                        user.setEmail( result.getKakaoAccount().getEmail() );
                        user.setNickname( result.getNickname() );
                        user.setCountry(VIewUtil.getLocationCode(getActivity()));
                        //user.setSex(result.getKakaoAccount().getGender().toString());
                        user.setSnsToken(Session.getCurrentSession().getAccessToken());
                        Log.e("HJLEE", "user : " + user.toString());*/
                        Log.e("HJLEE", "유저에게 성공적으로 동의를 받음. 토큰을 재발급 받게 됨.");
                        Log.e("HJLEE", "result.getKakaoAccount().getEmail()  : " + result.getKakaoAccount().getEmail());
                        requestMe();

                    }

                    @Override
                    public void onAccessTokenFailure(ErrorResult errorResult) {
                        // 동의 얻기 실패
                        Log.e("HJLEE", "동의 얻기 실패");
                    }
                });
    }

    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(getActivity())
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                                        ((HodooApplication) getActivity().getApplication()).setSnsLoginState(false);
                                        Log.e("HJLEE", "onSuccess : " + userId);
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }

}
