package com.animal.scale.hodoo.domain;

import java.util.ArrayList;

import lombok.Data;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
@Data
public class PetUserSelectItem {
    private String title;
    private int selectNum = -1;
    private ArrayList<String> childItem;
}
