package com.animal.scale.hodoo.activity.test;

import android.content.Context;

public interface Test {
    interface View {

    }
    interface Presenter {
        void initData(Context context);
        void sendNoti();
    }
}
