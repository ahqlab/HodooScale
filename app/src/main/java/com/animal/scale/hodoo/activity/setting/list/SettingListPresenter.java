package com.animal.scale.hodoo.activity.setting.list;

public class SettingListPresenter implements SettingList.Presenter{

    SettingList.View settingListView;
    SettingListModel settingListModel;

    public SettingListPresenter(SettingList.View settingListView) {
        this.settingListView = settingListView;
        this.settingListModel = new SettingListModel();
    }

    @Override
    public void getSttingListMenu() {
        settingListView.setListviewAdapter(settingListModel.getSettingList());
    }
}
