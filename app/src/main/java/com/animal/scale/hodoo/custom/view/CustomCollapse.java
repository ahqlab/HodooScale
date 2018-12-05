package com.animal.scale.hodoo.custom.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.animal.scale.hodoo.R;

public class CustomCollapse extends RelativeLayout implements View.OnClickListener {
    private String TAG = CustomCollapse.class.getSimpleName();
    private int mIcon;
    private String mTitle;
    private String mCon;
    private ImageView mIconView;
    private int flag = 1;
    private int initHeight = -1;
    private int openHeight = 0;

    private View header;
    private View content;

    private boolean initState = false;

    public CustomCollapse(Context context) {
        this(context, null);
    }

    public CustomCollapse(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCollapse(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init () {
        mIcon = R.drawable.down;
        setOnClickListener(this);
    }
    private void setTitle ( String title ) {
        mTitle = title;
    }
    private void setContent ( String con ) {
        mCon = con;
    }
    private void setCollapseIcon ( int icon ) {
        mIcon = icon;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if ( v.equals(this) ) {
            if ( initHeight < 0 )
                initHeight = this.getMeasuredHeight();
            flag = flag < 0 ? 1 : -1;
            int start = 0, end = 0;
            if ( flag < 0 ) {
                start = initHeight;
                end = this.getPaddingTop() + this.getPaddingBottom() + this.getMeasuredHeight() + openHeight + 15;
            } else {
                start = this.getMeasuredHeight();
                end = initHeight;
            }

            ValueAnimator anim = ValueAnimator.ofInt(start, end);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();

                    ViewGroup.LayoutParams layoutParams = CustomCollapse.this.getLayoutParams();
                    layoutParams.height = val;
                    CustomCollapse.this.setLayoutParams(layoutParams);
                }
            });
            anim.setDuration(500);
            anim.start();

            if ( flag < 0 ) {
                start = 0;
                end = this.getMeasuredHeight() + openHeight;
            } else {
                start = this.getMeasuredHeight();
                end = 0;
            }

            ValueAnimator tvAnim = ValueAnimator.ofInt(start, end);
            tvAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (int) valueAnimator.getAnimatedValue();
                    LayoutParams params = (LayoutParams) content.getLayoutParams();
                    params.height = val;
                    content.setLayoutParams(params);
                }
            });
            tvAnim.setDuration(500);
            tvAnim.start();

            mIconView.animate().rotation(flag < 0 ? -180 : 0).withLayer();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }
    private void initSize () {

        header = this.getChildAt(0);
        content = this.getChildAt(1);

        if ( mIconView == null ) {
            LayoutParams iconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iconParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            iconParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            iconParams.setMargins(0, 0, 0, this.getPaddingBottom());

            mIconView = new ImageView(getContext());
            mIconView.setLayoutParams(iconParams);
            mIconView.setImageResource(mIcon);

            addView(mIconView);
        }

        header.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        content.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        openHeight = dpToPx(content.getMeasuredHeight());


        LinearLayout wrap = (LinearLayout) content;
        Rect realSize = new Rect();
        TextView contentTv = (TextView) wrap.getChildAt(0);
        contentTv.getPaint().getTextBounds(contentTv.getText().toString(), 0, contentTv.getText().length(), realSize);

        if ( !initState ) {
            LayoutParams params = (LayoutParams) content.getLayoutParams();
            params.height = 0;
            content.setLayoutParams(params);
        }
        ViewGroup.LayoutParams params = this.getLayoutParams();
        params.height = dpToPx(header.getMeasuredHeight() - 15);
        this.setLayoutParams(params);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initSize();
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
