package com.animal.scale.hodoo.common;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;

public class AsyncTaskCancelTimerTask extends CountDownTimer {

    public static final String TAG = "HJLEE";

    private AsyncTask asyncTask;
    private boolean interrupt;

    private AsyncTaskCancelTimerTask(AsyncTask asyncTask, long startTime, long interval) {
        super(startTime, interval);
        this.asyncTask = asyncTask;
    }

    public AsyncTaskCancelTimerTask(AsyncTask asyncTask, long startTime, long interval, boolean interrupt) {
        super(startTime, interval);
        this.asyncTask = asyncTask;
        this.interrupt = interrupt;
    }

    @Override
    public void onTick(long l) {
        //Log.e(TAG, "onTick..");
        if (asyncTask == null) {
            this.cancel();
            return;
        }
        if (asyncTask.isCancelled()){
            this.cancel();
        }
        if (asyncTask.getStatus() == AsyncTask.Status.FINISHED){
            this.cancel();
        }
    }

    @Override
    public void onFinish() {
       // Log.e(TAG, "onTick..");
        if (asyncTask == null || asyncTask.isCancelled()){
            return;
        }
        try {
            if (asyncTask.getStatus() == AsyncTask.Status.FINISHED){
                return;
            }
            if (asyncTask.getStatus() == AsyncTask.Status.PENDING || asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                asyncTask.cancel(interrupt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
