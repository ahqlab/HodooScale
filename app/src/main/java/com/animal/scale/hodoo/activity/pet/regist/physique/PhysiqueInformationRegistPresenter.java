package com.animal.scale.hodoo.activity.pet.regist.physique;

import android.content.Context;
import android.widget.EditText;

import com.animal.scale.hodoo.domain.PetPhysicalInfo;

public class PhysiqueInformationRegistPresenter implements PhysiqueInformationRegistIn.Presenter{

    PhysiqueInformationRegistIn.View view;
    PhysiqueInformationRegistModel model;

    public PhysiqueInformationRegistPresenter(PhysiqueInformationRegistIn.View view){
        this.view = view;
        this.model = new PhysiqueInformationRegistModel();

    }

    @Override
    public void setNavigation() {
        view.setNavigation();
    }

    @Override
    public void getPhysiqueInformation(int petIdx) {
        model.getPhysiqueInformation(petIdx, new PhysiqueInformationRegistModel.getPhysiqueInformationResultListner() {
            @Override
            public void doPostExecute(PetPhysicalInfo petPhysicalInfo) {
                view.setDiseaseInfo(petPhysicalInfo);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void showRulerBottomDlg(EditText editText, String value) {
        view.showRulerBottomDlg(editText, value);
    }

    @Override
    public void deletePhysiqueInformation(int petIdx, int id) {
        model.deletePhysiqueInformation(petIdx, id, new PhysiqueInformationRegistModel.deleteInfoResultListner() {
            @Override
            public void doPostExecute(Integer result) {
                view.registPhysiqueInformation();
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain) {
        model.registPhysiqueInformation(petIdx, domain, new PhysiqueInformationRegistModel.registResultListner() {
            @Override
            public void doPostExecute(Integer result) {
                view.registResult(result);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }
}
