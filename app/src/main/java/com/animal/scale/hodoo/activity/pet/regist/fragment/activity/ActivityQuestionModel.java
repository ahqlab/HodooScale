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
