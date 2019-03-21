package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetRegistResult implements Serializable {
    private int code;
    private int petIdx;
}
