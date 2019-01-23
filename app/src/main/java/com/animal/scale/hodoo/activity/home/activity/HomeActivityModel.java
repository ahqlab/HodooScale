package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.invitation.Invitation;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.InvitationUser;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class HomeActivityModel extends CommonModel{

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context){
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void setSpinner(final DomainListCallBackListner<PetAllInfos> domainListCallBackListner) {
        Call<List<PetAllInfos>> call = NetRetrofit.getInstance().getPetBasicInfoService().aboutMyPetList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AbstractAsyncTaskOfList<PetAllInfos>() {
            @Override
            protected void doPostExecute(List<PetAllInfos> petAllInfos) {
                domainListCallBackListner.doPostExecute(petAllInfos);
            }

            @Override
            protected void doPreExecute() {
                domainListCallBackListner.doPreExecute();
            }
        }.execute(call);
    }

    public List<SettingMenu> getSettingList() {
        final List<SettingMenu> settingMenus = new ArrayList<SettingMenu>();
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_account_icon_50_50, "내 계정"));
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_notice_icon_50_50, "공지사항"));
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_introduce_icon_50_50, "호두 스케일 소개"));
        settingMenus.add(new SettingMenu(R.drawable.setting_middle_data_reset_icon_50_50, "데이터 초기화"));
        settingMenus.add(new SettingMenu(R.drawable.device_setting_middle_icon_50_50, "기기 설정"));
        settingMenus.add(new SettingMenu(R.drawable.setting_user_icon_50_50, "사용자 그룹 관리"));
        settingMenus.add(new SettingMenu(R.drawable.setting_pet_icon_50_50, "펫 관리"));
        return settingMenus;
    }

    public void getInvitationCount( final HomeActivityModel.DomainListCallBackListner<InvitationUser> callback ) {
        int idx = mSharedPrefManager.getIntExtra(SharedPrefVariable.USER_UNIQUE_ID);
        Call<List<InvitationUser>> call = NetRetrofit.getInstance().getInvitationService().getInvitationUser(idx);
        new AbstractAsyncTaskOfList<InvitationUser>() {
            @Override
            protected void doPostExecute(List<InvitationUser> users) {
//                for (int i = 0; i < users.size(); i++)
//                    if ( users.get(i).getState() > 0 )
//                        users.remove(i);


                callback.doPostExecute(users);
            }

            @Override
            protected void doPreExecute() {

            }
        }.execute(call);
    }
    public void setNotiCount ( int count ) {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, count);
    }
}
