package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetBreed  implements Serializable {
    private int id;
    private String name;
}
