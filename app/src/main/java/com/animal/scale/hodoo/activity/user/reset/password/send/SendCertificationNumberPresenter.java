package com.animal.scale.hodoo.activity.user.reset.password.send;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.User;

public class SendCertificationNumberPresenter implements SendCertificationNumberIn.Presenter {

    Context context;

    SendCertificationNumberIn.View view;

    SendCertificationNumberModel model;

    public SendCertificationNumberPresenter(SendCertificationNumberIn.View view) {
        this.view = view;
        this.model = new SendCertificationNumberModel();

    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void sendTempPassword(User user) {
        model.sendTempPassword(user, new SendCertificationNumberModel.DomainCallBackListner<CommonResponce<User>>(){
            @Override
            public void doPostExecute(CommonResponce<User> resultMessageGroup) {
                view.sendResult(resultMessageGroup);
                view.setProgress(false);
            }

            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }
        });
    }
}
