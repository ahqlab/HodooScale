package com.animal.scale.hodoo.activity.setting.alarmManager;

import android.content.Context;

import com.animal.scale.hodoo.domain.AlarmItem;

import java.util.List;

public interface AlarmItemListIn {

    interface View {

        void setAlarmItem(List<AlarmItem> d);
    }

    interface Presenter {

        void loadData(Context context);

        void getAlarmItems();
    }
}
