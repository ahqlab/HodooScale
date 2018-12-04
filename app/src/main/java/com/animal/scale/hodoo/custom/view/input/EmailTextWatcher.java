package com.animal.scale.hodoo.custom.view.input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.util.ValidationUtil;

public class EmailTextWatcher implements TextWatcher {

    Context context;

    CustomCommonEditTextIn view;

    public EmailTextWatcher(CustomCommonEditTextIn view) {
        this.view = view;
    }

    public EmailTextWatcher(CustomCommonEditTextIn view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!ValidationUtil.isEmpty(charSequence.toString())) {
            if (!ValidationUtil.isValidEmail(charSequence.toString())) {
                view.setErrorMessageViewisExposed(true);
                view.setErrorMessage(context.getString(R.string.vailed_email));
                view.setStatus(false);
            } else {
                view.setErrorMessage("");
                view.setErrorMessageViewisExposed(false);
                view.setStatus(true);
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!ValidationUtil.isEmpty(charSequence.toString())) {
            if (!ValidationUtil.isValidEmail(charSequence.toString())) {
                view.setErrorMessageViewisExposed(true);
                view.setErrorMessage(context.getString(R.string.vailed_email));
                view.setStatus(false);
            } else {
                view.setErrorMessage("");
                view.setErrorMessageViewisExposed(false);
                view.setStatus(true);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
