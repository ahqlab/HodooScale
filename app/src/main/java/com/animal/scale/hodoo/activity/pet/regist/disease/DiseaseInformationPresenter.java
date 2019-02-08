package com.animal.scale.hodoo.activity.pet.regist.disease;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.MealHistory;
import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.util.List;

public class DiseaseInformationPresenter implements DiseaseInformationIn.Presenter{


    DiseaseInformationIn.View view;

    DiseaseInformationModel model;

    public DiseaseInformationPresenter(DiseaseInformationIn.View view) {
        this.view = view;
        this.model = new DiseaseInformationModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void setNavigation() {
        view.setNavigation();
    }

    @Override
    public void getDiseaseInformation(int petIdx) {
        model.getDiseaseformation(petIdx, new CommonModel.DomainCallBackListner<PetChronicDisease>() {

            @Override
            public void doPostExecute(PetChronicDisease petChronicDisease) {
                view.setDiseaseInfo(petChronicDisease);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public List<PetChronicDisease> stringToListConversion(String diseaseName) {
        return model.stringToListConversion(diseaseName);
    }

    @Override
    public void deleteDiseaseInformation(int petIdx, int diseaseIdx) {
        model.deleteDiseaseformation(petIdx, diseaseIdx, new CommonModel.DomainCallBackListner<Integer>()  {
            @Override
            public void doPostExecute(Integer result) {
                view.registDiseaseInformation();
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void registDiseaseInformation(PetChronicDisease domain, int petIdx) {
        model.registDiseaseformation(domain, petIdx, new CommonModel.DomainCallBackListner<Integer>()  {
            @Override
            public void doPostExecute(Integer result) {
                view.nextStep(result);
            }
            @Override
            public void doPreExecute() {

            }
            @Override
            public void doCancelled() {

            }
        });
    }
}
