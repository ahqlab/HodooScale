package com.animal.scale.hodoo.activity.setting.pet.accounts;

public class PetAccountPresenter implements PetAccounts.Presenter{

    PetAccounts.View view;
    PetAccountModel model;

    public PetAccountPresenter(PetAccounts.View view) {
        this.view = view;
        this.model = new PetAccountModel();
    }
}
