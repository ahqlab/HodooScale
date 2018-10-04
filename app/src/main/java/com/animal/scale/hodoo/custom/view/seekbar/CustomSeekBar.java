package com.animal.scale.hodoo.custom.view.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.animal.scale.hodoo.R;

import java.util.ArrayList;

public class CustomSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    private TextPaint mTextPaint;

    private int mThumbSize;

    private ArrayList<ProgressItem> mProgressItemsList;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mThumbSize = getResources().getDimensionPixelSize(R.dimen.thumb_size);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(20);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void init() {
        /* 뷰의 크기에 영향을 받으며 아래와 같이 패딩을 설정하면 캔버스를 넓게 사용할 수 있습니다. */
        //this.setPadding(0, 100, 0, 100);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initData(ArrayList<ProgressItem> progressItemsList) {
        this.mProgressItemsList = progressItemsList;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {

        if (mProgressItemsList.size() > 0) {
            int progressBarWidth = getWidth();
            int progressBarHeight = getHeight();
            int thumboffset = getThumbOffset();
            int lastProgressX = 0;
            int progressItemWidth, progressItemRight;
            for (int i = 0; i < mProgressItemsList.size(); i++) {

                ProgressItem progressItem = mProgressItemsList.get(i);
                Paint progressPaint = new Paint();
                progressPaint.setColor(getResources().getColor(progressItem.color));
                progressItemWidth = (int) (progressItem.progressItemPercentage * progressBarWidth / 100);
                progressItemRight = lastProgressX + progressItemWidth;
                // for last item give right to progress item to the width
                if (i == mProgressItemsList.size() - 1
                        && progressItemRight != progressBarWidth) {
                    progressItemRight = progressBarWidth;
                }
                Rect progressRect = new Rect();
                progressRect.set(lastProgressX, thumboffset / 2, progressItemRight, progressBarHeight - thumboffset / 2);
                canvas.drawRect(progressRect, progressPaint);
                lastProgressX = progressItemRight;
            }
            super.onDraw(canvas);
            setProgressOnThumb(canvas);
            drwaLine(canvas);
        }

    }

    private void drwaLine(Canvas canvas) {
        /* 테스트1 */
        /* 시크바 라인 관련 */
        Paint paint = new Paint();
        paint.setColor(0xffff7700);
        paint.setStrokeWidth(11);

        //paint = new Paint();
        //paint.setColor(0xffff7700);

        //paint.setStrokeWidth(2);

        /*canvas.drawLine(
                0,100,0, 0, paint);
        canvas.drawLine(
                100,100,100, 0, paint);
        canvas.drawLine(
                200,100,200, 0, paint);
        canvas.drawLine(
                300,100,300, 0, paint);
        canvas.drawLine(
                400,100,400, 0, paint);*/



              canvas.drawLine(
                0,0,10, 100, paint);
        canvas.drawLine(
                100,0,100, 100, paint);
        canvas.drawLine(
                200,0,200, 100, paint);
        canvas.drawLine(
                300,0,300, 100, paint);
        canvas.drawLine(
                400,0,400, 100, paint);



       /* *//* 테스트2 *//*
        *//* 패스 관련 *//*
        paint = new Paint();
        paint.setColor(0xffff7700);
        paint.setStrokeWidth(11);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(600, 600);
        path.lineTo(800, 800);
        path.close();

        canvas.drawPath(path, paint);*/
    }

    private void setProgressOnThumb(Canvas canvas) {
        String progressText = String.valueOf(getProgress());
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(progressText, 0, progressText.length(), bounds);

        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbOffset = mThumbSize * (.5f - progressRatio);
        float thumbX = progressRatio * width + leftPadding + thumbOffset;
        float thumbY = getHeight() / 2f + bounds.height() / 2f;
        canvas.drawText(progressText, thumbX, thumbY, mTextPaint);

    }

}
