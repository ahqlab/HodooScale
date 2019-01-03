package com.animal.scale.hodoo.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

public class AlwaysOnTopService extends Service {


    private TextView mPopupView;                            //항상 보이게 할 뷰
    private WindowManager.LayoutParams mParams;  //layout params 객체. 뷰의 위치 및 크기
    private WindowManager mWindowManager;

    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        mPopupView = new TextView(this);                                         //뷰 생성
        mPopupView.setText("이 뷰는 항상 위에 있다.");                        //텍스트 설정
        mPopupView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); //텍스트 크기 18sp
        mPopupView.setTextColor(Color.BLUE);                                  //글자 색상
        mPopupView.setBackgroundColor(Color.argb(127, 0, 255, 255)); //텍스트뷰 배경 색

        //최상위 윈도우에 넣기 위한 설정
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,//항상 최 상위. 터치 이벤트 받을 수 있음.
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,  //포커스를 가지지 않음
                PixelFormat.TRANSLUCENT);                                        //투명
        mParams.gravity = Gravity.LEFT | Gravity.TOP;                   //왼쪽 상단에 위치하게 함.

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);  //윈도우 매니저
        mWindowManager.addView(mPopupView, mParams);      //윈도우에 뷰 넣기. permission 필요.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
