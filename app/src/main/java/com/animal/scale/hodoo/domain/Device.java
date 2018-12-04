package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class Device implements Serializable {

    private int deviceIdx;

    private String groupCode;

    private String serialNumber;

    private String connect;

    private String createDate;
}
