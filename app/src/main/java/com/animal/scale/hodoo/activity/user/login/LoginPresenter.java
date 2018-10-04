package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;

import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.User;

import java.util.List;

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
                    checkRegistrationStatus();
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
    public void checkRegistrationStatus() {

        loginModel.confirmDeviceRegistration(new LoginModel.DeviceRegistrationListener() {
            @Override
            public void doPostExecute(List<Device> devices) {
                if(!devices.isEmpty()){
                    //디바이스 등록됨.
                    loginModel.confirmPetRegistration(new LoginModel.PetRegistrationListener() {
                        @Override
                        public void doPostExecute(List<Pet> pets) {
                            if(!pets.isEmpty()){
                                //PET 등록됨.
                                if(pets.size() == 1){
                                    if(pets.get(0).getBasic() == 0){
                                        loginView.goPetRegistActivity(pets.get(0).getPetIdx());
                                    }else if(pets.get(0).getDisease() == 0){
                                        loginView.goDiseasesRegistActivity(pets.get(0).getPetIdx());
                                    }else if(pets.get(0).getPhysical() == 0){
                                        loginView.goPhysicalRegistActivity(pets.get(0).getPetIdx());
                                    }else if(pets.get(0).getWeight() == 0){
                                        loginView.goWeightRegistActivity(pets.get(0).getPetIdx());
                                    }else{
                                        loginView.goHomeActivity();
                                    }
                                }else{
                                    loginView.goHomeActivity();
                                }
                            }else{
                                //PET 등록페이지 이동
                                loginView.goPetRegistActivity(0);
                            }
                        }
                        @Override
                        public void doPreExecute() {
                        }
                    });
                }else{
                    //디바이스 없음
                    loginView.goDeviceRegistActivity();
                }
            }

            @Override
            public void doPreExecute() {
                loginView.setProgress(true);
            }
        });
    }
}
