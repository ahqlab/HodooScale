package com.animal.scale.hodoo.common;

import android.content.DialogInterface;

public interface CommonListener {
    interface PopupClickListener {
        void onPositiveClick( DialogInterface dialog, int which );
        void onNegativeClick( DialogInterface dialog, int which );
    }
}
