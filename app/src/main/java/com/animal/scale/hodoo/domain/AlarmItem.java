package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class AlarmItem implements Domain{

    private int id;

    private String name;

    private String koName;

    private String enName;
}
