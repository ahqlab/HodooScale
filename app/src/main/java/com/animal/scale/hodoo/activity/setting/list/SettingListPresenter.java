package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.domain.SettingMenu;

public class SettingListPresenter implements SettingList.Presenter {

    SettingList.View settingListView;
    SettingListModel settingListModel;

    public SettingListPresenter(SettingList.View settingListView) {
        this.settingListView = settingListView;
        this.settingListModel = new SettingListModel();
    }

    @Override
    public void loadData(Context context) {
        settingListModel.loadData(context);
    }

    @Override
    public void getSttingListMenu() {
        settingListView.setListviewAdapter(settingListModel.getSettingList());
    }
}
