package com.animal.scale.hodoo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FirebaseInfo {
    private int type;
    private String data;
}
