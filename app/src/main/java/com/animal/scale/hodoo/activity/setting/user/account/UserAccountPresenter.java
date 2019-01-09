package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

import java.util.List;

public class UserAccountPresenter implements UserAccountIn.Presenter {

    UserAccountIn.View view;

    UserAccountModel model;

    public UserAccountPresenter(UserAccountIn.View view) {
        this.view = view;
        this.model = new UserAccountModel();
    }

    @Override
    public void initUserData(Context context) {
        model.initUserData(context);
    }

    @Override
    public void getData() {
        model.getUserData(new UserAccountModel.asyncTaskListner() {
            @Override
            public void doPostExecute(List<User> data) {
                model.addRegistBtn(data);
                int idx = model.getUserIdx();
                view.setAdapter(idx, data);
            }
            @Override
            public void doPreExecute() {

            }
        });
    }
}
