package com.animal.scale.hodoo.activity.setting.user.group.manager;

import android.content.Context;

import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGropManager;
import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGropManagerModel;

public class UserGroupPresenter implements UserGropManager.Presenter {
    private UserGropManager.View mView;
    private UserGropManagerModel mModel;

    public static final int INIT_TYPE = 1;
    public static final int UPDATE_TYPE = 2;

    UserGroupPresenter (Context context, UserGropManager.View view ) {
        mView = view;
        mModel = new UserGropManagerModel();
        mModel.loadData(context);
    }

    @Override
    public void getBadgeCount( int type ) {
        mView.setBadgeCount( type, mModel.getBadgeCount() );
    }

    @Override
    public void getSttingListMenu() {
        mView.setListviewAdapter(mModel.getSettingList());
    }
}
