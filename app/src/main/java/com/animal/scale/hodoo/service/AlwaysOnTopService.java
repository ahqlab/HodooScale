package com.animal.scale.hodoo.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.constant.HodooConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AlwaysOnTopService extends Service implements View.OnClickListener {
    private String TAG = AlwaysOnTopService.class.getSimpleName();

    private TextView mPopupView;                            //항상 보이게 할 뷰
    private WindowManager.LayoutParams mParams;  //layout params 객체. 뷰의 위치 및 크기
    private WindowManager mWindowManager;

    private String title;
    private String msg;
    private String host;
    private String toUserEmail = "";
    private String data = "";
    
    private View alert;

    private Binder binder;

    Messenger messenger = null;

    static final int CONNECT = 0;
    static final int SEND_VALUE = 2;

    int notiType = 0;

    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LayoutInflater inflater = (LayoutInflater) getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        alert = inflater.inflate(R.layout.layout_always_service_dialog, null, false);

        TextView title = alert.findViewById(R.id.alert_title);
        TextView content = alert.findViewById(R.id.alert_content);

        host = intent.getStringExtra("host");
        data = intent.getStringExtra("data");

        try {
            JSONObject obj = new JSONObject(data);
            String notiTypeStr = obj.getString("notiType");

            if ( notiTypeStr != null || !notiTypeStr.equals("") )
                notiType = Integer.parseInt( notiTypeStr );
        } catch (JSONException e) {
            e.printStackTrace();
        }



        Button cancelBtn = alert.findViewById(R.id.cancel_btn);
        Button confirmBtn = alert.findViewById(R.id.confirm_btn);

        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

        toUserEmail = intent.getStringExtra("message");

        title.setText( intent.getStringExtra("title") );
        content.setText( toUserEmail + "님의 초대입니다." );

        mPopupView = new TextView(this);                                         //뷰 생성
        mPopupView.setText( intent.getStringExtra("title") );
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
        mParams.gravity = Gravity.CENTER;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);  //윈도우 매니저
        mWindowManager.addView(alert, mParams);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "destroy");
        mWindowManager.removeView(alert);
        mWindowManager.removeViewImmediate(alert);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn :
                Intent intent = null;
                switch (notiType) {
                    case HodooConstant.FIREBASE_NORMAL_TYPE :
                        intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case HodooConstant.FIREBASE_INVITATION_TYPE :
                        String url = "selphone://" + host;

                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.putExtra("toUserEmail", toUserEmail);
                        intent.putExtra("data", data);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }

                break;
        }
        stopSelf();
    }

    public class Binder extends android.os.Binder {
        public AlwaysOnTopService getService() {
            return AlwaysOnTopService.this;
        }
    }
}
