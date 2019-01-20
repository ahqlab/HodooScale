package com.animal.scale.hodoo.custom.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.LinearLayout;

import com.animal.scale.hodoo.R;

public class BottomDialog extends BottomSheetDialogFragment implements View.OnClickListener {
    private View contentView;
    private int mLayout;
    private OnClickListener listener;

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

    @Override
    public void onClick(View view) {
        dismiss();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), mLayout, null);

        LinearLayout wrap = contentView.findViewById(R.id.button_wrap);

        for (int i = 0; i < wrap.getChildCount(); i++) {
            wrap.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( listener != null )
                        listener.onClick(view);
                }
            });
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