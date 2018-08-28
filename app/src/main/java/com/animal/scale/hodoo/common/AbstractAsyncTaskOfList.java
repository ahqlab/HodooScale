package com.animal.scale.hodoo.common;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public abstract class AbstractAsyncTaskOfList<D> extends AsyncTask<Call, Void, List<D>> {

    protected abstract void doPostExecute(List<D> d);

    protected abstract void doPreExecute();

    @Override
    protected List<D> doInBackground(Call... params) {
        try {
            Call<List<D>> call = params[0];
            Response<List<D>> response = call.execute();
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
    protected void onPostExecute(List<D> d) {
        doPostExecute(d);
        super.onPostExecute(d);
    }
}
