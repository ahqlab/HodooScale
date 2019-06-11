package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetBasicInfo;

/**
 * Created by SongSeokwoo on 2019-04-09.
 */
public interface PetTypeIn {
    interface View {
        void setType( int petType );
        void setNavigation();
        void setBasicInfo(PetBasicInfo basicInfo);
    }
    interface Presenter {
        void getType( int petIdx );
        void loadData(Context context);
        void setNavigation();
        void getPetBasicInformation(String location, int petIdx);
    }
}
