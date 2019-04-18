package com.animal.scale.hodoo.activity.setting.alarmManager;

import android.content.Context;
import android.view.View;

import com.animal.scale.hodoo.domain.AlarmItem;

import java.util.List;

public interface AlarmItemListIn {

    interface View {

        void setAlarmItem(List<AlarmItem> d);

        void setAlarm(int number);

        void saveNotiListner (android.view.View view);

        void done( int result );
    }

    interface Presenter {

        void loadData(Context context);

        void getAlarmItems();

        void getAlarm();

        void saveAlarm( int number );
    }
}
