package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

    public User() {}

    public User(String email) {
        this.email = email;
    }

    private int userIdx;

    private String email;

    private String password;

    private String passwordCheck;

    private String nickname;

    private String sex;

    private int country;

    private String pushToken;

    private String createDate;
    //joinColumn
    private String groupCode;

    private int userCode;

}
