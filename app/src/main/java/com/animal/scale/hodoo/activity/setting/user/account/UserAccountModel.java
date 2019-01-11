package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class UserAccountModel {

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initUserData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getUserData(final UserAccountModel.asyncTaskListner asyncTaskListner) {
       Call<List<User>> call = NetRetrofit.getInstance().getUserService().getGroupMemner(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AbstractAsyncTaskOfList<User>() {
            @Override
            protected void doPostExecute(List<User> data) {
                if(data.size() > 0){
                    asyncTaskListner.doPostExecute(data);
                }
            }
            @Override
            protected void doPreExecute() {
                asyncTaskListner.doPreExecute();
            }
        }.execute(call);
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
    public void withdrawGroup (int from, final CommonModel.DomainCallBackListner<Integer> callback) {
        int to = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        Call<Integer> call = NetRetrofit.getInstance().getUserService().withdrawGroup(to, from);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                callback.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
    public int getAccessType() {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_GROUP_ACCESS_TYPE);
    }

    public interface asyncTaskListner {
        void doPostExecute(List<User> data);
        void doPreExecute();
    }
}
