package com.animal.scale.hodoo.activity.pet.regist.fragment.disease;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetChronicDisease;

/**
 * Created by SongSeokwoo on 2019-04-02.
 */
public interface DiseaseInfomationIn {
    interface View {
        void setDiseaseInfo( PetChronicDisease petChronicDisease );
        void setNavigation ();
        void registDiseaseInformation();
        void nextStep(int result);
    }
    interface Presenter {
        void initData(Context context);
        void getDiseaseInformation(int petIdx);
        void registDiseaseInformation(PetChronicDisease domain, int petIdx);
        void deleteDiseaseInformation(int petIdx, int id);
    }
}
