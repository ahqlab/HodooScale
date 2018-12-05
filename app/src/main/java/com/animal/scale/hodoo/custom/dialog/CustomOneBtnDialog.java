package com.animal.scale.hodoo.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.animal.scale.hodoo.R;

public class CustomOneBtnDialog extends Dialog {

    private TextView mContentView;
    private Button mLeftButton;
    private String mContent;

    private View.OnClickListener mLeftClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_custom_dialog);


        mContentView = (TextView) findViewById(R.id.txt_content);
        mLeftButton = (Button) findViewById(R.id.btn_left);
        // 제목과 내용을 생성자에서 셋팅한다.
        mContentView.setText(mContent);
        // 클릭 이벤트 셋팅
        if (mLeftClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public CustomOneBtnDialog(Context context, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }

    public CustomOneBtnDialog() {
        super(null);
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public CustomOneBtnDialog(Context context, String content, View.OnClickListener leftListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContent = content;
        this.mLeftClickListener = leftListener;
    }

}
