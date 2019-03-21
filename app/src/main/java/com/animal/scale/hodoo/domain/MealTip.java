package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.base.FragmentTip;

import lombok.Data;

@Data
public class MealTip extends FragmentTip implements Domain {

    public MealTip() {

    }

    public MealTip(String country) {
        this.language = country;
    }

    private int tipIdx;

    private String language;

    private String title;

    private String content;


}
