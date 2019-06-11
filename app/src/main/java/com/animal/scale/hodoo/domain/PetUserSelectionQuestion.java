package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class PetUserSelectionQuestion implements Domain {

    private int questionIdx;

    private int bodyFat;

    private int playTime;

    private int active;

    private String createDate;

}
