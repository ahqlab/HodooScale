package com.animal.scale.hodoo.custom.view.input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.animal.scale.hodoo.util.ValidationUtil;

public class CommonTextWatcher implements TextWatcher {
    public static final String TAG = CommonTextWatcher.class.getSimpleName();
    public static final int EMAIL_TYPE = 0;
    public static final int PASSWORD_TYPE = 1;

    public interface CommonTextWatcherCallback {
        void onChangeState( boolean state );
    }
    private CustomCommonEditTextIn view;
    private Context context;
    private CommonTextWatcherCallback mCallback;
    private int mMsgResource;
    private boolean state = false;
    private int mType;

    public CommonTextWatcher(CustomCommonEditTextIn view, Context context, int type, int msgResource, CommonTextWatcherCallback callback) {
        this.view = view;
        this.context = context;
        mMsgResource = msgResource;
        mType = type;
        mCallback = callback;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkValidation(charSequence);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        checkValidation(charSequence);
    }

    @Override
    public void afterTextChanged(Editable editable) {view.setErrorMessageViewisExposed(false);}
    public void checkValidation (CharSequence charSequence) {
        state = mType == EMAIL_TYPE ? ValidationUtil.isValidEmail(charSequence.toString()) : !ValidationUtil.isEmpty(charSequence.toString());
        
        Log.e(TAG, String.format("state : %b", state));
        view.setErrorMessageViewisExposed(!state);
        view.setErrorMessage(!state ? context.getString(mMsgResource) : "");
        view.setStatus(state);
        if ( mCallback != null )
            mCallback.onChangeState(state);
    }
}
