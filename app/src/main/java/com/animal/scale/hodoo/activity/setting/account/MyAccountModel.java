package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.login.LoginModel;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MyAccountModel extends CommonModel{

    public SharedPrefManager mSharedPrefManager;

    Context context;

    public void initLoadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public List<SettingMenu> getSettingList() {
        final List<SettingMenu> settingMenus = new ArrayList<SettingMenu>();
//        settingMenus.add(new SettingMenu(context.getString(R.string.log_out)));
        settingMenus.add(new SettingMenu(context.getString(R.string.change_user_account_info)));
        settingMenus.add(new SettingMenu(context.getString(R.string.withdraw)));
        return settingMenus;
    }

    public void logout() {
        mSharedPrefManager.removeAllPreferences();
    }
    public void saveFCMToken (User user, final LoginModel.DomainCallBackListner<Integer> callback) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().saveFCMToken(user);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                callback.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();

    }
    public void initUserData(final CommonModel.DomainCallBackListner<User> domainCallBackListner) {
        Call<User> call = NetRetrofit.getInstance().getUserService().get(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<User>() {
            @Override
            protected void doPostExecute(User user) {
                domainCallBackListner.doPostExecute(user);
            }
            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void withdraw( int type, final CommonModel.DomainCallBackListner<Integer> callback ) {
        int idx = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        if ( idx > 0 ) {
            Call<Integer> call = NetRetrofit.getInstance().getUserService().withdraw(idx, type);
            new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
                @Override
                protected void doPostExecute(Integer result) {
                    callback.doPostExecute(result);
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
    public void checkGroupCount( final CommonModel.DomainCallBackListner<Integer> callback ) {
        int idx = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        Call<Integer> call = NetRetrofit.getInstance().getUserService().checkGroupCount(idx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                callback.doPostExecute(integer);
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
