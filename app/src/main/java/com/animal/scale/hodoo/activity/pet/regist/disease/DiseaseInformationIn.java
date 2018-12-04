package com.animal.scale.hodoo.activity.pet.regist.disease;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.util.List;

public interface DiseaseInformationIn {

    interface View{

        void setNavigation();

        void setDiseaseInfo(PetChronicDisease petChronicDisease);

        void registDiseaseInformation();

        void nextStep(int result);
    }

    interface Presenter{

        void loadData(Context context);

        void setNavigation();

        void getDiseaseInformation(int petIdx);

        List<PetChronicDisease> stringToListConversion(String diseaseName);

        void deleteDiseaseInformation(int petIdx, int id);

        void registDiseaseInformation(PetChronicDisease domain, int petIdx);
    }
}
