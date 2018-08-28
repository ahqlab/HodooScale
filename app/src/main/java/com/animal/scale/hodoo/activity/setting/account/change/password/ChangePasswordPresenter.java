package com.animal.scale.hodoo.activity.setting.account.change.password;

public class ChangePasswordPresenter implements ChangePassword.Presenter {

    ChangePassword.View view;

    ChangePasswordModel model;


    public ChangePasswordPresenter(ChangePassword.View view) {
        this.view = view;
        this.model = new ChangePasswordModel();
    }
}
