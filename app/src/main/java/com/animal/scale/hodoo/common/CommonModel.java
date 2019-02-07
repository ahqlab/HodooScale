package com.animal.scale.hodoo.common;

import android.content.Context;

import java.io.Serializable;
import java.util.List;

public class CommonModel {

    public static final int limitedTime = 10000;
    public static final int interval = 1000;

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public interface DomainCallBackListner<D extends Serializable> {
        void doPostExecute(D d);
        void doPreExecute();

    }

    public interface DomainListCallBackListner<D extends Serializable> {
        void doPostExecute(List<D> d);
        void doPreExecute();
    }
}
