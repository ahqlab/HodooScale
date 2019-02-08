package com.animal.scale.hodoo.activity.setting.account.change.password;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.User;

public class ChangePasswordPresenter implements ChangePassword.Presenter {

    ChangePassword.View view;

    ChangePasswordModel model;

    public ChangePasswordPresenter(ChangePassword.View view) {
        this.view = view;
        this.model = new ChangePasswordModel();
    }

    @Override
    public void changeUserPassword(User user) {
        model.changeUserPassword(user, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.userPasswordUpdateResult(integer);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void initLoadData(Context context) {
        model.loadData(context);
    }
}
