package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.message.ResultMessage;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultMessageGroup implements Serializable {

    public Object domain;

    public ResultMessage resultMessage;

    @Override
    public String toString() {
        return "ResultMessageGroup{" +
                "domain=" + domain +
                ", resultMessage=" + resultMessage +
                '}';
    }
}
