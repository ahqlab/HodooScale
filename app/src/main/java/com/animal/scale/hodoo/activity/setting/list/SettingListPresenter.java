package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.SettingMenu;

public class SettingListPresenter implements SettingList.Presenter {

    SettingList.View settingListView;
    SettingListModel settingListModel;
    private CommonNotificationModel notificationModel;

    public SettingListPresenter(SettingList.View settingListView) {
        this.settingListView = settingListView;
        this.settingListModel = new SettingListModel();
    }

    @Override
    public void loadData(Context context) {
        settingListModel.loadData(context);
        notificationModel = CommonNotificationModel.getInstance(context);
    }

    @Override
    public void getSttingListMenu() {
        settingListView.setListviewAdapter(notificationModel.getInvitationBadgeCount(), settingListModel.getSettingList());
    }

    @Override
    public void logout() {
        settingListModel.logout();
        settingListView.goLoginPage();
    }
}
