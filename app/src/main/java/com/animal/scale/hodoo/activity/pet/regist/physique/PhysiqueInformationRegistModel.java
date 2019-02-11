package com.animal.scale.hodoo.activity.pet.regist.physique;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

public class PhysiqueInformationRegistModel extends CommonModel {

    public Context context;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context){
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getPhysiqueInformation(int petIdx, final DomainCallBackListner<PetPhysicalInfo> domainCallBackListner) {
        Call<PetPhysicalInfo> call = NetRetrofit.getInstance().getPetPhysicalInfoService().getPhysicalIformation(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), petIdx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<PetPhysicalInfo>(){
            @Override
            protected void doPostExecute(PetPhysicalInfo petPhysicalInfo) {
                domainCallBackListner.doPostExecute(petPhysicalInfo);
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

    public void deletePhysiqueInformation(int petIdx, int id, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetPhysicalInfoService().delete(petIdx, id);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>(){

            @Override
            protected void doPostExecute(Integer result) {
                domainCallBackListner.doPostExecute(result);
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

    public void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetPhysicalInfoService().regist(petIdx, domain);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>(){

            @Override
            protected void doPostExecute(Integer result) {
                domainCallBackListner.doPostExecute(result);
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

    public interface getPhysiqueInformationResultListner {
        void doPostExecute(PetPhysicalInfo petPhysicalInfo);
        void doPreExecute();
    }

    public interface deleteInfoResultListner {
        void doPostExecute(Integer result);
        void doPreExecute();
    }

    public interface registResultListner {
        void doPostExecute(Integer result);
        void doPreExecute();
    }
}
