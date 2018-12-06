package com.animal.scale.hodoo.activity.pet.regist.weight;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetWeightInfo;

public interface WeightCheckIn {

    interface View{

        void setNavigation();

        void setDomain(PetWeightInfo petWeightInfo);

        void registWeightInformation();

        void registResult(Integer integer);

        //void setViewFlipper();
    }
    interface Presenter{

        void loadData(Context context);

        void setNavigation();

        void getWeightInformation(int petIdx);

        void deleteWeightInfo(int petIdx, int id);

        void registWeightInfo(int petIdx, PetWeightInfo domain);

        //void setViewFlipper();
    }
}
