/**
 * Created by SongSeokwoo on 2019.03.22
 */

package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.animal.scale.hodoo.R;

public class MeterageCup extends View implements View.OnTouchListener, Runnable {

    private String TAG = MeterageCup.class.getSimpleName();

    private int[] cupResources = {
            R.drawable.cup
    };
    private int[] meterageNumber = {
      0, 50, 100, 150, 200
    };
    private int fillValue = 0;
    private int topMargin = 100;
    private Bitmap bitmap;

    private int triangleWidth = 50;
    private int triangleHeight = 70;
    private int range = 0;
    private int width = 0;
    private int height = 0;

    private int textOffset = 0;


    private TouchCallback callback;

    private boolean initState = false;

    private int value = 0;

    public interface TouchCallback {
        void onResult( int value );
    }


    public MeterageCup(Context context) {
        this(context, null);
    }

    public MeterageCup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeterageCup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init () {
        setWillNotDraw(false);
        /* image settings (s) */
        bitmap = BitmapFactory.decodeResource(getResources(), cupResources[0]);
        /* image settings (e) */

        width = bitmap.getWidth();

        setMeasuredDimension(getMeasuredWidth(), bitmap.getHeight());
        height = bitmap.getHeight();
        fillValue = height;
        range = height / ( meterageNumber.length );
        this.setOnTouchListener(this);
    }

    public void setFillValue( int fillValue ) {
        this.fillValue = fillValue;
    }
    public void setMeterageNumber ( int[] meterageNumber ) {
        this.meterageNumber = meterageNumber;
        invalidate();
    }

    public void setCallback ( TouchCallback callback ) {
        this.callback = callback;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* 이미지 한개만 배치 */
        int xVal = (canvas.getWidth() / 2) - (width / 2);

        /* fill (s) */
        int fillY = height;
        fillY -= fillValue;//fill value zero settings

        Paint fillPaint = new Paint();
        fillPaint.setColor(Color.GRAY);

        Rect bounds = new Rect();
        Paint mTextPaint = new Paint();
        mTextPaint.getTextBounds(String.format("%d ml", meterageNumber[0]), 0, String.format("%d ml", meterageNumber[0]).length(), bounds);

        if ( textOffset == 0 )
            textOffset = bounds.height() * 3;

        if ( !initState ) {
            fillY += textOffset;
            initState = true;
        }

        canvas.drawRect(xVal, (height + topMargin) - fillY, xVal + width, height + topMargin, fillPaint);
//        canvas.drawRect(xVal, 0, xVal + width, 500, fillPaint);
        /* fill (e) */

        /* triangle (s) */
        Path trianglePath = new Path();
        Paint trianglePaint = new Paint();
        trianglePaint.setColor(ContextCompat.getColor(getContext(), R.color.mainRed));
        trianglePaint.setStyle(Paint.Style.FILL);

        trianglePath.moveTo(xVal - triangleWidth, height - fillY + (triangleHeight / 2) + topMargin);
        trianglePath.lineTo(xVal,  height - (triangleHeight / 2) - fillY + (triangleHeight / 2) + topMargin);
        trianglePath.lineTo(xVal - triangleWidth, height - triangleHeight - fillY + (triangleHeight / 2) + topMargin);
        trianglePath.close();
        canvas.drawPath(trianglePath, trianglePaint);
        /* triangle (e) */

        canvas.drawBitmap( bitmap, xVal, topMargin, null );
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);




        Paint mPaint = new Paint();

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        Paint strokePaint = new Paint();
        strokePaint.setColor(Color.DKGRAY);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(5f);
        strokePaint.setPathEffect(new DashPathEffect(new float[] {20,10}, 0));

        Path path = new Path();

