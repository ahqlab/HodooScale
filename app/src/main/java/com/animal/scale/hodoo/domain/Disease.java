package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class Disease {

    private String name;

    private boolean checked = false;

    private int checkedCount;

    public Disease(String name) {
        this.name = name;
    }
    public Disease(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

}
