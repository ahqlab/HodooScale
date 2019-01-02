package com.animal.scale.hodoo.activity.user.signup;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
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
        model.registUser(user, new SignUpModel.DomainCallBackListner<ResultMessageGroup>() {
            @Override
            public void doPostExecute(ResultMessageGroup resultMessageGroup) {
                if(resultMessageGroup.getResultMessage().equals(ResultMessage.DUPLICATE_EMAIL)){
                    view.showPopup("DUPLICATE_EMAIL");
                }else if(resultMessageGroup.getResultMessage().equals(ResultMessage.FAILED)){
                    view.showPopup("FAILED");
                }else if(resultMessageGroup.getResultMessage().equals(ResultMessage.SUCCESS)){
                    view.sendEmail(user.getEmail());
                }
            }
            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }
        });
    }

    @Override
    public void userCertifiedMailSend(String toMail) {
        model.userCertifiedMailSend(toMail, new SignUpModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                if ( result > 0 )
                    view.setProgress(false);
                    view.goNextPage();
            }
            @Override
            public void doPreExecute() {}
        });
    }

    @Override
    public void getAllCountry(int country) {
        countryModel.getAllCountry(country, new CommonModel.DomainListCallBackListner<Country>() {
            @Override
            public void doPostExecute(List<Country> d) {
                view.showCountry(d);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
