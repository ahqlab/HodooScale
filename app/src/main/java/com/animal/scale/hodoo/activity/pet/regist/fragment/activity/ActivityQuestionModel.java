package com.animal.scale.hodoo.activity.pet.regist.fragment.activity;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;
import com.animal.scale.hodoo.service.NetRetrofit;

import retrofit2.Call;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
public class ActivityQuestionModel extends CommonModel {
    private Context context;
    private SharedPrefManager mSharedPrefManager;
    public void loadData (Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    /**
     * 펫의 활동 정보를 등록한다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param callback 서버에서 가져 온 후 리턴시키는 콜백함수
     * @return
    */
    public void registPetUserSelectQuestion (int petIdx, final CommonModel.ObjectCallBackListner<CommonResponce<PetUserSelectionQuestion>> callback) {
        Call<CommonResponce<PetUserSelectionQuestion>> call = NetRetrofit.getInstance().getPetUserSelectionQuestionService().getPetUserSelectQuestion(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), petIdx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<PetUserSelectionQuestion>>() {
            @Override
            protected void doPostExecute(CommonResponce<PetUserSelectionQuestion> petUserSelectionQuestionCommonResponce) {
                callback.doPostExecute(petUserSelectionQuestionCommonResponce);
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
