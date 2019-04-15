package com.animal.scale.hodoo.activity.setting.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.user.account.UserAccountIn;
import com.animal.scale.hodoo.activity.setting.user.account.UserAccountModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.AppVersion;
import com.animal.scale.hodoo.domain.CommonResponce;

public class VersionPresenter implements VersionIn.Presenter{

    VersionIn.View view;

    VersionModel model;

    public VersionPresenter(VersionIn.View view) {
        this.view = view;
        this.model = new VersionModel();
    }

    @Override
    public void getServerAppVersion() {
        model.getServerAppVersion(new CommonModel.DomainCallBackListner<CommonResponce<AppVersion>>() {
            @Override
            public void doPostExecute(CommonResponce<AppVersion> appVersion) {
                view.setServerAppVersion(appVersion);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public String getVersionInfo(Context context) {
        String version = null;
        try {
            PackageInfo i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = i.versionName;
        } catch(PackageManager.NameNotFoundException e) {

        }
        return version;
    }

    public void doUpdate(String serverVersion, String appVersion){
        if(serverVersion.equals(appVersion)){
            //Log.e("HJLEE", "appVersion : " + appVersion + "serverVersion : " + serverVersion + " 과 동일합니다. 아무일도 안합니다.");
        }else{
            view.goToMarket();
            //Log.e("HJLEE", "appVersion : " + appVersion + "serverVersion : " + serverVersion + " 과 다릅니다. 마켓으로 이동합니다.");
        }
    }
}
