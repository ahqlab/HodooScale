package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;

public class MyAccountPresenter implements MyAccount.Presenter{

    MyAccount.View myAccountView;

    MyAccountModel myAccountModel;

    @Override
    public void initLoadData(Context context) {
        myAccountModel.initLoadData(context);
    }

    public MyAccountPresenter(MyAccount.View myAccountView) {
        this.myAccountView = myAccountView;
        this.myAccountModel = new MyAccountModel();
    }
    @Override
    public void getSttingListMenu() {
        myAccountView.setListviewAdapter(myAccountModel.getSettingList());
    }

    @Override
    public void logout() {
        myAccountModel.logout();
        myAccountView.goLoginPage();
    }

    @Override
    public void changePassword() {
        myAccountView.goChangePasswordPage();
    }
}
