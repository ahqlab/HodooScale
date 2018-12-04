package com.animal.scale.hodoo.Esptouch;

import android.os.AsyncTask;

import com.espressif.iot.esptouch.IEsptouchResult;

import java.util.List;

public class EsptouchAsyncTask extends AsyncTask<byte[], Void, List<IEsptouchResult>> {

    @Override
    protected List<IEsptouchResult> doInBackground(byte[]... bytes) {
        return null;
    }
}
