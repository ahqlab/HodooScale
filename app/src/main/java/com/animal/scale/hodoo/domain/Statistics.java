package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Statistics implements Serializable {

    public Statistics(){

    }

    public Statistics(String theDay, float average) {
        this.theDay = theDay;
        this.average = average;
    }

    private String theHour;

    private String theDay;

    private String theWeek;

    private String theMonth;

    private String theYear;

    private String today;

    private float average;

}
