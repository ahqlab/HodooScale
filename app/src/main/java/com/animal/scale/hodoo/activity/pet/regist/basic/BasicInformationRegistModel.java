package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.HttpUtill;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class BasicInformationRegistModel extends CommonModel {

    public Context context;

    private BottomDialog builder;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    ;

    public View onClickOpenBottomDlg() {
        return null;
    }

    public void callDatePickerDialog(final EditText getDate) {

    }

    public void registBasicInfo(final String requestUrl, final PetBasicInfo info, final CircleImageView profile, final DomainCallBackListner<Pet> domainCallBackListner) {
        new AsyncTaskCancelTimerTask(new AsyncTask<Void, String, Pet>() {
            @Override
            protected Pet doInBackground(Void... voids) {
                return HttpUtill.HttpFileRegist(requestUrl, mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), info, profile);
            }

            @Override
            protected void onPostExecute(Pet pet) {
                domainCallBackListner.doPostExecute(pet);
            }

            @Override
            protected void onPreExecute() {
                domainCallBackListner.doPreExecute();
            }


        }.execute(), limitedTime, interval, true).start();
    }

    public void updateBasicInfo(final String requestUrl, final PetBasicInfo info, final CircleImageView profile, final BasicInfoUpdateListner basicInfoUpdateListner) {
        new AsyncTaskCancelTimerTask(new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                return HttpUtill.HttpFileUpdate(requestUrl, info, profile);
            }

            @Override
            protected void onPreExecute() {
                basicInfoUpdateListner.doPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                basicInfoUpdateListner.doPostExecute();
            }
        }.execute(), limitedTime, interval, true).start();
    }

    public void getPetBasicInformation(int petIdx, final DomainCallBackListner<PetBasicInfo> domainCallBackListner) {
        Call<PetBasicInfo> call = NetRetrofit.getInstance().getPetService().getBasicInformation(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE), petIdx);
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


    public interface BasicInfoRegistListner {
        void doPostExecute(Pet pet);

        void doPreExecute();
    }

    public interface BasicInfoUpdateListner {
        void doPostExecute();

        void doPreExecute();
    }

    public interface PetBasicInformationResultListner {
        void doPostExecute(PetBasicInfo basicInfo);

        void doPreExecute();
    }
}