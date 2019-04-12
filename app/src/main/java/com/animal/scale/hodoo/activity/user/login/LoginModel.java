package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.ValidationUtil;

import java.util.List;

import retrofit2.Call;

public class LoginModel extends CommonModel {

    User user;

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initUserData(User user, Context context) {
        this.user = user;
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void initUserData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public boolean editTextisEmptyCheck(String message){
        return ValidationUtil.isEmpty(message);
    }
    public boolean editTextisValidEmail(String message){
        return ValidationUtil.isValidEmail(message);
    }

    public void sendServer(User user, final DomainCallBackListner<CommonResponce<User>> domainCallBackListner) {
        Call<CommonResponce<User>> call = NetRetrofit.getInstance().getUserService().login(user);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<User>>() {
            @Override
            protected void doPostExecute(CommonResponce<User> resultMessageGroup) {
                domainCallBackListner.doPostExecute(resultMessageGroup);
            }
            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {
                domainCallBackListner.doCancelled();
            }
        }.execute(call), limitedTime, interval, true).start();


    }

    public void saveUserSharedValue(User user){
        removeSharedValue();
        mSharedPrefManager.putIntExtra(SharedPrefVariable.USER_UNIQUE_ID, user.getUserIdx());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.USER_EMAIL, user.getEmail());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.GROUP_CODE, user.getGroupCode());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.USER_PASSWORD, user.getPassword());
        mSharedPrefManager.putIntExtra(SharedPrefVariable.USER_GROUP_ACCESS_TYPE, user.getAccessType());
    }

    public void removeSharedValue(){
        mSharedPrefManager.removeAllPreferences();
    }


    public void confirmDeviceRegistration(final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getDeviceService().getMyDeviceListResult(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                domainCallBackListner.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void confirmPetRegistration(final DomainListCallBackListner<Pet> domainCallBackListner) {
        Call<List<Pet>> call = NetRetrofit.getInstance().getPetService().getMyPetList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<Pet>() {
            @Override
            protected void doPostExecute(List<Pet> pets) {
                domainCallBackListner.doPostExecute(pets);
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
    public void confirmPetRegistrationResult(final DomainCallBackListner<CommonResponce<Integer>> domainCallBackListner) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getPetService().getExistMyPet(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>() {

            @Override
            protected void doPostExecute(CommonResponce<Integer> commonResponce) {
                domainCallBackListner.doPostExecute(commonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();

        /*Call<Integer []> call = NetRetrofit.getInstance().getPetService().getMyPetResult(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer []>() {
            @Override
            protected void doPostExecute(Integer[] pets) {
                domainCallBackListner.doPostExecute(pets);
            }
            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();*/
    }

    public void saveFCMToken (User user, final DomainCallBackListner<Integer> callback) {
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

    public void saveAutoLogin() {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.AUTO_LOGIN, HodooConstant.AUTO_LOGIN_SUCCESS);
    }
    public void removeAutoLogin() {
        mSharedPrefManager.removePreference(SharedPrefVariable.AUTO_LOGIN);
    }

    public User getUser () {
        User user = new User();
        user.setEmail(mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_EMAIL));
        user.setPassword(mSharedPrefManager.getStringExtra(SharedPrefVariable.USER_PASSWORD));

        return user;
    }
    public int getAutoLoginState() {
        return mSharedPrefManager.getIntExtra( SharedPrefVariable.AUTO_LOGIN );
    }

}
