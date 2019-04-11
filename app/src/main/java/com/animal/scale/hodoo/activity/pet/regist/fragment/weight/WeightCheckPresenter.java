package com.animal.scale.hodoo.activity.pet.regist.fragment.weight;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.BfiModel;
import com.animal.scale.hodoo.domain.PetWeightInfo;

import java.util.List;

public class WeightCheckPresenter implements WeightCheckIn.Presenter {

    private String TAG = WeightCheckPresenter.class.getSimpleName();

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
        model.getWeightInformation(petIdx, new CommonModel.DomainCallBackListner<PetWeightInfo>() {
            @Override
            public void doPostExecute(PetWeightInfo petWeightInfo) {
                view.setDomain(petWeightInfo);
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
    public void deleteWeightInfo(int petIdx, int id) {
        model.deleteWeightInformation(petIdx, id, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.registWeightInformation();
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
    public void registWeightInfo(int petIdx, PetWeightInfo domain) {
        model.registWeightInformation(petIdx, domain, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.registResult(integer);
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
    public void getBfiQuestion(String location, int type) {
        model.getBfiQuestion(location, type, new CommonModel.DomainListCallBackListner<BfiModel>() {

            @Override
            public void doPostExecute(List<BfiModel> d) {
                Log.e(TAG, "doPostExecute" );
                view.setQuestion(d);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    /*@Override
    public void setViewFlipper() {
        view.setViewFlipper();
    }*/
}
