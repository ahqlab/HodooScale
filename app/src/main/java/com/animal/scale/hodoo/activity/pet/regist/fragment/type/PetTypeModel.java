package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

/**
 * Created by SongSeokwoo on 2019-04-09.
 */
public class PetTypeModel extends CommonModel {
    private Context context;
    private SharedPrefManager mSharedPrefManager;

    public void loadData(Context context){
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public void getPetType( int petIdx, final DomainCallBackListner<Integer> callback ) {
        Call<Integer> call = NetRetrofit.getInstance().getPetService().getPetType(petIdx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>(){

            @Override
            protected void doPostExecute(Integer type) {
                callback.doPostExecute(type);
            }

            @Override
            protected void doPreExecute() {
                callback.doPreExecute();
            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
