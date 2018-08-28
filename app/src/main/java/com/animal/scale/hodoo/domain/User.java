package com.animal.scale.hodoo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable{

    public User(String email) {
        this.email = email;
    }
    public User() {
    }

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("passwordCheck")
    @Expose
    private String passwordCheck;

    @SerializedName("nickname")
    @Expose
    private String nickname;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("from")
    @Expose
    private String from;

    @SerializedName("groupId")
    @Expose
    private String groupId;

    @SerializedName("createDate")
    @Expose
    private String createDate;


}
