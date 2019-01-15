package com.animal.scale.hodoo.activity.setting.device.feeder;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetAllInfos;

public class FeederOrderIn {

    interface View{

        void setPetAllInfo(PetAllInfos petAllInfos);

        void insertResult(Integer integer);
    }
    interface Presenter{
        void loadData(Context context);

        void getPetAllInfo();

        void insertOrder(float rer);
    }
}
