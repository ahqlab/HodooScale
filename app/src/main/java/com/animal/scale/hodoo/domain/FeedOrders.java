package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class FeedOrders implements Domain{

    private int orderIdx;

    private String groupCode;

    private int userIdx;

    private int feedIdx;

    private int petIdx;

    private float rer;

}
