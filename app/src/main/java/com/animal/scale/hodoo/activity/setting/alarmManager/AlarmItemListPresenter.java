package com.animal.scale.hodoo.activity.setting.alarmManager;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.AlarmItem;
import com.animal.scale.hodoo.domain.CommonResponce;

import java.util.List;

public class AlarmItemListPresenter implements AlarmItemListIn.Presenter {

    AlarmItemListIn.View view;

    AlarmItemListModel model;

    AlarmItemListPresenter(AlarmItemListIn.View view) {
        this.view = view;
        this.model = new AlarmItemListModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getAlarmItems() {
        model.getAlarmItems(new CommonModel.CommonDomainCallBackListner<AlarmItem>() {

            @Override
            public void doPostExecute(CommonResponce<List<AlarmItem>> d) {
                view.setAlarmItem(d.getDomain());
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