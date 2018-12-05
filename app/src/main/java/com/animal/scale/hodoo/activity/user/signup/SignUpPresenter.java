package com.animal.scale.hodoo.activity.user.signup;

import android.content.Context;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.message.ResultMessage;

class SignUpPresenter implements SignUpIn.Presenter {

    Context context;

    SignUpIn.View view;

    SignUpModel model;

    public SignUpPresenter(SignUpIn.View view) {
        this.view = view;
        this.model = new SignUpModel();
    }

    @Override
    public void loadData(Context context) {
        this.context = context;
        model.loadData(context);
    }

    @Override
    public void registUser(User user) {
        model.registUser(user, new SignUpModel.DomainCallBackListner<ResultMessageGroup>() {
            @Override
            public void doPostExecute(ResultMessageGroup resultMessageGroup) {
                if(resultMessageGroup.getResultMessage().equals(ResultMessage.DUPLICATE_EMAIL)){
                    view.showPopup("DUPLICATE_EMAIL");
                }else if(resultMessageGroup.getResultMessage().equals(ResultMessage.FAILED)){
                    view.showPopup("FAILED");
                }else if(resultMessageGroup.getResultMessage().equals(ResultMessage.SUCCESS)){
                    view.goNextPage();
                }
            }
            @Override
            public void doPreExecute() {
            }
        });
    }
}
