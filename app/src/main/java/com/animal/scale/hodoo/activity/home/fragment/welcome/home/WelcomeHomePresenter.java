package com.animal.scale.hodoo.activity.home.fragment.welcome.home;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.meal.regist.MealRegistrationModel;
import com.animal.scale.hodoo.activity.user.login.LoginModel;
import com.animal.scale.hodoo.activity.user.login.LoginPresenter;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.message.ResultMessage;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

public class WelcomeHomePresenter implements WelcomeHomeIn.Presenter {

    WelcomeHomeIn.View view;

    WelcomeHomeModel model;

    LoginModel loginModel;

    Context context;

    MealRegistrationModel mealRegistrationModel;

    public WelcomeHomePresenter(WelcomeHomeIn.View view) {
        this.view = view;
        this.model = new WelcomeHomeModel();
        this.loginModel = new LoginModel();
    }

    @Override
    public void loadData(Context context) {
        this.context = context;
        model.loadData(context);
        loginModel.initUserData(context);
    }

    @Override
    public void removeAllPref() {
        model.removeAllPref();
    }

    @Override
    public void doSnsLogin(User user) {
        model.doSnsLogin(user, new CommonModel.ObjectCallBackListner<CommonResponce<User>>() {
            @Override
            public void doPostExecute(CommonResponce<User> commonResponce) {
                view.setSnsLoginResult(commonResponce);
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
    public void doLogin(User user) {
        String token = FirebaseInstanceId.getInstance().getToken();
        user.setPushToken(token);

        loginModel.sendServer(user, new CommonModel.DomainCallBackListner<CommonResponce<User>>() {
            @Override
            public void doPostExecute(CommonResponce<User> resultMessageGroup) {
                if (resultMessageGroup != null) {
                    if (resultMessageGroup.getResultMessage().equals(ResultMessage.WAIT_INVITATION)) {
                        view.goInvitationActivity();
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.WITHDRAW_USER)) {
                        view.showPopup(context.getString(R.string.login__alert_withdraw_user_content));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.FAILED)) {
                        view.showPopup(context.getString(R.string.failed));
                    } else if (resultMessageGroup.getResultMessage().equals(ResultMessage.SUCCESS)) {
                        User user = resultMessageGroup.getDomain();
                        loginModel.saveUserSharedValue(resultMessageGroup.getDomain());
                        // 디바이스, 펫 등록 상태를 Check 한다
                        checkRegistrationStatus();
                    }
                } else {
                    view.showPopup(context.getString(R.string.failed));
                    view.setBtnState(false);
                }
            }

            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }

            @Override
            public void doCancelled() {
                view.setProgress(false);
            }
        });
    }

    @Override
    public void initDate(Context context) {

    }

    private void checkRegistrationStatus() {
        loginModel.confirmPetRegistrationResult(new CommonModel.DomainCallBackListner<CommonResponce<Integer>>() {
            @Override
            public void doPostExecute(CommonResponce<Integer> responce) {
                Log.e("HJLEE", "responce : " + responce.toString());
                if (responce.getStatus() == HodooConstant.OK_RESPONSE || responce.getStatus() == HodooConstant.NO_CONTENT_RESPONSE) {
                    if (responce.getDomain() > 0) {
                        //등록된 펫 존재함.
                        view.setProgress(false);
                        //view.setAutoLoginState();
                    } else {
                        //펫이 없음.
                        view.setProgress(false);
                        view.selectTheNextAction();

                        //loginView.saveFcmToken();
                    }
                } else if (responce.getStatus() == HodooConstant.NO_CONTENT_RESPONSE) {
                    //펫이 없음.
                    view.setProgress(false);
                    view.selectTheNextAction();
                } else if (responce.getStatus() == HodooConstant.SQL_ERROR_RESPONSE) {
                    view.setProgress(false);
                    view.setServerError();
                }
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {
                //loginView.removeAutoLogin();
                //loginView.setProgress(false);
                //loginView.setServerError();
            }
        });

    }
}