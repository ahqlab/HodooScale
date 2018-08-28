package com.animal.scale.hodoo.util;

import android.databinding.BindingAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyOwnBindingUtil {

    public interface StringRule {
        public boolean validate(Editable s);
    }

    @BindingAdapter("android:watcher")
    public static void bindTextWatcher(EditText pEditText, TextWatcher pTextWatcher) {
        pEditText.addTextChangedListener(pTextWatcher);
    }

    @BindingAdapter(value = {"email:rule", "email:errorMsg"}, requireAll = true)
    public static void bindTextChange(final EditText pEditText, final StringRule pStringRule, final String msg) {
        pEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!pStringRule.validate(s)) {
                    pEditText.setError(msg);
                }
            }
        });
    }
}
