package com.animal.scale.hodoo;

import android.content.Context;

public interface Main {
    interface View {
        void goHomeActivity();
        void goAutoLogin();
    }
    interface Presenter {
        void initDate(Context context);
        void getData();
    }
}
