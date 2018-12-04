package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Spinner;

public class spinner extends android.support.v7.widget.AppCompatSpinner {
    public spinner(Context context) {
        this(context, null);
    }

    public spinner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public spinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
