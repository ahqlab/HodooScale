package com.animal.scale.hodoo.activity.pet.regist.weight;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class WeightCheckModel extends CommonModel {

    public Context context;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context){
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    };

    public void getWeightInformation(int petIdx, final ResultListner resultListner) {
        Call<PetWeightInfo> call = NetRetrofit.getInstance().getPetWeightInfoService().getPetWeightInformation(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), petIdx);
        new AbstractAsyncTask<PetWeightInfo>() {
            @Override
            protected void doPostExecute(PetWeightInfo petWeightInfo) {
                Log.e("HJLEE", "model  doPostExecute ");
                resultListner.doPostExecute(petWeightInfo);
            }

            @Override
            protected void doPreExecute() {
                resultListner.doPreExecute();
            }
        }.execute(call);
    }

    public void deleteWeightInformation(int petIdx, int id, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetWeightInfoService().delete(petIdx, id);
        new AbstractAsyncTask<Integer>(){

            @Override
            protected void doPostExecute(Integer result) {
                domainCallBackListner.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }

    public void registWeightInformation(int petIdx, PetWeightInfo petWeightInfo, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetWeightInfoService().regist(petIdx, petWeightInfo);
        new AbstractAsyncTask<Integer>(){

            @Override
            protected void doPostExecute(Integer integer) {
                domainCallBackListner.doPostExecute(integer);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }


    public interface ResultListner {
        void doPostExecute(PetWeightInfo petWeightInfo);
        void doPreExecute();
    }
}
