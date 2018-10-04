package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

public class UserAccountModel {

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initUserData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getUserData(final UserAccountModel.asyncTaskListner asyncTaskListner) {
     /*  Call<List<User>> call = NetRetrofit.getInstance().getUserService().getGroupMemner(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_ID));
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
        }.execute(call);*/
    }

    public void addRegistBtn(List<User> data) {
        User info = new User();
        info.setSex("새 구성원");
        data.add(0, info);
    }

    public interface asyncTaskListner {
        void doPostExecute(List<User> data);
        void doPreExecute();
    }
}
