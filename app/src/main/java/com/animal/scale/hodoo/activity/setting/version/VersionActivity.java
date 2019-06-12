package com.animal.scale.hodoo.activity.setting.version;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityVersionBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.AppVersion;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.util.MathUtil;

public class VersionActivity extends BaseActivity<VersionActivity> implements VersionIn.View{

    ActivityVersionBinding binding;

    VersionIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_version);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_app_version)));
        super.setToolbarColor();
        presenter = new VersionPresenter(VersionActivity.this);
        binding.setPresenter(new VersionPresenter(VersionActivity.this));
        presenter.getServerAppVersion();
    }

    @Override
    protected BaseActivity<VersionActivity> getActivityClass() {
        return VersionActivity.this;
    }

    /**
     * 버전 비교하여 텍스트 노출함.
     * @param appVersion
     */
    @Override
    public void setServerAppVersion(CommonResponce<AppVersion> appVersion) {

        String inAppVersion = presenter.getVersionInfo(VersionActivity.this);
        binding.setDomain(appVersion.getDomain());
        binding.setVersion(inAppVersion);
        if(appVersion.getDomain().getVersion().equals(inAppVersion)){
            //binding.currentVersion.setText(R.string.current_app_version + inAppVersion);
            //binding.currentVersionDesc.setText("Supported OS iOS 10.0 or late");
            binding.currentVersion.setText(getResources().getString(R.string.current_app_version)  + " " + inAppVersion);
            binding.updateBtn.setText(R.string.this_is_latest_version);
        }else{
            //binding.currentVersion.setText(R.string.current_app_version + inAppVersion);
            binding.currentVersion.setText(getResources().getString(R.string.current_app_version) + " " + inAppVersion);
            //binding.currentVersionDesc.setText("Supported OS iOS 10.0 or later");
            binding.updateBtn.setText(R.string.you_need_update);
        }
    }

    /**
     * 새로운 버전이 있읅경우 마켓으로 이동한다.
     */
    @Override
    public void goToMarket() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    @BindingAdapter({"serverVersion", "appVersion"})
    public static void writeButtonText(Button boButton, String serverVersion, String appVersion) {
        Log.e("HJLEE", "serverVersion : " + serverVersion);
        Log.e("HJLEE", "appVersion : " + appVersion);
       /* if(serverVersion.equals(appVersion)){
            boButton.setText("This is the latest version");
        }else{
            boButton.setText("Go app store to update");
        }*/
    }
}
