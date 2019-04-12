package com.animal.scale.hodoo.activity.home.activity;

import android.content.Context;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.animal.scale.hodoo.domain.WeightTip;

import java.util.List;

public interface HomeActivityIn {

    interface View{

        void setCustomPetListDialog(List<PetAllInfos> petAllInfos);

        void setCurcleImage(PetAllInfos info);

        void refreshBadge();

        void setPushCount ( int count );

        void moveLoginActivity ();

        void setFragment();

        void setTipOfCountry(WeightTip weightTips);
    }

    interface Presenter{

        void loadData(Context context);

        void chageCurcleImageOfSelectPet(PetAllInfos info);

        void getInvitationToServer();

        void loginCheck();

        void loadCustomPetListDialog();

        void getTipOfCountry(WeightTip weightTip);
    }
}
