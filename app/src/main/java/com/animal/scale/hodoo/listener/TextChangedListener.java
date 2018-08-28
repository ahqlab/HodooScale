package com.animal.scale.hodoo.listener;

import android.widget.EditText;

public abstract class TextChangedListener {

    EditText editText;

    public TextChangedListener(EditText editText){
        this.editText = editText;
    }

    protected abstract void TextWarcher(String message);
}
