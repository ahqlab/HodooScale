package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchHistory implements Serializable {

    public SearchHistory() {
    }

    public SearchHistory(String feedName, String createDate) {
        this.feedName = feedName;
        this.createDate = createDate;
    }

    private int id;

    private String feedName;

    private int feedIdx;

    private int userIdx;

    private String createDate;

    public SearchHistory(String feedName, int userIdx, int feedIdx, String createDate) {
        this.feedName = feedName;
        this.userIdx = userIdx;
        this.feedIdx = feedIdx;
        this.createDate = createDate;
    }
}
