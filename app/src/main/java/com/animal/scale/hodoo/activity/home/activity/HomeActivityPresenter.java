package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public class HomeActivityPresenter implements HomeActivityIn.Presenter{

    public HomeActivityIn.View view;

    public HomeActivityModel model;


    public HomeActivityPresenter(HomeActivityIn.View view){
        this.view = view;
        this.model = new HomeActivityModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void loadCustomDropdownView() {
        model.setSpinner(new HomeActivityModel.DomainListCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(List<PetAllInfos> petAllInfos) {
                view.setCustomDropdownView(petAllInfos);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getSttingListMenu() {
        view.setListviewAdapter(model.getSettingList());
    }

    @Override
    public void setCurrentPetInfos(List<PetAllInfos> data) {
        view.setCurrentPetInfos(data);
    }

    @Override
    public void setCurcleImage(PetAllInfos info) {
        view.setCurcleImage(info);
    }
}
