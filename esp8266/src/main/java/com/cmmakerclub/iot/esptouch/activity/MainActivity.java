package com.cmmakerclub.iot.esptouch.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cmmakerclub.iot.esptouch.EsptouchTask;
import com.cmmakerclub.iot.esptouch.IEsptouchListener;
import com.cmmakerclub.iot.esptouch.IEsptouchResult;
import com.cmmakerclub.iot.esptouch.IEsptouchTask;
import com.cmmakerclub.iot.esptouch.R;
import com.cmmakerclub.iot.esptouch.databinding.MainActivityBinding;
import com.cmmakerclub.iot.esptouch.helper.AppHelper;
import com.cmmakerclub.iot.esptouch.model.AccessPoint;
import com.cmmakerclub.iot.esptouch.task.__IEsptouchTask;

import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener {

	public static final String TAG = MainActivity.class.getSimpleName();


	private TextView mTvApSsid;

	private EditText mEdtApPassword;

	private Button mBtnConfirm;

	private Switch mSwitchIsSsidHidden;

	private EspWifiAdminSimple mWifiAdmin;

	private Spinner mSpinnerTaskCount;

    CoordinatorLayout rootLayout;
    DrawerLayout drawerLayout;
	Toolbar toolbar;

    private Context mContext;
    MainActivityBinding  mBinder;

    protected void initToolbar() {
//		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

    protected void initInstances() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);

//		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Fabric.with(this, new Crashlytics());
        mBinder = DataBindingUtil.setContentView(this, R.layout.main_activity);

        mContext = this;

		mWifiAdmin = new EspWifiAdminSimple(this);
		mTvApSsid = (TextView) findViewById(R.id.tvApSssidConnected);
		mEdtApPassword = (EditText) findViewById(R.id.edtApPassword);
		mBtnConfirm = (Button) findViewById(R.id.btnConfirm);
		mSwitchIsSsidHidden = (Switch) findViewById(R.id.switchIsSsidHidden);
		mBtnConfirm.setOnClickListener(this);
		initSpinner();

/*
		AdView mAdView = (AdView) findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);*/




//        ESPDevice.listAll(ESPDevice.class);
//        ESPDevice espDevice = new ESPDevice();
//        espDevice.save();
//		mInterstitialAd = new InterstitialAd(this);
//		mInterstitialAd.setAdUnitId(getResources().getString(R.id.));
//
//		mInterstitialAd.setAdListener(new AdListener() {
//			@Override
//			public void onAdClosed() {
//				requestNewInterstitial();
//				beginPlayingGame();
//			}
//		});
//
//		requestNewInterstitial();
	}

	private void initSpinner()
	{
		mSpinnerTaskCount = (Spinner) findViewById(R.id.spinnerTaskResultCount);
		int[] spinnerItemsInt = getResources().getIntArray(R.array.taskResultCount);
		int length = spinnerItemsInt.length;
		Integer[] spinnerItemsInteger = new Integer[length];
		for(int i=0;i<length;i++)
		{
			spinnerItemsInteger[i] = spinnerItemsInt[i];
		}
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
				R.layout.spinner_item, spinnerItemsInteger);
		mSpinnerTaskCount.setAdapter(adapter);
		mSpinnerTaskCount.setSelection(1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// display the connected accessPoint's ssid
		String apSsid = mWifiAdmin.getWifiConnectedSsid();
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.ssid = apSsid;
        accessPoint.password = AppHelper.getPsk(mContext, "");

        mBinder.setAp(accessPoint);

		// check whether the wifi is connected
		boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
		mBtnConfirm.setEnabled(!isApSsidEmpty);
	}

    @Override
    public void onClick(View v) {

        if (v == mBtnConfirm) {
            String apSsid = mTvApSsid.getText().toString();
            String apPassword = mEdtApPassword.getText().toString();
            String apBssid = mWifiAdmin.getWifiConnectedBssid();
            Boolean isSsidHidden = mSwitchIsSsidHidden.isChecked();
            String isSsidHiddenStr = "NO";
            String taskResultCountStr = Integer.toString(mSpinnerTaskCount
                    .getSelectedItemPosition());

            AppHelper.setPsk(mContext, apPassword);

            if (isSsidHidden)
            {
                isSsidHiddenStr = "YES";
            }
            if (__IEsptouchTask.DEBUG) {
                Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                        + ", " + " mEdtApPassword = " + apPassword);
            }

            Log.e("HJLEE","TAG : " + TAG);
            Log.e("HJLEE","apSsid : " + apSsid);
            Log.e("HJLEE","apBssid : " + apBssid);
			Log.e("HJLEE","apPassword : " + apSsid);
			Log.e("HJLEE","isSsidHiddenStr : " + isSsidHiddenStr);
            Log.e("HJLEE","apSsid : " + apSsid);
            new EsptouchAsyncTask3().execute(apSsid, apBssid, apPassword,
                    isSsidHiddenStr, taskResultCountStr);
        }
    }

	private class EsptouchAsyncTask2 extends AsyncTask<String, Void, IEsptouchResult> {

		private ProgressDialog mProgressDialog;

		private IEsptouchTask mEsptouchTask;
		// without the lock, if the user tap confirm and cancel quickly enough,
		// the bug will arise. the reason is follows:
		// 0. task is starting created, but not finished
		// 1. the task is cancel for the task hasn't been created, it do nothing
		// 2. task is created
		// 3. Oops, the task should be cancelled, but it is running
		private final Object mLock = new Object();

		@Override
		protected void onPreExecute() {
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog
					.setMessage("Esptouch is configuring, please wait for a moment...");
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					synchronized (mLock) {
						if (__IEsptouchTask.DEBUG) {
							Log.i(TAG, "progress dialog is canceled");
						}
						if (mEsptouchTask != null) {
							mEsptouchTask.interrupt();
						}
					}
				}
			});
			mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
					"Waiting...", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			mProgressDialog.show();
			mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
					.setEnabled(false);
		}

		@Override
		protected IEsptouchResult doInBackground(String... params) {
			synchronized (mLock) {
				String apSsid = params[0];
				String apBssid = params[1];
				String apPassword = params[2];
				String isSsidHiddenStr = params[3];
				boolean isSsidHidden = false;
				if (isSsidHiddenStr.equals("YES")) {
					isSsidHidden = true;
				}
				mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword,
						isSsidHidden, MainActivity.this);
			}
			IEsptouchResult result = mEsptouchTask.executeForResult();
			return result;
		}

		@Override
		protected void onPostExecute(IEsptouchResult result) {
			mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
					.setEnabled(true);
			mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
					"Confirm");
			// it is unnecessary at the moment, add here just to show how to use isCancelled()
			if (!result.isCancelled()) {
				if (result.isSuc()) {
					mProgressDialog.setMessage("Esptouch success, bssid = "
							+ result.getBssid() + ",InetAddress = "
							+ result.getInetAddress().getHostAddress());
                   // Answers.getInstance().logCustom(new CustomEvent("ESPTOUCH success"));
				} else {
					mProgressDialog.setMessage("Esptouch fail");
                  //  Answers.getInstance().logCustom(new CustomEvent("ESPTOUCH failure"));
				}
			}
		}
	}


	private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				String text = result.getBssid() + " is connected to the wifi";
                /*Answers.getInstance().logCustom(new CustomEvent("ESPTOUCH wifi connected"));*/
				Toast.makeText(MainActivity.this, text,
						Toast.LENGTH_LONG).show();
			}

		});
	}

	private IEsptouchListener myListener = new IEsptouchListener() {

		@Override
		public void onEsptouchResultAdded(final IEsptouchResult result) {
			onEsptoucResultAddedPerform(result);
		}
	};


	private class EsptouchAsyncTask3 extends AsyncTask<String, Void, List<IEsptouchResult>> {

		private ProgressDialog mProgressDialog;

		private IEsptouchTask mEsptouchTask;
		// without the lock, if the user tap confirm and cancel quickly enough,
		// the bug will arise. the reason is follows:
		// 0. task is starting created, but not finished
		// 1. the task is cancel for the task hasn't been created, it do nothing
		// 2. task is created
		// 3. Oops, the task should be cancelled, but it is running
		private final Object mLock = new Object();

		@Override
		protected void onPreExecute() {
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog
					.setMessage("Esptouch is configuring, please wait for a moment...");
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					synchronized (mLock) {
						if (__IEsptouchTask.DEBUG) {
							Log.i(TAG, "progress dialog is canceled");
						}
						if (mEsptouchTask != null) {
							mEsptouchTask.interrupt();
						}
					}
				}
			});
			mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
					"Waiting...", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
			mProgressDialog.show();
			mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
					.setEnabled(false);
		}

		@Override
		protected List<IEsptouchResult> doInBackground(String... params) {
			int taskResultCount = -1;
			synchronized (mLock) {
				String apSsid = params[0];
				String apBssid = params[1];
				String apPassword = params[2];
				String isSsidHiddenStr = params[3];
				String taskResultCountStr = params[4];
				boolean isSsidHidden = false;
				if (isSsidHiddenStr.equals("YES")) {
					isSsidHidden = true;
				}
				taskResultCount = Integer.parseInt(taskResultCountStr);
				mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword,
						isSsidHidden, MainActivity.this);
				mEsptouchTask.setEsptouchListener(myListener);

			}
			List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
			return resultList;
		}

		@Override
		protected void onPostExecute(List<IEsptouchResult> result) {
			mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE)
					.setEnabled(true);
			mProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setText(
					"Confirm");
			IEsptouchResult firstResult = result.get(0);
			// check whether the task is cancelled and no results received
			if (!firstResult.isCancelled()) {
				int count = 0;
				// max results to be displayed, if it is more than maxDisplayCount,
				// just show the count of redundant ones
				final int maxDisplayCount = 5;
				// the task received some results including cancelled while
				// executing before receiving enough results
				if (firstResult.isSuc()) {
					StringBuilder sb = new StringBuilder();
                   /* Answers.getInstance()
                            .logCustom(new CustomEvent("ESPTOUCH[] success")
                                    .putCustomAttribute("SIZE", result.size()));*/
					for (IEsptouchResult resultInList : result) {
						sb.append("Esptouch success, bssid = "
								+ resultInList.getBssid()
								+ ",InetAddress = "
								+ resultInList.getInetAddress()
										.getHostAddress() + "\n");
						count++;
						if (count >= maxDisplayCount) {
							break;
						}
					}
					if (count < result.size()) {
						sb.append("\nthere's " + (result.size() - count)
								+ " more result(s) without showing\n");
					}
					mProgressDialog.setMessage(sb.toString());
				} else {
					mProgressDialog.setMessage("Esptouch fail");
                 /*   Answers.getInstance()
                            .logCustom(new CustomEvent("ESPTOUCH[] failure")
                                    .putCustomAttribute("SIZE", result.size()));*/
				}
			}
		}
	}
}
