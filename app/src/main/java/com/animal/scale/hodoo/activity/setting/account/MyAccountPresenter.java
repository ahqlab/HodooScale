package com.animal.scale.hodoo.activity.setting.account;

import android.content.Context;
import android.content.DialogInterface;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonListener;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.User;

public class MyAccountPresenter implements MyAccount.Presenter {

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

    @Override
    public void initUserData() {
        myAccountModel.initUserData(new CommonModel.DomainCallBackListner<User>() {
            @Override
            public void doPostExecute(User user) {
                user.setPushToken(null);
                saveFCMToken(user);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void saveFCMToken(User user) {
        myAccountModel.saveFCMToken(user, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                logout();
            }

            @Override
            public void doPreExecute() {

            }
        });
    }


    @Override
    public void withdraw() {
        myAccountModel.withdraw(HodooConstant.WITHDRAW, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                myAccountView.showPopup("탈퇴되었습니다.", new CommonListener.PopupClickListener() {
                    @Override
                    public void onPositiveClick(DialogInterface dialog, int which) {
                        myAccountModel.logout();
                        myAccountView.goLoginPage();
                    }

                    @Override
                    public void onNegativeClick(DialogInterface dialog, int which) {

                    }
                });
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
