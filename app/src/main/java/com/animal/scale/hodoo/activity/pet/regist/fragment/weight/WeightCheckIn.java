package com.animal.scale.hodoo.activity.pet.regist.fragment.weight;

import android.content.Context;

import com.animal.scale.hodoo.domain.BfiModel;
import com.animal.scale.hodoo.domain.PetWeightInfo;

import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-05.
 */
public interface WeightCheckIn {
    interface View{

        void setNavigation();

        void setDomain(PetWeightInfo petWeightInfo);

        void registWeightInformation();

        void registResult(Integer integer);

        void setQuestion(List<BfiModel> bfis);

        //void setViewFlipper();
    }
    interface Presenter{

        void loadData(Context context);

        void setNavigation();

        void getWeightInformation(int petIdx);

        void deleteWeightInfo(int petIdx, int id);

        void registWeightInfo(int petIdx, PetWeightInfo domain);

        void getBfiQuestion ( String location, int type );

        //void setViewFlipper();
    }
}
