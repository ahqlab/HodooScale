package com.animal.scale.hodoo.domain;

import lombok.Data;

@Data
public class Notice implements Domain{

    private int noticeIdx;

    private String title;

    private String content;

    private String lastModified;

    private String createDate;

}
