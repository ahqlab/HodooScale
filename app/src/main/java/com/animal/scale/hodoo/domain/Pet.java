package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Pet implements Serializable {

    private int petIdx;

    private String petGroupCode;

    private int basic;

    private int disease;

    private int physical;

    private int weight;

    private int sltQst;

    private String createDate;

    private int petType;

    private String fixWeight;
}
