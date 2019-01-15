package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

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

    }

    @Override
    public void setNotiCount() {
//        notiModel.getInvitationBadgeCount();
        model.getInvitationCount(new CommonModel.DomainListCallBackListner<InvitationUser>() {
            @Override
            public void doPostExecute(List<InvitationUser> result) {
                for (int i = 0; i < result.size(); i++) {
                    if ( result.get(i).getState() > 0 )
                        result.remove(i);
                }
                List<InvitationUser> savedUsers = notiModel.getSavedinvitationUsers();
                int count = savedUsers.size();

                if ( savedUsers.size() == 0 ) {
                    savedUsers = result;
                    notiModel.saveInvitationUsers(savedUsers);
                }
                view.refreshBadge();
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
