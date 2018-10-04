package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchParam implements Serializable {

    public SearchParam(String searchWord) {
        this.searchWord = searchWord;
    }

    private String searchWord;
}
