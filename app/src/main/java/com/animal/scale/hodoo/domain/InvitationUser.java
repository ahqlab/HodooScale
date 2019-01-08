package com.animal.scale.hodoo.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvitationUser implements Serializable {
    private int id;
    private int toUserIdx;
    private int fromUserIdx;
    private int state;
    private Object created;
    private String nickname;
    private String convertNickName;
}
