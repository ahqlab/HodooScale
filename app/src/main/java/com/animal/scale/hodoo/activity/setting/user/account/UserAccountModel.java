package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class UserAccountModel extends CommonModel {

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initUserData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getUserData(final DomainListCallBackListner<User> domainCallBackListner) {
        Call<List<User>> call = NetRetrofit.getInstance().getUserService().getGroupMemner(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<User>() {
            @Override
            protected void doPostExecute(List<User> data) {
                if (data.size() > 0) {
                    domainCallBackListner.doPostExecute(data);
                }
            }
            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();

    }

    public void addRegistBtn(List<User> data) {
        User info = new User();
        info.setNickname("+");
        info.setSex(context.getString(R.string.istyle_new_group_user));
        data.add(0, info);
    }

    public int getUserIdx() {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
    }

    public void withdrawGroup(int from, final CommonModel.DomainCallBackListner<Integer> callback) {
        int to = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        Call<Integer> call = NetRetrofit.getInstance().getUserService().withdrawGroup(to, from);
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

    public int getAccessType() {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_GROUP_ACCESS_TYPE);
    }

    public interface asyncTaskListner {
        void doPostExecute(List<User> data);

        void doPreExecute();
    }
}
