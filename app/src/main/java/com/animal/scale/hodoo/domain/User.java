package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

    public User(String email) {
        this.email = email;
    }

    public User() {
    }

    private int userIdx;

    private String email;

    private String password;

    private String passwordCheck;

    private String nickname;

    private String sex;

    private String from;

    private String createDate;
    //joinColumn
    private String groupCode;

}
