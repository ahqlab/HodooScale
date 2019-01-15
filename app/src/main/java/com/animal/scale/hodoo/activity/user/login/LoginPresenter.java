package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.message.ResultMessage;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.List;

public class LoginPresenter implements Login.Presenter {

    Login.View loginView;
    LoginModel loginModel;
    Context context;

    public interface OnDialogClickListener {
        void onPositiveClick(DialogInterface dialog, int which );
        void onNegativeClick(DialogInterface dialog, int which);
    }

    public LoginPresenter(Login.View loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModel();
    }

    @Override
    public void initUserData(User user, Context context) {
        this.context = context;
        loginModel.initUserData(user, context);
    }

    @Override
    public void sendServer(User user) {
        String token = FirebaseInstanceId.getInstance().getToken();
        user.setPushToken(token);
        loginModel.sendServer(user, new LoginModel.DomainCallBackListner<CommonResponce<User>>() {
            @Override
            public void doPostExecute(CommonResponce<User> resultMessageGroup) {
                if ( resultMessageGroup != null ) {
                    if (resultMessageGroup.getResultMessage().equals(ResultMessage.NOT_FOUND_EMAIL)) {
                        loginView.showPopup(context.getString(R.string.not_found_email));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.ID_PASSWORD_DO_NOT_MATCH)) {
                        loginView.showPopup(context.getString(R.string.id_password_do_not_match));
                    } else if ( resultMessageGroup.getResultMessage().equals(ResultMessage.WITHDRAW_USER) ) {
                        loginView.showPopup("탈퇴한 회원입니다.");
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.FAILED)) {
                        loginView.showPopup(context.getString(R.string.failed));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.SUCCESS)) {
                        Log.e("HJLEE", "LOGIN RESULT : " + resultMessageGroup.getDomain().toString().trim());
                        Gson gson = new Gson();
                        User user = resultMessageGroup.getDomain();
                        if ( user.getUserCode() <= 0 ) {
                            loginView.showPopup("이메일 인증을 진행해주세요.", new OnDialogClickListener() {
                                @Override
                                public void onPositiveClick(DialogInterface dialog, int which) {
                                    loginView.goEmailCertified();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onNegativeClick(DialogInterface dialog, int which) {

                                }
                            });
                            return;
                        }
                        saveUserSharedValue(resultMessageGroup.getDomain());
                        checkRegistrationStatus();
                    }
                } else {
                    loginView.showPopup(context.getString(R.string.failed));
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
        sendServer(user);
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
                                        loginView.setAutoLoginState();
                                    }
                                }else{
                                    loginView.setAutoLoginState();
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

    @Override
    public void saveFCMToken(User user) {
    }

    @Override
    public void setAutoLogin(boolean state) {
        if ( state )
            loginModel.saveAutoLogin();
        loginView.goHomeActivity();
    }

    @Override
    public void autoLogin() {
        User user = loginModel.getUser();
        sendServer(user);
    }
}
