package com.animal.scale.hodoo.activity.setting.list;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class SettingListModel extends CommonModel {

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void logout() {
        sharedPrefManager.removeAllPreferences();
    }

    public int getUnitIdx () {
        return sharedPrefManager.getIntExtra(SharedPrefVariable.UNIT_STR);
    }
    public void saveUnitIdx ( int unitIdx ) {
        sharedPrefManager.putIntExtra(SharedPrefVariable.UNIT_STR, unitIdx);
    }

}
