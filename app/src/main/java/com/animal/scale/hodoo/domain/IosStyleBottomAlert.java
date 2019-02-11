package com.animal.scale.hodoo.domain;

import com.animal.scale.hodoo.custom.view.BottomDialog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class IosStyleBottomAlert {
    private int id;
    private String btnName;
    private BottomDialog.OnClickListener clickListener;
}
