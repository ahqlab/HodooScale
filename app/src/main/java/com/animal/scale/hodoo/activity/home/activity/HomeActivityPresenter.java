package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.activity.user.invitation.Invitation;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.CommonNotificationModel;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        model.setSpinner(new CommonModel.DomainListCallBackListner<PetAllInfos>() {
            @Override
            public void doPostExecute(List<PetAllInfos> petAllInfos) {
                view.setCustomDropdownView(petAllInfos);
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

                statement : if ( saveUsers != null && users != null ) {
                    List<InvitationUser> origin = users;
                    /* 데이터베이스 초대 완료된 회원 삭제 (s) */
                    Iterator<InvitationUser> iterator = users.iterator();
                    while (iterator.hasNext()) {
                        InvitationUser user = iterator.next();
                        if ( user.getState() > 0 )
                            iterator.remove();
                    }
                    /* 데이터베이스 초대 완료된 회원 삭제 (e) */

                    if ( saveUsers.size() == 0 ) {
                        notiModel.setAllInvitationUsers( users );
                        break statement;
                    }
                    if ( users.size() == 0 ) {
                        notiModel.setAllInvitationUsers( users );
                        break statement;
                    }

                    List<InvitationUser> effectiveUsers = new ArrayList<>();

                    for (int i = 0; i < saveUsers.size(); i++) {
                        for (int j = 0; j < users.size(); j++) {
                            if ( saveUsers.get(i).getToUserIdx() == users.get(j).getToUserIdx() && saveUsers.get(i).getFromUserIdx() == users.get(j).getFromUserIdx() )
                                effectiveUsers.add(saveUsers.get(i));
                        }
                    }
                    List<InvitationUser> removeUsers = new ArrayList<>();
                    removeUsers.addAll(saveUsers);
                    removeUsers.removeAll(effectiveUsers);
                    if ( DEBUG ) Log.e(TAG, "");

                    Iterator<InvitationUser> targets = saveUsers.iterator();
                    Iterator<InvitationUser> removeItems = removeUsers.iterator();
                    while (targets.hasNext()) {
                        InvitationUser target = targets.next();
                        while (removeItems.hasNext()) {
                            InvitationUser item = removeItems.next();
                            if ( target.getToUserIdx() == item.getToUserIdx() && target.getFromUserIdx() == item.getFromUserIdx() ) {
                                targets.remove();
                            }
                        }
                    }

                    notiModel.setAllInvitationUsers( saveUsers );







//                    List<InvitationUser> big, small, tempUsers = new ArrayList<>();
//                    if ( saveUsers.size() > users.size() ) {
//                        big = saveUsers;
//                        small = users;
//                    } else {
//                        small = saveUsers;
//                        big = users;
//                    }
//
//                    for (int i = 0; i < big.size(); i++) {
//                        for (int j = 0; j < small.size(); j++) {
//                            if ( big.get(i).getToUserIdx() != small.get(j).getToUserIdx() && big.get(i).getFromUserIdx() != small.get(i).getFromUserIdx() ) {
//                                tempUsers.add(big.get(i));
//                            }
//                        }
//                    }

                    /* 데이터베이스에 없는 회원 정리 (s) */
//                    List<InvitationUser> tempUsers = new ArrayList<>();
//                    for (int i = 0; i < users.size(); i++) {
//                        tempUsers.add(InvitationUser.builder().id(users.get(i).getId()).toUserIdx(users.get(i).getToUserIdx()).fromUserIdx(users.get(i).getFromUserIdx()).state(users.get(i).getState()).build());
//                    }
//                    List<InvitationUser> removeUsers = saveUsers;
//                    removeUsers.removeAll(tempUsers);
//
//                    Iterator<InvitationUser> iterator = removeUsers.iterator();
//                    Iterator<InvitationUser> iter2 = saveUsers.iterator();
//                    int count = 0;
//                    while (iterator.hasNext()) {
//                        InvitationUser removeUser = iterator.next();
//                        while (iter2.hasNext()) {
//                            InvitationUser target = iter2.next();
//                            if ( removeUser.getFromUserIdx() == target.getFromUserIdx() &&
//                                    removeUser.getToUserIdx() == target.getToUserIdx() ) {
//                                iter2.remove();
//                            }
//                        }
//                    }
//
//                    /* 데이터베이스에 없는 회원 정리 (e) */
//
//                    /* 이미 초대가 완료된 회원 정리 (s) */
//                    for (int i = 0; i < users.size(); i++) {
//                        if ( users.get(i).getState() > 0 ) {
//                            for (int j = 0; j < saveUsers.size(); j++) {
//                                if (
//                                        users.get(i).getToUserIdx() == saveUsers.get(j).getToUserIdx() &&
//                                                users.get(i).getFromUserIdx() == saveUsers.get(j).getFromUserIdx()
//                                        ) {
//                                    saveUsers.remove(j);
//                                    notiModel.setAllInvitationUsers( saveUsers );
//                                }
//                            }
//                        }
//                    }
//                    /* 이미 초대가 완료된 회원 정리 (e) */
//                    if ( saveUsers.size() == 0 ) {
//                        notiModel.setAllInvitationUsers(saveUsers);
//                    }


                } else if ( saveUsers == null ) {

                    Iterator<InvitationUser> iter = users.iterator();
                    while ( iter.hasNext() ) {
                        InvitationUser user = iter.next();
                        if ( user.getState() > 0 )
                            iter.remove();
                    }
                    notiModel.setAllInvitationUsers(users);
                }
                view.refreshBadge();
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
