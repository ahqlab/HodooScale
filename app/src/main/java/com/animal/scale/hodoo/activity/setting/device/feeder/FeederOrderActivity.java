package com.animal.scale.hodoo.activity.setting.device.feeder;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityDeviceListBinding;
import com.animal.scale.hodoo.databinding.ActivityFeederOrderBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.util.RER;

public class FeederOrderActivity extends BaseActivity<FeederOrderActivity> implements FeederOrderIn.View{

    ActivityFeederOrderBinding binding;

    FeederOrderIn.Presenter presenter;

    private float rer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feeder_order);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.feed_order)));
        presenter = new FeederOrderPresenter(FeederOrderActivity.this);
        presenter.loadData(FeederOrderActivity.this);

    }

    @Override
    protected BaseActivity<FeederOrderActivity> getActivityClass() {
        return FeederOrderActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPetAllInfo();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setPetAllInfo(PetAllInfos petAllInfos) {
        if ( petAllInfos == null )
            return;
        rer = new RER(Float.parseFloat(mSharedPrefManager.getStringExtra(SharedPrefVariable.TODAY_AVERAGE_WEIGHT)) , petAllInfos.getFactor()).getRER();
        Log.e("HJLEE", "RER : " + rer);
    }

    @Override
    public void insertResult(Integer integer) {
        Log.e("HJLEE", "result : " + integer);
    }

    public void onClicOrderBtn(View view){
        presenter.insertOrder(rer);
    }
}
