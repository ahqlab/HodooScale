package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.List;

public interface HomeActivityIn {

    interface View{

        void setCustomDropdownView(List<PetAllInfos> petAllInfos);

        void setListviewAdapter(List<SettingMenu> settingList);

        void setCurrentPetInfos(List<PetAllInfos> data);

        void setCurcleImage(PetAllInfos info);

//        void resetNoti( int  );
    }

    interface Presenter{

        void loadData(Context context);

        void loadCustomDropdownView();

        void getSttingListMenu();

        void setCurrentPetInfos(List<PetAllInfos> data);

        void setCurcleImage(PetAllInfos info);

        void setNotiCount();
    }
}
