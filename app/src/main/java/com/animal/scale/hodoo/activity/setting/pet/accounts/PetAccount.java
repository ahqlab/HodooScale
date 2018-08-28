package com.animal.scale.hodoo.activity.setting.pet.accounts;

import lombok.Data;

@Data
public class PetAccount {

    public PetAccount(String name, String accountType) {
        this.name = name;
        this.accountType = accountType;
    }

    public PetAccount(int icon, String name, String accountType) {
        this.icon = icon;
        this.name = name;
        this.accountType = accountType;
    }

    private int icon;

    private String name;

    private String accountType;
}
