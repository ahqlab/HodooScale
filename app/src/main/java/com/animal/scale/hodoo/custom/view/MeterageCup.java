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
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.animal.scale.hodoo.R;

public class MeterageCup extends View implements View.OnTouchListener, Runnable {

    private String TAG = MeterageCup.class.getSimpleName();

    private int[] cupResources = {
            R.drawable.cup
    };
    private int[] meterageNumber = {
      0, 50, 100, 150, 200
    };
    private String[] meterageStr = {
      "0", "1/4", "2/4", "3/4", "4/4"
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
    private String valueStr = "1/4";

    private int fillColor = ContextCompat.getColor(getContext(), R.color.meterage_cup_color);

    private int alpha = 255;

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
        range = height / ( meterageStr.length );
        this.setOnTouchListener(this);
    }

    public void setFillValue( int fillValue ) {
        this.fillValue = fillValue;
    }
    public void setMeterageNumber ( int[] meterageNumber ) {
        this.meterageNumber = meterageNumber;
        range = height / ( meterageNumber.length );
        invalidate();
    }

    public void setFillColor ( int fillColor ) {
        this.fillColor = fillColor;
        invalidate();
    }

    public void setFillColor ( int fillColor, int percent ) {
        this.fillColor = fillColor;
        this.alpha = 255 * percent / 100;
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
        fillPaint.setColor(fillColor);
        fillPaint.setAlpha(alpha);

        Rect bounds = new Rect();
        Paint mTextPaint = new Paint();
        mTextPaint.getTextBounds(meterageStr[0], 0, meterageStr[0].length(), bounds);

        if ( textOffset == 0 )
            textOffset = bounds.height() * 3;

        if ( !initState ) {
            fillY += textOffset;
            initState = true;
        }

//        RectF oval1 = new RectF(xVal, (height + topMargin) - fillY, xVal + width,(height + topMargin) - fillY + 80);
//        canvas.drawOval(oval1, fillPaint);

        canvas.drawRect(xVal, (height + topMargin) - fillY, xVal + width, height + topMargin, fillPaint);
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
        for (int i = 0; i < meterageStr.length; i++) {
            if ( i == 0 )
                y = 0;
            else
                y += height / ( meterageStr.length );

            /* text drawing (s) */
            canvas.drawText(meterageStr[i], xVal + width + 10, i == meterageStr.length - 1 ? topMargin + (height / ( meterageStr.length )) : (topMargin + height) - y - ((int) (bounds.height() * 2)), textPaint);
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

        getParent().requestDisallowInterceptTouchEvent(true);
        
        if ( view instanceof ScrollView) {
            Log.e(TAG, "scroll view touch");
        }
        int y  = (int) motionEvent.getY();
        fillValue = y - topMargin;
        if ( fillValue < height - ( range * (meterageStr.length - 1) ) - textOffset) {
            fillValue = height - ( range * (meterageStr.length - 1) ) - textOffset;
            valueStr = meterageStr[ meterageStr.length - 1 ];
            value = 100 / (meterageStr.length - 1) * meterageStr.length - 1;
            if ( callback != null )
                callback.onResult( 100 / (meterageStr.length - 1) * (meterageStr.length - 1) );
            invalidate();
            return true;
        } else if ( fillValue > height  - textOffset) {
            fillValue = height - textOffset;
            value = 0;
            if ( callback != null )
                callback.onResult( 100 / (meterageStr.length - 1) * value );
            invalidate();
            return true;
        }

        if ( motionEvent.getAction() == MotionEvent.ACTION_UP ) {
            int[] data = new int[meterageStr.length];
            for (int i = 0; i < meterageStr.length; i++) {
                data[i] = height - (range * i);
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
            Log.e(TAG, String.format("result : %d, calcu : %d selectCount : %d, length : %d", (100 / meterageStr.length - 1) * selectCount, (100 / meterageStr.length - 1), selectCount, meterageStr.length - 1));
            if ( callback != null )
                callback.onResult( 100 / (meterageStr.length - 1) * selectCount );
            value = 100 / (meterageStr.length - 1) * selectCount;
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

        int[] data = new int[meterageStr.length];
        int position = 0;
        for (int i = 0; i < meterageStr.length; i++) {
            if ( 100 / (meterageStr.length - 1) * i == in ) {
                position = i;
            }
        }
        for (int i = 0; i < meterageNumber.length; i++) {
            data[i] = height - (range * i);
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
        valueStr = meterageStr[selectCount];
        fillValue = in - textOffset;
        invalidate();
    }

    @Override
    public void run() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), bitmap.getHeight() + topMargin + ( triangleHeight / 2 ));
    }
}
