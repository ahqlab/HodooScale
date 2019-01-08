package com.animal.scale.hodoo.service.firebase;

import android.content.Context;

import com.animal.scale.hodoo.domain.FirebaseInfo;

import java.util.List;
import java.util.Map;

public interface FirebaseIn {
    interface View {
        void sendNotification( Map<String, String> data );
        void setBadge( int count );
        void sendBroad();
    }
    interface Presenter {
        void initDate ( Context context );
        void getData ( Map<String, String> data );
        void countingBadge ();
        void saveBadgeCount ( int count );
    }
}
