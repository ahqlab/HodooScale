package com.animal.scale.hodoo.activity.setting.account.info;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.Country;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.util.CountryModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ChangeUserInfoPresenter implements ChangeUserInfoIn.Presenter{

    ChangeUserInfoIn.View view;

    ChangeUserInfoModel model;
    CountryModel countryModel;

    public ChangeUserInfoPresenter(ChangeUserInfoIn.View view){
        this.view = view;
        this.model = new ChangeUserInfoModel();
        countryModel = new CountryModel();
    }

    @Override
    public void initLoadData(Context context) {
        model.initLoadData(context);
    }

    @Override
    public void initUserData() {
        model.initUserData(new CommonModel.DomainCallBackListner<User>() {
            @Override
            public void doPostExecute(User user) {
                view.setUserInfo(user);
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
    public void updateBasicInfo(User user) {
        model.updateBasicInfo(user, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.showResultPopup(integer);
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
    public void getCountry(int language) {
        countryModel.getAllCountry(language, new CommonModel.DomainListCallBackListner<Country>() {
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
