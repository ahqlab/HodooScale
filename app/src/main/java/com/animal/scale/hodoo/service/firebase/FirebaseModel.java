package com.animal.scale.hodoo.service.firebase;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.FirebaseInfo;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class FirebaseModel extends CommonModel {
    private Context context;
    private SharedPrefManager mSharedPrefManager;
    @Override
    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }
    public void setData ( String data ) {
        mSharedPrefManager.putStringExtra(SharedPrefVariable.FIREBASE_NOTI, data);
    }
    public Map<String,String> getFirebaseInfos () {
        return (Map<String, String>) VIewUtil.fromJson( mSharedPrefManager.getStringExtra(SharedPrefVariable.FIREBASE_NOTI), new TypeToken< Map<String,String>>(){}.getType());
    }
    public void saveBadge( int count ) {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.BADGE_COUNT, count);
    }
}
