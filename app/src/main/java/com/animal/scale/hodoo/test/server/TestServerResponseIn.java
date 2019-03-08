package com.animal.scale.hodoo.test.server;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

public interface TestServerResponseIn {
    interface View {

        void setResult(User d);
    }
    interface Presenter {

        void loadData(Context context);

        void testSubmit();
    }
}
