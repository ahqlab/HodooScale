package com.animal.scale.hodoo.activity.setting.account.info;

import android.content.Context;

import com.animal.scale.hodoo.domain.User;

public class ChangeUserInfoPresenter implements ChangeUserInfoIn.Presenter{

    ChangeUserInfoIn.View view;

    ChangeUserInfoModel model;

    public ChangeUserInfoPresenter(ChangeUserInfoIn.View view){
        this.view = view;
        this.model = new ChangeUserInfoModel();
    }

    @Override
    public void initLoadData(Context context) {
        model.initLoadData(context);
    }

    @Override
    public void initUserData() {
        model.initUserData(new ChangeUserInfoModel.DomainCallBackListner<User>() {
            @Override
            public void doPostExecute(User user) {
                view.setUserInfo(user);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void updateBasicInfo(User user) {
        model.updateBasicInfo(user, new ChangeUserInfoModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.showResultPopup(integer);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
