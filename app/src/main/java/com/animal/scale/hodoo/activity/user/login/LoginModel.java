package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Groups;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.ValidationUtil;

import retrofit2.Call;

public class LoginModel {

    User user;

    Groups groups;

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initUserData(User user, Context context) {
        this.user = user;
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public boolean editTextisEmptyCheck(String message){
        return ValidationUtil.isEmpty(message);
    }
    public boolean editTextisValidEmail(String message){
        return ValidationUtil.isValidEmail(message);
    }

    public void sendServer(final LoginResultListener loginResultListener) {
        Call<User> call = NetRetrofit.getInstance().getUserService().login(user);
        new AbstractAsyncTask<User>() {
            @Override
            protected void doPostExecute(User user) {
                loginResultListener.doPostExecute(user);

            }
            @Override
            protected void doPreExecute() {
                loginResultListener.doPreExecute();
            }


        }.execute(call);
    }

    public void saveUserSharedValue(User user){
        mSharedPrefManager.putIntExtra(SharedPrefVariable.USER_UNIQUE_ID, user.getId());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.USER_ID, user.getEmail());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.GEOUP_ID, user.getGroupId());
    }

    public void isRegistPetCheck(final RegistCheckListener registCheckListener) {
        Call<Groups> call = NetRetrofit.getInstance().getGroupsService().get(new Groups(mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID), mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID)));
        new AbstractAsyncTask<Groups>() {
            @Override
            protected void doPostExecute(Groups groups) {
                registCheckListener.doPostExecute(groups);
            }

            @Override
            protected void doPreExecute() {
                registCheckListener.doPreExecute();
            }
        }.execute(call);
    }

    public interface LoginResultListener {
        void doPostExecute(User user);
        void doPreExecute();
    }

    public interface RegistCheckListener {
        void doPostExecute(Groups groups);
        void doPreExecute();
    }

}
