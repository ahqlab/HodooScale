package com.animal.scale.hodoo.activity.setting.device.bowelplate.list;

import android.content.Context;

import com.animal.scale.hodoo.activity.setting.list.SettingList;
import com.animal.scale.hodoo.activity.setting.list.SettingListModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Device;

import java.util.List;

public class ActivityBowelPlateListPresenter implements ActivityBowelPlateListIn.Presenter{

    ActivityBowelPlateListIn.View view;
    ActivityBowelPlateListModel model;

    public ActivityBowelPlateListPresenter(ActivityBowelPlateListIn.View view) {
        this.view = view;
        this.model = new ActivityBowelPlateListModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void setChangeSwithStatus(int deviceIdx, boolean b) {
        model.setChangeSwithStatus(deviceIdx, b, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.setChangeSwithStatusResult(integer);
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
    public void setChangeDeviceRegisted(int deviceIdx, boolean b) {
        model.setChangeDeviceRegisted(deviceIdx, b, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.setChangeDeviceRegistedResult(integer);
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
    public void getMyBowlPlateList() {
        model.getMyBowlPlateList(new CommonModel.DomainListCallBackListner<Device>() {
            @Override
            public void doPostExecute(List<Device> d) {
                view.MyBowlPlateList(d);
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
