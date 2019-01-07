package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.message.ResultMessage;

import java.io.Serializable;

import lombok.Data;

@Data
public class FinPasswordResponse implements Serializable {

    public ResultMessage resultMessage;

    public User domain;
}
