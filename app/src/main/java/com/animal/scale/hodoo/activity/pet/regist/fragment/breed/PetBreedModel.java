package com.animal.scale.hodoo.activity.pet.regist.fragment.breed;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

/**
 * Created by SongSeokwoo on 2019-05-14.
 */
public class PetBreedModel extends CommonModel {
    public Context context;
    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    /**
     * 펫의 전체 품종을 가져온다
     *
     * @param location   다국어를 위한 지역값
     * @param typeIdx    펫의 타입 인덱스 값
     * @param callback   콜백함수
     * @return
     * @description     펫의 타입에 따라 가져오는 품종이 다르다.
    */
    public void getAllPetBreed(String location, int typeIdx, final ObjectCallBackListner<CommonResponce<List<PetBreed>>> callback) {
        final Call<CommonResponce<List<PetBreed>>> call = NetRetrofit.getInstance().getPetService().getAllBreed( location, typeIdx );
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<List<PetBreed>>>() {

            @Override
            protected void doPostExecute(CommonResponce<List<PetBreed>> responce) {
                callback.doPostExecute(responce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
}
