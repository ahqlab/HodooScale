package com.animal.scale.hodoo.activity.user.reset.password.send;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

public class SendCertificationNumberPresenter implements SendCertificationNumberIn.Presenter {

    Context context;

    SendCertificationNumberIn.View view;

    SendCertificationNumberModel model;

    public SendCertificationNumberPresenter(Context context) {
        this.context = context;
        this.view = view;
        this.model = new SendCertificationNumberModel();

    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void sendTempPassword(User user) {
        model.sendTempPassword(user, new SendCertificationNumberModel.DomainCallBackListner<ResultMessageGroup>(){

            @Override
            public void doPostExecute(ResultMessageGroup resultMessageGroup) {
                view.sendResult(resultMessageGroup);
               // view.setProgress(false);
            }

            @Override
            public void doPreExecute() {
                //view.setProgress(true);
            }
        });
    }
}
