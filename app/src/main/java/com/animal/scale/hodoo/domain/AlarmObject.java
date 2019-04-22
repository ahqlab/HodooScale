package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by SongSeokwoo on 2019-04-18.
 */
@Data
@Builder
@AllArgsConstructor
public class AlarmObject implements Serializable {
    private int idx;
    private int userIdx;
    private int number;
}
