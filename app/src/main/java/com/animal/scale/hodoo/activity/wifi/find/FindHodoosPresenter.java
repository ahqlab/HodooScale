package com.animal.scale.hodoo.activity.wifi.find;

import android.content.Context;

import com.animal.scale.hodoo.activity.home.activity.HomeActivityIn;
import com.animal.scale.hodoo.activity.home.activity.HomeActivityModel;
import com.animal.scale.hodoo.common.CommonModel;

public class FindHodoosPresenter implements  FindHodoosIn.Presenter {

    public FindHodoosIn.View view;

    public FindHodoosModel model;


    public FindHodoosPresenter(FindHodoosIn.View view){
        this.view = view;
        this.model = new FindHodoosModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void registDevice(String bssid) {
        model.registDevice(bssid, new FindHodoosModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                view.registDeviceResult(result);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
