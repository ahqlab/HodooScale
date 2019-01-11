package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public class HomeActivityPresenter implements HomeActivityIn.Presenter{

    public HomeActivityIn.View view;

    public HomeActivityModel model;

    private CommonNotificationModel notiModel;


    public HomeActivityPresenter(HomeActivityIn.View view){
        this.view = view;
        this.model = new HomeActivityModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
        notiModel = CommonNotificationModel.getInstance(context);
    }

    @Override
    public void loadCustomDropdownView() {
        model.setSpinner(new HomeActivityModel.DomainListCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(List<PetAllInfos> petAllInfos) {
                view.setCustomDropdownView(petAllInfos);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getSttingListMenu() {
        view.setListviewAdapter(model.getSettingList());
    }

    @Override
    public void setCurrentPetInfos(List<PetAllInfos> data) {
        view.setCurrentPetInfos(data);
    }

    @Override
    public void setCurcleImage(PetAllInfos info) {
        view.setCurcleImage(info);
    }

    @Override
    public void setNotiCount() {
//        notiModel.getInvitationBadgeCount();
        model.getInvitationCount(new CommonModel.DomainListCallBackListner<InvitationUser>() {
            @Override
            public void doPostExecute(List<InvitationUser> result) {
                List<InvitationUser> savedUsers = notiModel.getSavedinvitationUsers();
                for (int i = 0; i < savedUsers.size(); i++) {
                    for (int j = 0; j < result.size(); j++) {
                        if ( savedUsers.get(i).getToUserIdx() == result.get(j).getToUserIdx() && savedUsers.get(i).getFromUserIdx() == result.get(j).getFromUserIdx() ) {
                            savedUsers.remove(i);
                        }
                    }
                }
                for (int i = 0; i < savedUsers.size(); i++) {
                    notiModel.removeInvitationData(savedUsers.get(i).getToUserIdx(), savedUsers.get(i).getFromUserIdx());
                }
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
