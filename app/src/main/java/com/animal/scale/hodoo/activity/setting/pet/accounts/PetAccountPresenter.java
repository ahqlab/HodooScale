package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Context;

import com.animal.scale.hodoo.domain.PetAllInfos;

import java.util.List;

public class PetAccountPresenter implements PetAccounts.Presenter{

    PetAccounts.View view;
    PetAccountModel model;

    public PetAccountPresenter(PetAccounts.View view) {
        this.view = view;
        this.model = new PetAccountModel();
    }

    @Override
    public void initUserData(Context context) {
        model.initUserData(context);
    }

    @Override
    public void getData() {
       model.getPetData(new PetAccountModel.asyncTaskListner() {
            @Override
            public void doPostExecute(List<PetAllInfos> data) {
                model.addRegistBtn(data);
                view.setAdapter(data);
            }
            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void saveCurrentIdx(int idx) {
        model.saveCurrentIdx(idx);
        view.reFreshData( model.getSelectedPetIdx() );
    }
}
