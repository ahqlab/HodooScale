package com.animal.scale.hodoo.activity.pet.regist.fragment.physique;

import android.content.Context;
import android.widget.EditText;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
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
        model.getPhysiqueInformation(petIdx, new CommonModel.DomainCallBackListner<PetPhysicalInfo>() {
            @Override
            public void doPostExecute(PetPhysicalInfo petPhysicalInfo) {
                view.setDiseaseInfo(petPhysicalInfo);
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
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void showRulerBottomDlg(EditText editText, String value) {
        view.showRulerBottomDlg(editText, value);
    }

    @Override
    public void deletePhysiqueInformation(int petIdx, int id) {
        model.deletePhysiqueInformation(petIdx, id, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                view.registPhysiqueInformation();
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
    public void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain) {
        model.registPhysiqueInformation(petIdx, domain, new CommonModel.CommonDomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(CommonResponce<Integer> d) {
                //view.nextStep(PetRegistActivity.WEIGHT_TYPE);
                view.registResult(d.getDomain());
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
    public int getUnitIdx() {
        return model.getUnitIdx();
    }
}
