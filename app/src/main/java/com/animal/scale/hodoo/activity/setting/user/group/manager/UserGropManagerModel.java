package com.animal.scale.hodoo.activity.setting.user.group.manager;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class UserGropManagerModel extends CommonModel {
    private SharedPrefManager mSharedPrefManager;
    private CommonNotificationModel notificationData;
    private Context context;
    @Override
    public void loadData(Context context) {
        super.loadData(context);
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
        notificationData = CommonNotificationModel.getInstance(context);
    }
}
