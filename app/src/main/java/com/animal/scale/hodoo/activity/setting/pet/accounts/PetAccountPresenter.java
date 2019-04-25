package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public class PetAccountPresenter implements PetAccounts.Presenter{

    PetAccounts.View view;
    PetAccountModel model;

    public PetAccountPresenter(PetAccounts.View view) {
        this.view = view;
        this.model = new PetAccountModel();
    }

    @Override
    public void initUserData(Context context) {
        model.initUserData(context);
    }

    @Override
    public void getData() {
       model.getPetData(new CommonModel.DomainListCallBackListner<PetAllInfos>(){

           @Override
           public void doPostExecute(List<PetAllInfos> data) {
               if(data.size() > 0){
                   model.addRegistBtn(data);
                   view.setAdapter(data);
               }else{
                   model.removeCurrentPetIdx();
                   view.goToPetRegistActivity();
               }
           }

           @Override
            public void doPreExecute() {

            }

           @Override
           public void doCancelled() {

           }
       });
    }

    @Override
    public void saveCurrentIdx(int idx) {
        model.saveCurrentIdx(idx);
        view.reFreshData( model.getSelectedPetIdx() );
    }

    @Override
    public void deletePet(int petIdx) {
        model.deletePet(petIdx, new CommonModel.DomainCallBackListner<CommonResponce<Integer>>(){

            @Override
            public void doPostExecute(CommonResponce<Integer> integerCommonResponce) {
                view.petDeleteResult(integerCommonResponce.getDomain());
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
