package com.animal.scale.hodoo.activity.pet.regist.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistModel;
import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.HttpUtill;
import com.github.javiersantos.bottomdialogs.BottomDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public class PetRegistModel extends CommonModel {

    public Context context;

    private BottomDialog builder;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
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

    public void updateBasicInfo(final String requestUrl, final PetBasicInfo info, final CircleImageView profile, final BasicInformationRegistModel.BasicInfoUpdateListner basicInfoUpdateListner) {
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

    public void getAllPetBreed(String location, int typeIdx, final DomainListCallBackListner<PetBreed> callback) {
        final Call<CommonResponce<List<PetBreed>>> call = NetRetrofit.getInstance().getPetService().getAllBreed( location, typeIdx );
        new AsyncTaskCancelTimerTask(new AbstractAsyncTaskOfList<PetBreed>() {
            @Override
            protected void doPostExecute(List<PetBreed> d) {
                callback.doPostExecute(d);
            }

            @Override
            protected void doPreExecute() {

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


    public void deleteDiseaseformation(int petIdx, int diseaseIdx,  final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetChronicDiseaseService().delete(petIdx, diseaseIdx);
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


    public void registDiseaseformation(PetChronicDisease domain, int petIdx, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetChronicDiseaseService().registDiseaseformation(domain, petIdx);
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
    public void deleteWeightInformation(int petIdx, int id, final DomainCallBackListner<Integer> domainCallBackListner) {
        Call<Integer> call = NetRetrofit.getInstance().getPetWeightInfoService().delete(petIdx, id);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>(){

            @Override
            protected void doPostExecute(Integer result) {
                domainCallBackListner.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public void registWeightInformation(int petIdx, PetWeightInfo petWeightInfo, final CommonDomainCallBackListner<Integer> domainCallBackListner) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getPetWeightInfoService().regist(petIdx, petWeightInfo);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>(){
            @Override
            protected void doPostExecute(CommonResponce<Integer> commonResponce) {
                domainCallBackListner.doPostExecute(commonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }


    public void registPetType ( String path, int petType, final DomainCallBackListner<Integer> callback ) {
        Call<Integer> call = NetRetrofit.getInstance().getPetService().registPetType(path, petType);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                callback.doPostExecute(result);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void registPetUserSelectQuestion (int petIdx, PetUserSelectionQuestion petUserSelectionQuestion, final CommonModel.ObjectCallBackListner<CommonResponce<Integer>> callback) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getPetService().registPetUserSelectQuestion(petIdx, petUserSelectionQuestion);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>() {
            @Override
            protected void doPostExecute(CommonResponce<Integer> integerCommonResponce) {
                callback.doPostExecute(integerCommonResponce);
            }

            @Override
            protected void doPreExecute() {

            }

            @Override
            protected void doCancelled() {

            }
        }.execute(call), limitedTime, interval, true).start();
    }
    public void deletePetUserSelectQuestion ( int petIdx, int questionIdx, final CommonModel.ObjectCallBackListner<CommonResponce<Integer>> callback ) {
        Call<CommonResponce<Integer>> call = NetRetrofit.getInstance().getPetService().deletePetUserSelectQuestion(petIdx, questionIdx);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<CommonResponce<Integer>>() {
            @Override
            protected void doPostExecute(CommonResponce<Integer> integerCommonResponce) {
                callback.doPostExecute(integerCommonResponce);
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
