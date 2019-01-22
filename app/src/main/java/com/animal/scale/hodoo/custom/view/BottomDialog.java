package com.animal.scale.hodoo.custom.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.IosStyleBottomAlert;
import com.animal.scale.hodoo.util.VIewUtil;

import java.util.ArrayList;
import java.util.List;

import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

public class BottomDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private String TAG = BottomDialog.class.getSimpleName();
    
    private View contentView;
    private int mLayout;
    private OnClickListener listener;

    private String[] mBtnStr;
    private List<IosStyleBottomAlert> mBtns;

    public interface OnClickListener {
        void onClick( View v );
    }

    public static BottomDialog getInstance() {
        return new BottomDialog();
    }

    public static BottomDialog getInstance( int layout ) {
        return new BottomDialog();
    }

    public void setLayout ( int layout ) {
        mLayout = layout;
    }
    public void setButton ( String...params ) {
        mBtnStr = params;
    }
    public void setButton (List<IosStyleBottomAlert> btn) {
        mBtns = btn;
        if ( DEBUG ) Log.e(TAG, "setButton");
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.bottom_ios_style_alert_layout, null);
        LinearLayout wrap = contentView.findViewById(R.id.button_wrap);


        if ( mBtnStr != null ) {
            mBtns = new ArrayList<>();
            for (int i = 0; i < mBtnStr.length; i++) {
                mBtns.add(
                        IosStyleBottomAlert.builder()
                                .btnName(mBtnStr[i])
                                .build()
                );
            }
        }
        if ( mBtns != null )  {
            for (int i = 0; i < mBtns.size(); i++) {
                Button btn = new Button(getContext());
                btn.setBackground(null);
                btn.setText( mBtns.get(i).getBtnName() );
                btn.setId( mBtns.get(i).getId() );
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ( listener != null )
                            listener.onClick(view);
                    }
                });
                wrap.addView(btn);
                if ( mBtns.size() > 0 ) {
                    if ( i != mBtns.size() - 1 ) {
                        View view = new View(getContext());
                        view.setBackgroundColor(Color.parseColor("#dadade"));
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, VIewUtil.dpToPx(1));
                        view.setLayoutParams(params);
                        wrap.addView(view);
                    }
                }


            }
        }
        View cancle = contentView.findViewById(R.id.cancel);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( listener != null )
                    listener.onClick(view);
            }
        });

        dialog.setContentView(contentView);
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    public void setOnclick ( OnClickListener listener ) {
        this.listener = listener;
    }
}