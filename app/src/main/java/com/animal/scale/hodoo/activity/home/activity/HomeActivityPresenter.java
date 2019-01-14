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
                List<InvitationUser> bigUsers = new ArrayList<>();
                List<InvitationUser> smallUsers = new ArrayList<>();
                List<InvitationUser> tempUsers = new ArrayList<>();

                if ( result.size() > savedUsers.size() ) {
                    bigUsers = result;
                    smallUsers = savedUsers;
                } else {
                    smallUsers = result;
                    bigUsers = savedUsers;
                }
                /* 서버에 저장되어 있는 유저와 크기가 다를 경우 (s) */
                if ( bigUsers.size() != smallUsers.size() && smallUsers.size() > 0 ) {
                    for (int i = 0; i < bigUsers.size(); i++) {
                        for (int j = 0; j < smallUsers.size(); j++) {
                            if ( bigUsers.get(i).getToUserIdx() == smallUsers.get(j).getToUserIdx() &&
                                    bigUsers.get(i).getFromUserIdx() == smallUsers.get(j).getFromUserIdx() &&
                                    bigUsers.get(i).getState() > 0) {
                                continue;
                            }
                            tempUsers.add(bigUsers.get(i));
                        }
                    }
                    notiModel.saveInvitationUsers(tempUsers);
                    view.refreshBadge();
                } else if ( bigUsers.size() != smallUsers.size() && smallUsers.size() == 0  ) {
                    notiModel.saveInvitationUsers(bigUsers);
                    view.refreshBadge();
                }
                /* 서버에 저장되어 있는 유저와 크기가 다를 경우 (e) */

                int badgeCount = notiModel.getBadgeCount();
                int count;
                if ( badgeCount > 0 ) {
                    count = badgeCount - notiModel.getInvitationBadgeCount();
                } else {
                    count = 0;
                }

                /* 배지 셋팅 (s) */

                model.setNotiCount(count <= 0 ? 0 : count + 1);
                view.setPushCount( count );
                /* 배지 셋팅 (e) */

            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
