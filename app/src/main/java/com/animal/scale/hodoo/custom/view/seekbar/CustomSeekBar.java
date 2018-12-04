package com.animal.scale.hodoo.custom.view.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.animal.scale.hodoo.R;

import java.util.ArrayList;

public class CustomSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    Context context;

    private TextPaint mTextPaint;

    private TextPaint thumbPaintLine;
    private TextPaint thumbPaintBg;

    private int mThumbSize;

    private ArrayList<ProgressItem> mProgressItemsList;

    public CustomSeekBar(Context context) {
        super(context);
        this.context = context;
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
        mThumbSize = getResources().getDimensionPixelSize(R.dimen.thumb_size);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(20);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        thumbPaintLine = new TextPaint();
        thumbPaintLine.setColor(Color.RED);
        thumbPaintLine.setStyle(Paint.Style.STROKE );

        thumbPaintBg = new TextPaint();
        thumbPaintBg.setColor(Color.WHITE);
        thumbPaintBg.setStyle(Paint.Style.FILL );
    }

    public void init() {
        /* 뷰의 크기에 영향을 받으며 아래와 같이 패딩을 설정하면 캔버스를 넓게 사용할 수 있습니다. */
       // this.setMargin(0, 100, 0, 100);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void initData(ArrayList<ProgressItem> progressItemsList) {
        this.mProgressItemsList = progressItemsList;
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected  synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mProgressItemsList.size() > 0) {
            int progressBarWidth = getWidth() - getPaddingRight();
            int progressBarHeight = getHeight();
            int thumboffset = getThumbOffset();
            int lastProgressX = getPaddingLeft();
            int progressItemWidth, progressItemRight;
            for (int i = 0; i < mProgressItemsList.size(); i++) {

                ProgressItem progressItem = mProgressItemsList.get(i);
                Paint progressPaint = new Paint();
                progressPaint.setColor(getResources().getColor(progressItem.color));
                progressItemWidth = (int) (progressItem.progressItemPercentage * progressBarWidth / 100) ;
                progressItemRight = lastProgressX + progressItemWidth;
                // for last item give right to progress item to the width
                if (i == mProgressItemsList.size() - 1 && progressItemRight != progressBarWidth) {
                    progressItemRight = progressBarWidth;
                }
                Rect progressRect = new Rect();
                RectF progressRectF = new RectF();
                if(progressItem.color == R.color.seek_bar_gray){
                    progressRect.set(lastProgressX, thumboffset / 2, progressItemRight , progressBarHeight - thumboffset / 2);
                    progressRectF.set(lastProgressX, thumboffset / 2, progressItemRight , progressBarHeight - thumboffset / 2);
                    canvas.drawLine(progressItemRight,0,progressItemRight, 100, progressPaint);
                    canvas.drawRoundRect(progressRectF, 6, 6, progressPaint);
                }else if(progressItem.color == R.color.grey){
                    progressRect.set(lastProgressX, thumboffset / 2, progressItemRight , progressBarHeight - thumboffset / 2);
                    canvas.drawLine(progressItemRight,0,progressItemRight, 100, progressPaint);
                    int median =  ((progressItemRight - lastProgressX) / 2 ) + lastProgressX;
                    canvas.drawText(context.getString(R.string.excess), median, 15, mTextPaint);
                    canvas.drawRect(progressRect, progressPaint);
                }else{
                    int median =  ((progressItemRight - lastProgressX) / 2 ) + lastProgressX;
                    canvas.drawText(context.getString(R.string.recommend), median, 15, mTextPaint);
                    progressRect.set(lastProgressX, thumboffset / 2, progressItemRight , progressBarHeight - thumboffset / 2);
                    canvas.drawRect(progressRect, progressPaint);
                }

                //progressRect.set(lastProgressX, thumboffset / 2, progressItemRight , progressBarHeight - thumboffset / 2);
                lastProgressX = progressItemRight;
            }

            setProgressOnThumb(canvas);
            //drwaLine(canvas);
        }
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
        RectF rectF = new RectF();
        rectF.set(thumbX - 50, 2, thumbX + 50, getHeight());
        canvas.drawRoundRect(rectF,15, 15, thumbPaintBg);
        canvas.drawRoundRect(rectF, 15, 15, thumbPaintLine);
        canvas.drawText(progressText + "kcal", thumbX, thumbY, mTextPaint);
    }
}
