package com.animal.scale.hodoo.activity.pet.regist.fragment.physique;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
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

    /**
     * 등록되어있는 단위값을 가져온다.
     *
     * @param
     * @return
     * @description     등록되어 있는 단위가 없을경우 0을 리턴한다.
    */
    public int getUnitIdx() {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.UNIT_STR);
    }

    /**
     * 등록되어 있는 피지컬 값을 가져온다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param domainCallBackListner   콜백함수
     * @return
     * @description
    */
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
    /**
     * 서버 있는 피지컬 값을 삭제한다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param id       피지컬의 인덱스 값
     * @param domainCallBackListner   콜백함수
     * @return
     * @description
    */
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

    /**
     * 펫의 피지컬을 서버에 등록한다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param domain   피지컬 도메인
     * @param domainCallBackListner   콜백함수
     * @return
     * @description
    */
    public void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain, final CommonDomainCallBackListner<Integer> domainCallBackListner) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getPetPhysicalInfoService().regist(petIdx, mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), domain);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>(){

            @Override
            protected void doPostExecute(CommonResponce<Integer> commonResponce) {
                domainCallBackListner.doPostExecute(commonResponce);
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
