package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.animal.scale.hodoo.R;

public class CircleTextView extends android.support.v7.widget.AppCompatTextView {
    private int mWidth = 0;
    private int mHeight = 0;
    private int mRadius = 0;
    public CircleTextView(Context context) {
        this(context, null);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.mainRed));
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mWidth = getMeasuredHeight() - 15;
        mHeight = getMeasuredHeight();
        mRadius = mWidth / 2;
        setPadding(mWidth + 20, 0, 0,0);
        invalidate();
    }
}
