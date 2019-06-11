package com.animal.scale.hodoo.activity.pet.regist.fragment.disease;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.PetChronicDisease;

/**
 * Created by SongSeokwoo on 2019-04-02.
 */
public class DiseaseInfomationPresenter implements DiseaseInfomationIn.Presenter {
    private DiseaseInfomationFragment view;
    private Context context;
    private DiseaseInformationModel model;

    DiseaseInfomationPresenter ( DiseaseInfomationFragment view ) {
        this.view = view;
        model = new DiseaseInformationModel();
    }

    @Override
    public void initData(Context context) {
        this.context = context;
        model.loadData(context);
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
}
