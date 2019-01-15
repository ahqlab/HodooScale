package com.animal.scale.hodoo.message;

import lombok.Getter;

public enum ResultMessage {
    //공통 ERROR MESSAGE
    FAILED(0),
    SUCCESS(1),
    //USER 관련 ERROR MESSAGE
    NOT_FOUND_EMAIL(10),
    ID_PASSWORD_DO_NOT_MATCH(11),
    DUPLICATE_EMAIL(12),
    WITHDRAW_USER(14);

    @Getter
    private final int name;

    private ResultMessage(int name) {
        this.name = name;
    }
}
