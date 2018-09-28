package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public interface PetAccounts {

    interface View {
        public void setAdapter(List<PetAllInfos> data);
    }

    interface Presenter {

        void initUserData(Context context);

        void getData();
    }
}
