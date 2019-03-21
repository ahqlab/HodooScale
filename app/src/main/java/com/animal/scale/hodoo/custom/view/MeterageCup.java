package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;

public class MeterageCup extends View {

    private String TAG = MeterageCup.class.getSimpleName();

    private int[] cupResources = {
            R.drawable.cup
    };
    private int[] meterageNumber = {
      0, 50, 100, 150
    };

    public MeterageCup(Context context) {
        this(context, null);
    }

    public MeterageCup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeterageCup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* 이미지 한개만 배치 */
        int count = 1;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), cupResources[0]);
        canvas.drawBitmap( bitmap, 0, 0, null );
        int height = canvas.getHeight();
        Log.e(TAG, String.format("canvas height : %d", canvas.getHeight()));
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        for (int i = 0; i < meterageNumber.length; i++) {
            canvas.drawText(String.format("%d ml", meterageNumber[i]), 50, height / count, textPaint);
            count++;
        }


    }


}
