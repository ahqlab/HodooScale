package com.animal.scale.hodoo.custom.view.input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.animal.scale.hodoo.util.ValidationUtil;

public class CommonTextWatcher implements TextWatcher {
    public static final String TAG = CommonTextWatcher.class.getSimpleName();
    public static final int EMAIL_TYPE = 0;
    public static final int EMPTY_TYPE = 1;
    public static final int JOIN_PW_TYPE = 2;
    public static final int PWCHECK_TYPE = 3;

    public interface CommonTextWatcherCallback {
        void onChangeState( boolean state );
    }
    private CustomCommonEditTextIn view;
    private CustomCommonEditTextIn[] views;
    private Context context;
    private CommonTextWatcherCallback mCallback;
    private int mMsgResource;
    private int[] mMsgResources;
    private boolean state = false;
    private int mType;

    public CommonTextWatcher(CustomCommonEditTextIn view, Context context, int type, int msgResource, CommonTextWatcherCallback callback) {
        this.view = view;
        this.context = context;
        mMsgResource = msgResource;
        mType = type;
        mCallback = callback;
    }
    public CommonTextWatcher(CustomCommonEditTextIn target, CustomCommonEditTextIn compareView, Context context, int type, int msgResources[], CommonTextWatcherCallback callback) {
        views = new CustomCommonEditTextIn[]{
                target,
                compareView
        };
        this.context = context;
        mMsgResources = msgResources;
        mType = type;
        mCallback = callback;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//        checkValidation(charSequence);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkValidation(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if ( mType != JOIN_PW_TYPE && mType != PWCHECK_TYPE )
            view.setErrorMessageViewisExposed(false);
    }
    public void checkValidation (CharSequence charSequence) {
        if ( mType != JOIN_PW_TYPE && mType != PWCHECK_TYPE ) {
            state = mType == EMAIL_TYPE ? ValidationUtil.isValidEmail(charSequence.toString()) : !ValidationUtil.isEmpty(charSequence.toString());
            view.setErrorMessageViewisExposed(!state);
            view.setErrorMessage(!state ? context.getString(mMsgResource) : "");
            view.setStatus(state);
            if ( mCallback != null )
                mCallback.onChangeState(state);
        } else {
            state = !ValidationUtil.isEmpty(charSequence.toString());
            views[0].setErrorMessageViewisExposed(state);
            views[0].setErrorMessage(!state ? context.getString(mMsgResources[0]) : "");
            views[0].setStatus(!state);
            if ( mCallback != null )
                mCallback.onChangeState(state);
            if ( !state )
                return;

            if ( mType == JOIN_PW_TYPE ) {
                state = views[0].getText().toString().matches(views[1].getText().toString());
                if ( !ValidationUtil.isEmpty(views[1].getText().toString()) ) {
                    views[1].setErrorMessageViewisExposed(!state);
                    views[1].setErrorMessage(!state ? context.getString(mMsgResources[1]) : "");
                    views[1].setStatus(state);
                }
            }
            else if ( mType == PWCHECK_TYPE ) {
                state = views[0].getText().toString().matches(views[1].getText().toString());
                views[0].setErrorMessageViewisExposed(!state);
                views[0].setErrorMessage(!state ? context.getString(mMsgResources[1]) : "");
                views[0].setStatus(state);
                if ( mCallback != null )
                    mCallback.onChangeState(state);
            }


        }

    }
}
