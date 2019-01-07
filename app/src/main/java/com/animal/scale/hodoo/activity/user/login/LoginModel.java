package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Feed;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.ValidationUtil;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
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
        new AbstractAsyncTask<CommonResponce<User>>() {
            @Override
            protected void doPostExecute(CommonResponce<User> resultMessageGroup) {
                domainCallBackListner.doPostExecute(resultMessageGroup);
            }
            @Override
            protected void doPreExecute() {
                domainCallBackListner.doPreExecute();
            }
        }.execute(call);
    }

    public void saveUserSharedValue(User user){
        mSharedPrefManager.putIntExtra(SharedPrefVariable.USER_UNIQUE_ID, user.getUserIdx());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.USER_EMAIL, user.getEmail());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.GROUP_CODE, user.getGroupCode());
        mSharedPrefManager.putStringExtra(SharedPrefVariable.USER_PASSWORD, user.getPassword());
    }

    public void confirmDeviceRegistration(final DeviceRegistrationListener deviceRegistrationListener) {
        Call<List<Device>> call = NetRetrofit.getInstance().getDeviceService().getMyDeviceList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AbstractAsyncTaskOfList<Device>() {
            @Override
            protected void doPostExecute(List<Device> devices) {
                deviceRegistrationListener.doPostExecute(devices);
            }
            @Override
            protected void doPreExecute() {
                deviceRegistrationListener.doPreExecute();
            }
        }.execute(call);
    }

    public void confirmPetRegistration(final PetRegistrationListener petRegistrationListener) {
        Call<List<Pet>> call = NetRetrofit.getInstance().getPetService().getMyPetList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AbstractAsyncTaskOfList<Pet>() {
            @Override
            protected void doPostExecute(List<Pet> pets) {
                petRegistrationListener.doPostExecute(pets);
            }
            @Override
            protected void doPreExecute() {
                petRegistrationListener.doPreExecute();
            }
        }.execute(call);
    }
    public void saveFCMToken (User user, final LoginModel.DomainCallBackListner<Integer> callback) {
        Call<Integer> call = NetRetrofit.getInstance().getUserService().saveFCMToken(user);
        new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer integer) {
                callback.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }


    public interface LoginResultListener {
        void doPostExecute(User user);
        void doPreExecute();
    }

    public interface RegistCheckListener {
        void doPostExecute();
        void doPreExecute();
    }
    public interface DeviceRegistrationListener {
        void doPostExecute(List<Device> devices);
        void doPreExecute();
    }
    public interface PetRegistrationListener {
        void doPostExecute(List<Pet> pets);
        void doPreExecute();
    }

}
