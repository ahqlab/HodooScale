package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Statistics implements Serializable {

    private String theHour;

    private String theDay;

    private String theWeek;

    private String theMonth;

    private String theYear;

    private int average;

}
