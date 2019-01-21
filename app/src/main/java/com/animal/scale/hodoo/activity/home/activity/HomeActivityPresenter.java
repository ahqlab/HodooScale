package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.ArrayList;
import java.util.List;

import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

public class HomeActivityPresenter implements HomeActivityIn.Presenter {

    private String TAG = HomeActivityPresenter.class.getSimpleName();

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

                    /* 이미 초대가 완료된 회원 정리 (s) */
                    for (int i = 0; i < users.size(); i++) {
                        if ( users.get(i).getState() > 0 ) {
                            for (int j = 0; j < saveUsers.size(); j++) {
                                if (
                                        users.get(i).getToUserIdx() == saveUsers.get(j).getToUserIdx() &&
                                                users.get(i).getFromUserIdx() == saveUsers.get(j).getFromUserIdx() &&
                                                users.get(i).getState() != saveUsers.get(j).getState()
                                        ) {
                                    saveUsers.remove(j);
                                    notiModel.setAllInvitationUsers( saveUsers );
                                }
                            }
                        }
                    }
                    /* 이미 초대가 완료된 회원 정리 (e) */

                    if ( DEBUG ) Log.e(TAG, "");
                } else if ( saveUsers == null ) {
                    for (int i = 0; i < users.size(); i++) {
                        if ( users.get(i).getState() > 0 )
                            users.remove(i);
                    }

                    notiModel.setAllInvitationUsers(users);
                }
            }

            @Override
            public void doPreExecute() {

            }
        });
    }


}
