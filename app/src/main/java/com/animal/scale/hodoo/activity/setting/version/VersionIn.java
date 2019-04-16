package com.animal.scale.hodoo.activity.setting.version;

import android.content.Context;

import com.animal.scale.hodoo.domain.AppVersion;
import com.animal.scale.hodoo.domain.CommonResponce;

public interface VersionIn {

    interface View{

        void setServerAppVersion(CommonResponce<AppVersion> appVersion);

        void goToMarket();
    }

    interface Presenter{

        void getServerAppVersion();

        String getVersionInfo(Context context);
    }
}
