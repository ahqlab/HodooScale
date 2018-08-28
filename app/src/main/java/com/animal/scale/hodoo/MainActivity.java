package com.animal.scale.hodoo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.animal.scale.hodoo.activity.user.login.LoginActivity;
import com.animal.scale.hodoo.util.CheckConnect;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ProgressBar bar;

        private static final String CONNECTION_CONFIRM_CLIENT_URL = "http://clients3.google.com/generate_204";

        public String store_version;

        public String device_version;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            bar = (ProgressBar) findViewById(R.id.progress_loader);
            if (isOnline()) {
                new ServiceCheckTask().execute();
            } else {
                Toast.makeText(getApplicationContext(), R.string.not_connected_to_the_Internet, Toast.LENGTH_LONG).show();
            }
        }

        private boolean isOnline() {
            CheckConnect cc = new CheckConnect(CONNECTION_CONFIRM_CLIENT_URL);
            cc.start();
            try {
                cc.join();
                return cc.isSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;

    }

    private class ServiceCheckTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //Intent intent = new Intent(getApplicationContext(), WifiSearchActivity.class);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            super.onPostExecute(result);
        }
    }
}
