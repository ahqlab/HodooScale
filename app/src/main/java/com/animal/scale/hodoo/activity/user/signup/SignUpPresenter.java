package com.animal.scale.hodoo.activity.user.signup;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Country;
import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.message.ResultMessage;
import com.animal.scale.hodoo.util.CountryModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

class SignUpPresenter implements SignUpIn.Presenter {

    Context context;

    SignUpIn.View view;

    SignUpModel model;

    CountryModel countryModel;

    public SignUpPresenter(SignUpIn.View view) {
        this.view = view;
        this.model = new SignUpModel();
        countryModel = new CountryModel();
    }

    @Override
    public void loadData(Context context) {
        this.context = context;
        model.loadData(context);
    }

    @Override
    public void registUser(final User user) {
        model.registUser(user, new CommonModel.ObjectCallBackListner<CommonResponce<User>>() {
            @Override
            public void doPostExecute(CommonResponce<User> commonResponce) {
                if(commonResponce.getResultMessage().equals(ResultMessage.DUPLICATE_EMAIL)){
                    view.showPopup("DUPLICATE_EMAIL");
                }else if(commonResponce.getResultMessage().equals(ResultMessage.FAILED)){
                    view.showPopup("FAILED");
                }else if(commonResponce.getResultMessage().equals(ResultMessage.SUCCESS)){
                    view.sendEmail(user.getEmail());
                    return;
                }
                view.setProgress(false);
            }
            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void userCertifiedMailSend(String toMail) {
        model.userCertifiedMailSend(toMail, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                if ( result != null || result > 0 )
                    view.setProgress(false);
                    view.goNextPage();
            }
            @Override
            public void doPreExecute() {}

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void getAllCountry(int country) {
        countryModel.getAllCountry(country, new CommonModel.DomainListCallBackListner<Country>() {
            @Override
            public void doPostExecute(List<Country> d) {
                view.setCountry(d);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {
                
            }
        });
    }
}
