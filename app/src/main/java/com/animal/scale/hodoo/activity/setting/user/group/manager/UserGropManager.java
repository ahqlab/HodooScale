package com.animal.scale.hodoo.activity.setting.user.group.manager;

import android.content.Context;

import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public interface UserGropManager {
    interface View {
        void setBadgeCount( int type, int count );
        void setListviewAdapter(List<SettingMenu> menu);
    }
    interface Presenter {
        void getBadgeCount(int type );
    }
}
