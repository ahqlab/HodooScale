package com.animal.scale.hodoo.message;

import lombok.Getter;

public enum ResultMessage {

    FAILED(400), SUCCESS(200),
    // USER 관련 ERROR MESSAGE

    DUPLICATE_EMAIL(212), PASSWORD_UPDATE_FAILED(213), WITHDRAW_USER(214), NOT_FOUND_USER(215),

    // LOGIN RETURN ERROR MESSAGE
    NOT_FOUND_EMAIL(210), ID_PASSWORD_DO_NOT_MATCH(211), WAIT_INVITATION(216),
    // DIVICE 등록 관련
    DUPLICATE_DEVICE(300), NOT_FOUND_DEVICE(301),
    // 메일 관련 에러 메시지
    FAILED_TO_SEND_MAIL(510),

    ALREADY_REGISTERED(600);


    @Getter
    private final int name;

    private ResultMessage(int name) {
        this.name = name;
    }
}
