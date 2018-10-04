package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetChronicDisease implements Serializable{

    public PetChronicDisease() {
    }

    public PetChronicDisease(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    private int id;

    private String diseaseName;
}
