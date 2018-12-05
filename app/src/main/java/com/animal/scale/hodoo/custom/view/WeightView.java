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

import java.util.stream.IntStream;

public class WeightView extends LinearLayout {
    private final String TAG = WeightView.class.getSimpleName();
    private int displayCount = 0;
    private float textSize = 12;
    private TickerView[] firstNum;
    private TickerView[] pointView;
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
        pointView = new TickerView[displayCount - 1];
        firstNum = new TickerView[3];
        for (int i = 0; i < displayCount; i++) {
            if ( i == 0 && displayCount > 1 ) {
                for (int j = 0; j < firstNum.length - 1; j++) {
                    TickerView firstNumView = new TickerView(getContext());
                    firstNumView.setAnimationDuration(1500);
                    firstNumView.setCharacterLists(TickerUtils.provideNumberList());
                    firstNumView.setTextSize(textSize);
                    firstNumView.setLayoutParams(params);
                    firstNum[j] = firstNumView;
                    addView(firstNum[j]);
                }

                TextView dot = new TextView(getContext());
                dot.setText(".");
                dot.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize / 4);

                firstNum[0].setText("0");
                addView(dot);
                continue;
            }
            TickerView number = new TickerView(getContext());
            number.setCharacterLists(TickerUtils.provideNumberList());

            number.setTextSize(textSize);
            number.setLayoutParams(params);
            number.setAnimationDuration(1500);
            pointView[i - 1] = number;
            addView(number);
            number.setText("0");
        }
        TextView kg = new TextView(getContext());
        kg.setText("kg");
        addView(kg);
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
        displayCount = attr.getInteger(R.styleable.WeightView_displayNum, 1);
        Log.e(TAG, String.format("displayCount : %d", displayCount));
        textSize = attr.getDimension(R.styleable.WeightView_textSize, 12);
        attr.recycle();
    }
    public void setNumber ( float num ) {
        
        String numberStr = String.valueOf(num);
        String[] splitStr = numberStr.split("\\.");

        char[] number = new char[splitStr[0].length()];
        for(int i=0;i<number.length;i++){
            number[i]=(splitStr[0].charAt(i));
            for (int j = 0; j <= Integer.parseInt(String.valueOf(number[i])); j++) {
                firstNum[i].setText(String.valueOf(j));
            }
            System.out.println(number[i]); //출력
        }
//        for (int i = 0; i <= Integer.parseInt(splitStr[0]); i++) {
//            firstNum.setText( String.valueOf(i) );
//        }
//        for (int i = 0; i < displayCount - 1; i++) {
//            for (int j = 0; j < point.length; j++) {
//                for (int k = 0; k < Integer.parseInt(point[j]); k++) {
//                    pointView[i].setText(String.valueOf(k));
//                }
//            }
//
//        }
    }
}
