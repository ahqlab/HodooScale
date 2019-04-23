package com.animal.scale.hodoo.domain;

import lombok.Data;

/**
 * Created by SongSeokwoo on 2019-04-23.
 */
@Data
public class WeightGoalChart implements Domain {
    private int id;

    private float currentWeight;

    private float weightGoal;

    private int petType;
}
