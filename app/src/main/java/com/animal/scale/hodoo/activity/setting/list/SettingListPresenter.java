package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void getStringSettingList( Context context ) {
        ArrayList<String> titleList = new ArrayList<>( Arrays.asList( context.getResources().getStringArray(R.array.setting_title) ) );


        /* content (s) */
        ArrayList<List<SettingMenu>> contentList;
        contentList = setList(
                context.getResources().getStringArray(R.array.general_settings),
                context.getResources().getStringArray(R.array.user_settings),
                context.getResources().getStringArray(R.array.device_settings),
                context.getResources().getStringArray(R.array.hodoo_link_settings),
                context.getResources().getStringArray(R.array.pet_settings),
                context.getResources().getStringArray(R.array.support_settings) );
        /* content (e) */

        contentList.get(SettingListActivity.HODOO_LINK).get(SettingListActivity.REQUEST).setBadgeCount(notificationModel.getInvitationCount());
        contentList.get(SettingListActivity.USER).get(SettingListActivity.UNIT).setStringState(true);
        int unitIdx = settingListModel.getUnitIdx();
        String[] unitArr = context.getResources().getStringArray(R.array.unit_str_arr);
        contentList.get(SettingListActivity.USER).get(SettingListActivity.UNIT).setSettingStr(unitArr[unitIdx]);
        settingListView.setExpandableListAdapter(titleList, contentList);
    }

    @Override
    public void logout() {
        settingListModel.logout();
        settingListView.goLoginPage();
    }

    @Override
    public void getInvitationCount() {
        settingListView.updateBadgeCount(notificationModel.getInvitationCount());
    }

    @Override
    public void saveUnit(int unitIdx) {
        settingListModel.saveUnitIdx(unitIdx);
    }

    public ArrayList<List<SettingMenu>> setList( String[]...param ) {
        ArrayList<List<SettingMenu>> array = new ArrayList<>();

        for (int i = 0; i < param.length; i++) {
            List<SettingMenu> menus = new ArrayList<SettingMenu>();
            String[] strArr = param[i];
            for (int j = 0; j < strArr.length; j++) {
                SettingMenu menu = new SettingMenu(strArr[j]);
                menus.add(menu);
            }
            array.add(menus);
        }
        return array;
    }
}
