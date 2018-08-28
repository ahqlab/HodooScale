package com.animal.scale.hodoo.util;

import android.widget.EditText;

public class ValidationUtil {

    public final static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            //android Regex to check the email address Validation
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isEmpty(EditText editText) {
        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }
    public static boolean isEmpty(String message) {
        String input = message.trim();
        return input.length() == 0;
    }

    public static void setError(EditText editText, String errorString) {
        editText.setError(errorString);
    }

    public static void clearError(EditText editText) {
        editText.setError(null);
    }
}
