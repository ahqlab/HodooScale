package com.animal.scale.hodoo.activity.user.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.invitation.finish.InvitationFinishActivity;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.message.ResultMessage;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

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
        loginModel.sendServer(user, new CommonModel.DomainCallBackListner<CommonResponce<User>>() {
            @Override
            public void doPostExecute(CommonResponce<User> resultMessageGroup) {
                if ( resultMessageGroup != null ) {
                    if (resultMessageGroup.getResultMessage().equals(ResultMessage.NOT_FOUND_EMAIL)) {
                        loginView.showPopup(context.getString(R.string.not_found_email));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.ID_PASSWORD_DO_NOT_MATCH)) {
                        loginView.showPopup(context.getString(R.string.id_password_do_not_match));
                    } else if ( resultMessageGroup.getResultMessage().equals(ResultMessage.WAIT_INVITATION) ) {
                        loginView.goInvitationActivity();
                    } else if ( resultMessageGroup.getResultMessage().equals(ResultMessage.WITHDRAW_USER) ) {
                        loginView.showPopup(context.getString(R.string.login__alert_withdraw_user_content));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.FAILED)) {
                        loginView.showPopup(context.getString(R.string.failed));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.SUCCESS)) {
                        Gson gson = new Gson();
                        User user = resultMessageGroup.getDomain();
                        if ( user.getUserCode() <= 0 ) {
                            loginView.showPopup(context.getString(R.string.login__alert_email_certified_content), new OnDialogClickListener() {
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

                        // 디바이스, 펫 등록 상태를 Check 한다
                        checkRegistrationStatus();
                    }
                } else {
                    loginView.showPopup(context.getString(R.string.failed));
                    loginView.setBtnState(true);
                }
            }

            @Override
            public void doPreExecute() {
                loginView.setProgress(true);
            }

            @Override
            public void doCancelled() {
                loginView.setProgress(false);
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
       /* loginModel.confirmDeviceRegistration(new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                if(integer > 0){
                    //디바이스 등록됨.*/
            loginModel.confirmPetRegistrationResult(new CommonModel.DomainCallBackListner<CommonResponce<Integer>>() {
                @Override
                public void doPostExecute(CommonResponce<Integer> responce) {
                    if(responce.getStatus() == HodooConstant.OK_RESPONSE){
                        if(responce.getDomain() > 0){
                            //등록된 펫 존재함.
                            loginView.setProgress(false);
                            loginView.setAutoLoginState();
                        }else{
                            //펫이 없음.
                            loginView.setProgress(false);
                            loginView.selectTheNextAction();

                            //loginView.saveFcmToken();
                        }
                    }else if(responce.getStatus() == HodooConstant.SQL_ERROR_RESPONSE){
                        loginView.setProgress(false);
                        loginView.setServerError();
                    }
                }

                @Override
                public void doPreExecute() {

                }

                @Override
                public void doCancelled() {
                    loginView.setProgress(false);
                    loginView.setServerError();
                }
                /*@Override
                public void doPostExecute(Integer[] results) {
                    if ( results.length == 1 ) {
                        switch ( results[0] ) {
                            case HodooConstant.PET_REGIST_SUCESS :
                                loginView.setAutoLoginState();
                                break;
                            case HodooConstant.PET_REGIST_FAILED :
                                loginView.goPetRegistActivity(0);
                                break;
                        }
                    } else {
                        int petIdx = results[1];
                        switch ( results[0] ) {
                            case HodooConstant.PET_REGIST_FAILED :
                                loginView.goPetRegistActivity(petIdx);
                                break;
                            case HodooConstant.PET_NOT_REGIST_DISEASES :
                                loginView.goDiseasesRegistActivity(petIdx);
                                break;
                            case HodooConstant.PET_NOT_REGIST_PHYSICAL :
                                loginView.goPhysicalRegistActivity(petIdx);
                                break;
                            case HodooConstant.PET_NOT_REGIST_WEIGHT :
                                loginView.goWeightRegistActivity(petIdx);
                                break;
                        }
                    }
                }
                @Override
                public void doPreExecute() {

                }

                @Override
                public void doCancelled() {

                }*/
            });
              /*  }else{
                    //디바이스 없음

                }
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });*/
    }

    @Override
    public void saveFCMToken(User user) {
        String token = FirebaseInstanceId.getInstance().getToken();
        user.setPushToken(token);
        loginModel.saveFCMToken(user, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                if ( integer != null ) {
                    loginView.goDeviceRegistActivity();
                }
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {
                loginView.setProgress(false);
            }
        });
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
        if ( loginModel.getAutoLoginState() == HodooConstant.AUTO_LOGIN_SUCCESS ) {
            loginView.setAutoLogin(true);
        }
        loginView.setPassword(user.getPassword());
        sendServer(user);
    }
}
