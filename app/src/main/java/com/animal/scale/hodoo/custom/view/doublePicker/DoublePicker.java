package com.animal.scale.hodoo.custom.view.doublePicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.NumberPicker;

import com.animal.scale.hodoo.R;

public class DoublePicker extends NumberPicker {

    private int decimals;
    private NumberPicker integerPicker;
    private NumberPicker fractionPicker;


    public DoublePicker(Context context) {
        super(context);
    }

    @Override
    public void setValue(int value) {

        super.setValue(Integer.parseInt("." + value));
    }
}
