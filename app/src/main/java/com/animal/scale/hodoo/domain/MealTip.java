package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class MealTip implements Domain {

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
