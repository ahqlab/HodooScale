package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.util.List;

public interface PetAccounts {

    interface View {
        public void setAdapter(List<PetBasicInfo> data);
    }

    interface Presenter {

        void initUserData(Context context);

        void getData();
    }
}
