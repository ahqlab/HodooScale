package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public interface SettingList {

    interface View {

        public void setListviewAdapter(int badgeCount, List<SettingMenu> menus);

        void setExpandableListAdapter (ArrayList<String> title, ArrayList<List<SettingMenu>> content);

        void goLoginPage();

        void updateBadgeCount( int count );
    }

    interface Presenter {

        void loadData(Context context);

        void getStringSettingList( Context context );

        void logout();

        void getInvitationCount();

        void saveUnit( int unitIdx );

    }
}
