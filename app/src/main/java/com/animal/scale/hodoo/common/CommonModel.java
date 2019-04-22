package com.animal.scale.hodoo.common;

import android.content.Context;

import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.CommonResponce;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

public class CommonModel {

    public static final int limitedTime = HodooConstant.LIMITED_TIME;
    public static final int interval = HodooConstant.INTERVAL;

    Context context;

    public SharedPrefManager sharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        sharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public interface DomainCallBackListner<D extends Serializable> {
        void doPostExecute(D d);
        void doPreExecute();
        void doCancelled();

    }

    public interface CommonDomainListCallBackListner<D> {
        void doPostExecute(CommonResponce<List<D>> d);
        void doPreExecute();
        void doCancelled();

    }

    public interface CommonDomainCallBackListner<D> {
        void doPostExecute(CommonResponce<D> d);
        void doPreExecute();
        void doCancelled();

    }

    public interface ObjectCallBackListner<D> {
        void doPostExecute(D d);
        void doPreExecute();
        void doCancelled();

    }

    public interface DomainListCallBackListner<D extends Serializable> {
        void doPostExecute(List<D> d);
        void doPreExecute();
        void doCancelled();
    }
}
