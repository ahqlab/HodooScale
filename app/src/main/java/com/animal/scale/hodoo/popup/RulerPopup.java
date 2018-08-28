package com.animal.scale.hodoo.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.tistory.dwfox.dwrulerviewlibrary.utils.DWUtils;
import com.tistory.dwfox.dwrulerviewlibrary.view.ObservableHorizontalScrollView;
import com.tistory.dwfox.dwrulerviewlibrary.view.ScrollingValuePicker;

public class RulerPopup  extends Dialog{

    private ScrollingValuePicker myScrollingValuePicker;

    private static final float MIN_VALUE = 0;
    private static final float MAX_VALUE = 100;
    private static final float LINE_RULER_MULTIPLE_SIZE = 4.5f;
    private TextView text;

    public RulerPopup(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.dialog_custom_ruler_popup);     //다이얼로그에서 사용할 레이아웃입니다.

        text = (TextView) findViewById(R.id.text);

        myScrollingValuePicker = (ScrollingValuePicker) findViewById(R.id.myScrollingValuePicker);
        myScrollingValuePicker.setViewMultipleSize(LINE_RULER_MULTIPLE_SIZE);
        myScrollingValuePicker.setMaxValue(MIN_VALUE, MAX_VALUE);
        myScrollingValuePicker.setValueTypeMultiple(5);
        myScrollingValuePicker.setInitValue(10);
        myScrollingValuePicker.getScrollView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    myScrollingValuePicker.getScrollView().startScrollerTask();
                }
                return false;
            }
        });

        myScrollingValuePicker
                .setOnScrollChangedListener(new ObservableHorizontalScrollView.OnScrollChangedListener() {

                    @Override
                    public void onScrollChanged(ObservableHorizontalScrollView view, int l, int t) {
                    }

                    @Override
                    public void onScrollStopped(int l, int t) {
                        text.setText("Value STOP : " +
                                DWUtils.getValueAndScrollItemToCenter(myScrollingValuePicker.getScrollView()
                                        , l
                                        , t
                                        , MAX_VALUE
                                        , MIN_VALUE
                                        , myScrollingValuePicker.getViewMultipleSize()));
                    }
                });



    }
}
