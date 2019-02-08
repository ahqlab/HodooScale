package com.animal.scale.hodoo.activity.wifi.find;

import android.content.Context;

import com.animal.scale.hodoo.activity.home.activity.HomeActivityIn;
import com.animal.scale.hodoo.activity.home.activity.HomeActivityModel;
import com.animal.scale.hodoo.activity.user.login.LoginModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Pet;

import java.util.List;

public class FindHodoosPresenter implements  FindHodoosIn.Presenter {

    public FindHodoosIn.View view;

    public FindHodoosModel model;
    public LoginModel loginModel;


    public FindHodoosPresenter(FindHodoosIn.View view){
        this.view = view;
        this.model = new FindHodoosModel();
        this.loginModel = new LoginModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void registDevice(String bssid) {
        model.registDevice(bssid, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                view.registDeviceResult(result);
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
    public void confirmPetRegistration() {
        loginModel.confirmPetRegistration(new CommonModel.DomainListCallBackListner<Pet>() {
            @Override
            public void doPostExecute(List<Pet> pets) {
                if(!pets.isEmpty()){
                    //PET 등록됨.
                    if(pets.size() == 1){
                        if(pets.get(0).getBasic() == 0){
                            view.goBasicRegistActivity(pets.get(0).getPetIdx());
                        }else if(pets.get(0).getDisease() == 0){
                            view.goDiseasesRegistActivity(pets.get(0).getPetIdx());
                        }else if(pets.get(0).getPhysical() == 0){
                            view.goPhysicalRegistActivity(pets.get(0).getPetIdx());
                        }else if(pets.get(0).getWeight() == 0){
                            view.goWeightRegistActivity(pets.get(0).getPetIdx());
                        }else{
                            view.successDevideResigt();
                        }
                    }else{
                        view.successDevideResigt();
                    }
                }else{
                    //PET 등록페이지 이동
                    view.goBasicRegistActivity(0);
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
    public void loginModelData(Context context) {
        loginModel.initUserData(context);
    }
}
