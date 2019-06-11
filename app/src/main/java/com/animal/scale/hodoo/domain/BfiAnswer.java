package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by SongSeokwoo on 2019-04-05.
 */
@Data
public class BfiAnswer implements Serializable {
    private int id;
    private String answer;
    private String answerImg;
}
