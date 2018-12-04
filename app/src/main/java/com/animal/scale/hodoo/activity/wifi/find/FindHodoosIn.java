package com.animal.scale.hodoo.activity.wifi.find;

import android.content.Context;

public interface FindHodoosIn {

    interface View{

        void registDeviceResult(Integer result);
    }

    interface Presenter{

        void loadData(Context context);

        void registDevice(String bssid);
    }
}
