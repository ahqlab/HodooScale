package com.animal.scale.hodoo.custom.view.input;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.util.ValidationUtil;

public class PasswordTextWatcher implements TextWatcher {

    Context context;

    CustomCommonEditTextIn view;

    public PasswordTextWatcher(CustomCommonEditTextIn view) {
        this.view = view;
    }

    public PasswordTextWatcher(CustomCommonEditTextIn view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (ValidationUtil.isEmpty(charSequence.toString())) {
            view.setErrorMessage(context.getString(R.string.istyle_enter_the_password));
            view.setStatus(false);
        }else{
            view.setErrorMessage("");
            view.setStatus(true);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (ValidationUtil.isEmpty(charSequence.toString())) {
            view.setErrorMessage(context.getString(R.string.istyle_enter_the_password));
            view.setStatus(false);
        }else{
            view.setErrorMessage("");
            view.setStatus(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        view.setErrorMessageViewisExposed(false);
    }
}
