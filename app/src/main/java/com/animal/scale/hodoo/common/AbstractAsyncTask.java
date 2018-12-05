package com.animal.scale.hodoo.common;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @param <D>
 */
public abstract class AbstractAsyncTask<D extends Serializable> extends AsyncTask<Call, Void, D> {

    protected abstract void doPostExecute(D d);

    protected abstract void doPreExecute();

    @Override
    protected D doInBackground(Call... params) {
        try {
            Call<D> call = params[0];
            Response<D> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        doPreExecute();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(D d) {
        doPostExecute(d);
        super.onPostExecute(d);
    }
}
