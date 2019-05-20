package com.animal.scale.hodoo.domain;

import com.kakao.auth.AuthType;

import lombok.Data;

@Data
public class KakaoAuthItem {

    private int textId;
    private int icon;
    private int contentDescId;
    private AuthType authType;

    public KakaoAuthItem(int textId, int icon, int contentDescId, AuthType authType) {
        this.textId = textId;
        this.icon = icon;
        this.contentDescId = contentDescId;
        this.authType = authType;
    }
}
