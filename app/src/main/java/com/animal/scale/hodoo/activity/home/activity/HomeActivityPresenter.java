package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.ArrayList;
import java.util.List;

public class HomeActivityPresenter implements HomeActivityIn.Presenter {

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
    public void getInvitationToServer() {
        model.getInvitationCount(new CommonModel.DomainListCallBackListner<InvitationUser>() {
            @Override
            public void doPostExecute(List<InvitationUser> users) {
                List<InvitationUser> saveUsers = notiModel.getInvitationUsers();


                if ( saveUsers != null && users != null ) {
                    List<InvitationUser> big, small, tempUsers = new ArrayList<>();
                    if ( saveUsers.size() > users.size() ) {
                        big = saveUsers;
                        small = users;
                    } else {
                        small = saveUsers;
                        big = users;
                    }

                    for (int i = 0; i < big.size(); i++) {
                        for (int j = 0; j < small.size(); j++) {
                            if ( big.get(i).getToUserIdx() != small.get(j).getToUserIdx() && big.get(i).getFromUserIdx() != small.get(i).getFromUserIdx() ) {
                                tempUsers.add(big.get(i));
                            }
                        }
                    }
                } else if ( saveUsers == null ) {
                    notiModel.setAllInvitationUsers(users);
                }
            }

            @Override
            public void doPreExecute() {

            }
        });
    }


}
