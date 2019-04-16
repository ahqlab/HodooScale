package com.animal.scale.hodoo.activity.setting.version;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.AppVersion;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class VersionModel extends CommonModel {

    public void getServerAppVersion(final DomainCallBackListner<CommonResponce<AppVersion>> domainCallBackListner) {

        Call<CommonResponce<AppVersion>> call = NetRetrofit.getInstance().getAppVersionService().appVersionImport();
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<AppVersion>>() {
            @Override
            protected void doPostExecute(CommonResponce<AppVersion> appVersion) {
                domainCallBackListner.doPostExecute(appVersion);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
