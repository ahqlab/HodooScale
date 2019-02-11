package com.animal.scale.hodoo.activity.setting.device.feeder;

import android.content.Context;

import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragmentModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.PetAllInfos;

public class FeederOrderPresenter implements FeederOrderIn.Presenter {

    FeederOrderIn.View view;

    FeederOrderModel model;

    public FeederOrderPresenter(FeederOrderIn.View view) {
        this.view = view;
        this.model = new FeederOrderModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getPetAllInfo() {
        model.getPetAllInfo(new CommonModel.DomainCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(PetAllInfos petAllInfos) {
                view.setPetAllInfo(petAllInfos);
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
    public void insertOrder(float rer) {
        model.insertOrder(rer, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.insertResult(integer);
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
