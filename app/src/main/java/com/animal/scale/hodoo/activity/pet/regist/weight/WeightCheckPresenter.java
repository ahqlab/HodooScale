package com.animal.scale.hodoo.activity.pet.regist.weight;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetWeightInfo;

public class WeightCheckPresenter implements WeightCheckIn.Presenter{

    WeightCheckIn.View view;

    WeightCheckModel model;

    public WeightCheckPresenter(WeightCheckIn.View view){
        this.view = view;
        this.model = new WeightCheckModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void setNavigation() {
        view.setNavigation();
    }

    @Override
    public void getWeightInformation(int petIdx) {
        model.getWeightInformation(petIdx, new WeightCheckModel.ResultListner() {
            @Override
            public void doPostExecute(PetWeightInfo petWeightInfo) {
                view.setDomain(petWeightInfo);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void deleteWeightInfo(int petIdx, int id) {
        model.deleteWeightInformation(petIdx, id, new WeightCheckModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.registWeightInformation();
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void registWeightInfo(int petIdx, PetWeightInfo domain) {
        model.registWeightInformation(petIdx, domain, new WeightCheckModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.registResult(integer);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    /*@Override
    public void setViewFlipper() {
        view.setViewFlipper();
    }*/
}
