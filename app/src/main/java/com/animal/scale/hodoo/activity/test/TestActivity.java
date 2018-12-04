package com.animal.scale.hodoo.activity.test;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityTestBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.User;

public class TestActivity extends BaseActivity<TestActivity> implements TestImpl.View {

    private boolean DEBUG = true;
    private ActivityTestBinding binding;
    private TestImpl.Presenter presenter = null;
    private TestDomain mTestDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo("타이틀"));
        binding.nickName.setText("");
        super.setToolbarColor();
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected BaseActivity<TestActivity> getActivityClass() {
        return TestActivity.this;
    }

    @Override
    public void setText() {

    }

    public void setOnClick(View view) {
        switch ( view.getId() ) {
            case R.id.nick_confirm :
                Log.d(TAG, binding.nickEdit.getText().toString());
                mTestDomain.setName(binding.nickEdit.getText().toString());
                setmTestDomain(null);
                presenter.workModelTest(this);
                break;
        }
    }

    private void setmTestDomain( TestDomain domain ) {
        if ( domain != null ) binding.setTestDomain(domain);
        else binding.setTestDomain(mTestDomain);
    }
    private void init () {
        if ( presenter == null ) presenter = new TestPresenter(this);
        if ( mTestDomain == null ) mTestDomain = new TestDomain();
        setmTestDomain( TestDomain.builder().build() );
    }
}
