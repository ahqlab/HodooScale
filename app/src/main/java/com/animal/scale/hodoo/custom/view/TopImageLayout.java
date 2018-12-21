package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.util.VIewUtil;

public class TopImageLayout extends LinearLayout {
    private int topImageResource;
    private int topImageMargin;
    public TopImageLayout(Context context) {
        this(context, null);
    }

    public TopImageLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopImageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(attrs);
        initView();
    }
    private void getAttr(AttributeSet attrs) {
        setAttr( getContext().obtainStyledAttributes(attrs, R.styleable.TopImageLayout) );
    }
    private void setAttr( TypedArray arr ) {
        topImageResource = arr.getResourceId(R.styleable.TopImageLayout_topImage, R.drawable.ruler);
        topImageMargin = arr.getInteger(R.styleable.TopImageLayout_topImageMargin, 0);
    }
    private void initView () {
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, VIewUtil.dpToPx(topImageMargin), 0, VIewUtil.dpToPx(8));
        ImageView topImage = new ImageView(getContext());
        topImage.setImageResource(topImageResource);
        topImage.setLayoutParams(params);
        addView(topImage, 0);
    }
}
