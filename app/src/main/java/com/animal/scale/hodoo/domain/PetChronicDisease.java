package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class PetChronicDisease {

    public PetChronicDisease() {
    }

    public PetChronicDisease(int petId, String diseaseName) {
        this.petId = petId;
        this.diseaseName = diseaseName;
    }

    private int id;

    private int petId;

    private String diseaseName;
}
