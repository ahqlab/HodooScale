package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.domain.Groups;
import com.animal.scale.hodoo.domain.User;

public class LoginPresenter implements Login.Presenter {

    Login.View loginView;
    LoginModel loginModel;

    public LoginPresenter(Login.View loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel();
    }

    @Override
    public void initUserData(User user, Context context) {
        loginModel.initUserData(user, context);
    }

    @Override
    public void sendServer() {
        loginModel.sendServer(new LoginModel.LoginResultListener() {
            @Override
            public void doPostExecute(User user) {
                loginView.setProgress(false);
                if(user != null){
                    saveUserSharedValue(user);
                    isRegistPetCheck();
                }else{
                    loginView.showPopup("비밀번호가 맞지않습니다.");
                }
            }

            @Override
            public void doPreExecute() {
                loginView.setProgress(true);
            }
        });
    }

    @Override
    public void userValidationCheck(User user) {
        if (loginModel.editTextisEmptyCheck(user.getEmail())) {
            //이메일 형식에 어긋납니다.
            loginView.showPopup("이메일을 입력해주세요.");
        } else if (!loginModel.editTextisValidEmail(user.getEmail())) {
            //이메일 형식에 어긋납니다.
            loginView.showPopup("이메일 형식에 맞지 않습니다.");
        } else if (loginModel.editTextisEmptyCheck(user.getPassword())) {
            loginView.showPopup("비밀번호를 입력하세요.");
        } else {
            sendServer();
        }
    }


    @Override
    public void saveUserSharedValue(User user) {
        loginModel.saveUserSharedValue(user);
    }

    @Override
    public void isRegistPetCheck() {
        loginModel.isRegistPetCheck(new LoginModel.RegistCheckListener() {
            @Override
            public void doPostExecute(Groups groups) {
                loginView.setProgress(false);
                if (groups != null) {
                    loginView.goHomeActivity();
                } else {
                    loginView.goRegistActivity();
                }
            }
            @Override
            public void doPreExecute() {
                loginView.setProgress(true);
            }
        });
    }
}
