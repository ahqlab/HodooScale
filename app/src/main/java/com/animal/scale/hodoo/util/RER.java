package com.animal.scale.hodoo.util;

import lombok.Data;

@Data
public class RER {

    public float weight;

    public float factor;

    public RER(float weight, float factor) {
        this.weight = weight;
        this.factor = factor;
    }

    public float getRER() {
        float rer = ((30 * weight) + 70) * factor;
        return rer;
    }
}
