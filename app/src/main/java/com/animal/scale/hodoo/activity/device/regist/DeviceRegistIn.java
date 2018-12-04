package com.animal.scale.hodoo.activity.device.regist;

import android.content.Context;

public interface DeviceRegistIn {

    interface View{

        void setProgress(boolean play);

        void moveWIFISetting();
    }

    interface Presenter{

        void loadData(Context context);

        void tempRegist();
    }
}