        int y = 0;
        for (int i = 0; i < meterageNumber.length; i++) {
            if ( i == 0 )
                y = 0;
            else
                y += height / ( meterageNumber.length );



            /* text drawing (s) */
            canvas.drawText(String.format("%d ml", meterageNumber[i]), xVal + width + 10, i == meterageNumber.length - 1 ? topMargin + (height / ( meterageNumber.length )) : (topMargin + height) - y - ((int) (bounds.height() * 2)), textPaint);
//            canvas.drawText(String.format("%d ml", meterageNumber[i]), xVal + width + 10, (i + 1) * 50, textPaint);
            /* text drawing (e) */


            /* dash line drawing (s) */
            if ( i != 0   ) { //&& i != meterageNumber.length - 1
                path.moveTo(xVal, (int) ((topMargin + height) - y - (bounds.height() * 3)));
                path.lineTo(xVal + width, (int) ((topMargin + height) - y - (bounds.height() * 3)));

                canvas.drawPath(path, strokePaint);
            }
            /* dash line drawing (e) */

            Paint resultPaint = new Paint();
            resultPaint.setColor(ContextCompat.getColor(getContext(), R.color.mainRed));
            resultPaint.setStyle(Paint.Style.STROKE);
            resultPaint.setStrokeWidth(10f);


            Path resultPath = new Path();
            resultPath.moveTo(xVal, height - (triangleHeight / 2) - fillY + (triangleHeight / 2) + topMargin);
            resultPath.lineTo(xVal + width, height - (triangleHeight / 2) - fillY + (triangleHeight / 2) + topMargin);
            canvas.drawPath(resultPath, resultPaint);

        }


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int y  = (int) motionEvent.getY();
        fillValue = y - topMargin;
        if ( fillValue < height - ( range * (meterageNumber.length - 1) ) - textOffset) {
            fillValue = height - ( range * (meterageNumber.length - 1) ) - textOffset;
            value = meterageNumber[ meterageNumber.length - 1 ];
            invalidate();
            return true;
        } else if ( fillValue > height  - textOffset) {
            fillValue = height - textOffset;
            value = 0;
            invalidate();
            return true;
        }

        if ( motionEvent.getAction() == MotionEvent.ACTION_UP ) {
//
            int[] data = new int[meterageNumber.length];
            for (int i = 0; i < meterageNumber.length; i++) {
                data[i] = height - (range * i);
                Log.e(TAG, String.format("data[i] : %d", data[i]));
            }

            int target = y + topMargin;


            int near = 0;
            int min = Integer.MAX_VALUE;
            int selectCount = 0;
            for (int i = 0; i < data.length; i++) {
                int a = Math.abs((data[i] - y + topMargin));
                if (min > a) {
                    min = a;
                    near = data[i];
                    selectCount = i;
                }
            }
            if ( callback != null )
                callback.onResult( meterageNumber[selectCount] );
            value = meterageNumber[selectCount];
            fillValue = near - textOffset;

            invalidate();
            startThread();
        } else {
            invalidate();
        }

        return true;
    }
    private void startThread () {

    }

    public int getValue() {
        return value;
    }
    public void setValue( int in ) {

        int[] data = new int[meterageNumber.length];
        int position = 0;
        for (int i = 0; i < meterageNumber.length; i++) {
            if ( meterageNumber[i] == in ) {
                position = i;
            }
        }
        for (int i = 0; i < meterageNumber.length; i++) {
            data[i] = height - (range * i);
            Log.e(TAG, String.format("data[i] : %d", data[i]));
        }
        in = data[position];
        int near = 0;
        int min = Integer.MAX_VALUE;
        int selectCount = 0;
        for (int i = 0; i < data.length; i++) {
            int a = Math.abs((data[i] - in + topMargin));
            if (min > a) {
                min = a;
                near = data[i];
                selectCount = i;
            }
        }
        value = meterageNumber[selectCount];
        fillValue = in - textOffset;
        invalidate();
    }

    @Override
    public void run() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Log.e(TAG, String.format("bitmap height : %d", bitmap.getHeight()));
        setMeasuredDimension(getMeasuredWidth(), bitmap.getHeight() + topMargin + ( triangleHeight / 2 ));
    }
}
