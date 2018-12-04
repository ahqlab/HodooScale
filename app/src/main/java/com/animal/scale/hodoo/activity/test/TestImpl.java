package com.animal.scale.hodoo.activity.test;

import android.content.Context;

public interface TestImpl {
    interface View {
        void setText();
    }
    interface Presenter {
        void workModelTest(Context context);
    }
}
