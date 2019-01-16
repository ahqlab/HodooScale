package com.animal.scale.hodoo.custom.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.animal.scale.hodoo.R;

import org.w3c.dom.Text;

import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

public class CustomCollapse extends RelativeLayout implements View.OnClickListener {
    private String TAG = CustomCollapse.class.getSimpleName();
    private int mIcon;
    private String mTitle;
    private String mCon;
    private ImageView mIconView;
    private int flag = -1;
    private int initHeight = -1;
    private int openHeight = 0;


    private View header;
    private View content;

    private boolean initState = true;

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
    @Override
    public void onClick(View v) {
        if ( v.equals(this) ) {
            int start = 0, end = 0;
            if ( flag < 0 ) {
                start = 0;
                end = openHeight;
            } else {
                start = openHeight;
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
            flag = flag < 0 ? 1 : -1;
        }
    }
    private void initSize () {
        header = this.getChildAt(0);
        content = this.getChildAt(1);

        if ( mIconView == null ) {
            LayoutParams iconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            iconParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            iconParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            iconParams.setMargins(0, 0, 0, 0);

            mIconView = new ImageView(getContext());
            mIconView.setLayoutParams(iconParams);
            mIconView.setImageResource(mIcon);

            addView(mIconView);
        }

        header.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        final View contentTextView = ((LinearLayout) content).getChildAt(0);
        contentTextView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        String text = ((TextView)contentTextView).getText().toString();
        ((TextView)contentTextView).setText(text);

        contentTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LayoutParams params = (LayoutParams) content.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                content.setLayoutParams(params);
                openHeight = contentTextView.getMeasuredHeight();
                params.height = 0;

                content.setLayoutParams(params);
                contentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }
//
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initSize();
    }
    public void setContent( String content ) {
        if ( this.content == null )
            this.content = this.getChildAt(1);
        final TextView contentTextView = new TextView(getContext());
        contentTextView.setText(content);

        if ( DEBUG ) Log.e(TAG, String.format("line operation height : %d", contentTextView.getLineCount() * contentTextView.getLineHeight()));



        ((ViewGroup) this.content).removeAllViews();
        contentTextView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        ((ViewGroup) this.content).addView(contentTextView);

        contentTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                openHeight = contentTextView.getLineCount() * contentTextView.getLineHeight();
                contentTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

}
