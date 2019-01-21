package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class WeightView extends LinearLayout {
    private final String TAG = WeightView.class.getSimpleName();
    private int mDisplayCount = 0;
    private float mTextSize = 12;
    private int MAX_COUNT = 6;
    private String mSufFix = "";
    private TickerView[] mFirstNum;
    private TickerView[] mPointView;
    public WeightView(Context context) {
        this(context, null);
    }

    public WeightView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(attrs);
        init();
    }
    private void init () {
        setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPointView = new TickerView[mDisplayCount];
        mFirstNum = new TickerView[MAX_COUNT];

        /* MAX_COUNT 배치 */
        for (int j = 0; j < mFirstNum.length; j++) {
            TickerView firstNumView = new TickerView(getContext());
            firstNumView.setAnimationDuration(2000);
            firstNumView.setCharacterLists(TickerUtils.provideNumberList());
            firstNumView.setTextSize(mTextSize);
            firstNumView.setLayoutParams(params);
            mFirstNum[j] = firstNumView;
            addView(mFirstNum[j]);
        }

        if ( mDisplayCount > 0 ) {
            TextView dot = new TextView(getContext());
            dot.setText(".");
            dot.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize / 4);

            mFirstNum[0].setText("0");
            addView(dot);
        }
        /* 소수점 표현을 위한 배치 */
        for (int i = 0; i < mDisplayCount; i++) {
            TickerView number = new TickerView(getContext());
            number.setCharacterLists(TickerUtils.provideNumberList());

            number.setTextSize(mTextSize);
            number.setLayoutParams(params);
            number.setAnimationDuration(2000);
            mPointView[i] = number;
            addView(number);
            number.setText("0");
        }
        //접미사 처리
        if ( mSufFix != null || !mSufFix.equals("") ) {
            TextView kg = new TextView(getContext());
            kg.setText(mSufFix);
            kg.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize / 4);
            addView(kg);
        }

    }
    private void getAttr ( AttributeSet attrs ) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeightView);
        setAttr(typedArray);
    }
    private void getAttr ( AttributeSet attrs, int def ) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WeightView, def, 0);
        setAttr(typedArray);
    }
    private void setAttr( TypedArray attr ) {
        mDisplayCount = attr.getInteger(R.styleable.WeightView_displayNum, 1);
        mTextSize = attr.getDimension(R.styleable.WeightView_textSize, 12);
        mSufFix = attr.getString(R.styleable.WeightView_sufFix);
        attr.recycle();
    }
    public void setNumber ( float num ) {
        String formatStr = "%." + String.valueOf(mDisplayCount) + "f";
        String numberStr = String.format(formatStr, num);
        String[] splitStr = numberStr.split("\\.");

        Log.e("HJLEE", "splitStr : " + splitStr.length);

        for (int i = 0; i < mFirstNum.length; i++) {
            mFirstNum[i].setText(null);
        }
        char[] number = new char[splitStr[0].length()];
        for(int i=0;i<number.length;i++){
            number[i]=(splitStr[0].charAt(i));
            for (int j = 0; j <= Integer.parseInt(String.valueOf(number[i])); j++) {
                if ( mFirstNum[i] != null )
                    mFirstNum[i].setText(String.valueOf(j));
            }

        }
        if ( mDisplayCount > 0 ) {
            number = new char[splitStr[1].length()];
            if ( number.length < mDisplayCount) {
                number = new char[mDisplayCount];
                for (int i = 0; i < number.length; i++)
                    if ( i < splitStr[1].length() )
                        number[i]=(splitStr[1].charAt(i));
                    else
                        number[i]='0';
            } else {
                for (int i = 0; i < number.length; i++)
                    number[i]=(splitStr[1].charAt(i));
            }
            for (int i = 0; i < number.length; i++)
                mPointView[i].setText(String.valueOf(number[i]));
        }
    }
}
