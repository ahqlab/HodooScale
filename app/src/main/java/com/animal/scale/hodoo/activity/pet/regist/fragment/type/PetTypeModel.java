package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.PetBasicInfo;
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
    /**
     * 서버에 등록된 펫의 타입을 가져온다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param callback 콜백함수
     * @return
    */
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
    public void getPetBasicInformation(String location, int petIdx, final DomainCallBackListner<PetBasicInfo> domainCallBackListner) {
        Call<PetBasicInfo> call = NetRetrofit.getInstance().getPetService().getBasicInformation(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), petIdx, location);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<PetBasicInfo>() {

            @Override
            protected void doPostExecute(PetBasicInfo basicInfo) {
                domainCallBackListner.doPostExecute(basicInfo);
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
}
