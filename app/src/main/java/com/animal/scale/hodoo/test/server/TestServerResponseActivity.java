package com.animal.scale.hodoo.test.server;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.device.regist.DeviceRegistPresenter;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityTestServerResponseBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;

public class TestServerResponseActivity extends BaseActivity<TestServerResponseActivity> implements TestServerResponseIn.View{

    ActivityTestServerResponseBinding binding;

    TestServerResponseIn.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_test_server_response);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_server_response);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.title_device_regist)));
        super.setToolbarColor();
        presenter = new TestServerResponsePresenter(TestServerResponseActivity.this);
        presenter.loadData(getApplicationContext());
    }

    @Override
    protected BaseActivity<TestServerResponseActivity> getActivityClass() {
        return TestServerResponseActivity.this;
    }

    public void onClickSubmitBtn(View view){
        presenter.testSubmit();
    }

    @Override
    public void setResult(User d) {
        Log.e("HJLEE", " >>>> " + d);
    }
}
