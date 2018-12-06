package com.animal.scale.hodoo.custom.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.WelcomeViewPagerAdapter;
import com.animal.scale.hodoo.util.VIewUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeViewPager extends RelativeLayout {
    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private LinearLayout btnWrap;
    private int selectPosition = 0;

    public WelcomeViewPager(@NonNull Context context) {
        this(context, null);
    }

    public WelcomeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WelcomeViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mFragments = new ArrayList<>();
        init();
    }

    private void init() {
        mViewPager = new ViewPager(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mViewPager.setId(View.generateViewId());
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                selectPosition = i;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    changeBtnColor();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        addView(mViewPager);
    }

    private void initButton() {
        if ( btnWrap == null ) {
            btnWrap = new LinearLayout(getContext());
            LayoutParams parentParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parentParams.addRule(ALIGN_PARENT_BOTTOM);
            parentParams.addRule(CENTER_HORIZONTAL);
            parentParams.bottomMargin = VIewUtil.dpToPx(32);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(VIewUtil.dpToPx(7), VIewUtil.dpToPx(7));
            params.rightMargin = VIewUtil.dpToPx(10);
            for (int i = 0; i < mFragments.size(); i++) {
                View btn = new View(getContext());
                btn.setBackgroundResource(R.drawable.circle_button);
                if (selectPosition == i) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
                    }
                }
                btn.setLayoutParams(params);
                btnWrap.addView(btn);
            }
            btnWrap.setLayoutParams(parentParams);
            addView(btnWrap);
        }
    }

    public void setFragments(FragmentManager fm, Fragment... fragments) {
        for (Fragment fragment : fragments) {
            mFragments.add(fragment);
        }
        WelcomeViewPagerAdapter adapter = new WelcomeViewPagerAdapter(fm, mFragments);
        mViewPager.setAdapter(adapter);
        initButton();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void changeBtnColor() {
        for (int i = 0; i < btnWrap.getChildCount(); i++) {
            if (i == selectPosition)
                btnWrap.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            else
                btnWrap.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#cccccc")));
        }
    }
}
