package com.animal.scale.hodoo;

import android.app.Application;
import android.content.Context;

import com.animal.scale.hodoo.activity.user.login.GlobalApplication;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

/**
 * Created by SongSeokwoo on 2019-05-08.
 */
public class HodooApplication extends Application {
    private static Context context;
    private boolean experienceState = false;
    private boolean snsLoginState = false;

    public boolean isExperienceState() {
        return experienceState;
    }

    public void setExperienceState(boolean experienceState) {
        this.experienceState = experienceState;
    }
    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return context;
                }
            };
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        KakaoSDK.init(new HodooApplication.KakaoSDKAdapter());
    }

    /**
     * 어플에 SNS로 로그인했는지 확인한다.
     * @return
     */
    public boolean isSnsLoginState() {
        return snsLoginState;
    }

    /**
     * 어플에 SNS 로그인했는지 확인 하는 상태를 변경한다.
     * @param snsLoginState
     */
    public void setSnsLoginState(boolean snsLoginState) {
        this.snsLoginState = snsLoginState;
    }
}
