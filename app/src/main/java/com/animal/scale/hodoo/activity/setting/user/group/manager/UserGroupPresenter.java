package com.animal.scale.hodoo.activity.setting.user.group.manager;

import android.content.Context;

import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGropManager;
import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGropManagerModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;

public class UserGroupPresenter implements UserGropManager.Presenter {
    private UserGropManager.View mView;
    private UserGropManagerModel mModel;
    private CommonNotificationModel notificationModel;

    public static final int INIT_TYPE = 1;
    public static final int UPDATE_TYPE = 2;

    UserGroupPresenter (Context context, UserGropManager.View view ) {
        mView = view;
        mModel = new UserGropManagerModel();
        notificationModel = CommonNotificationModel.getInstance(context);
        mModel.loadData(context);
    }

    @Override
    public void getBadgeCount( int type ) {
        mView.setBadgeCount( type, notificationModel.getInvitationCount() );
    }

}
