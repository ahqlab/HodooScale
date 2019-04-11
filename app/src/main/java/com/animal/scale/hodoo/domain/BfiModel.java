package com.animal.scale.hodoo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Created by SongSeokwoo on 2019-04-05.
 */
@Data
public class BfiModel implements Serializable {
    private int id;
    private String question;
    private String answerIds;

    @SerializedName("answers")
    @Expose
    private List<BfiAnswer> answers;
    private int additionalId;

    private String title;
    private String info;
    private String image;
}
