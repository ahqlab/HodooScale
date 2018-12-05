package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class MealHistory implements Serializable {

    private int historyIdx;

    private String groupId;

    private int userIdx;

    private int feedIdx;

    private int petIdx;

    private float calorie;

    private int unitIndex;

    private String unitString;

    private String createDate;

}
