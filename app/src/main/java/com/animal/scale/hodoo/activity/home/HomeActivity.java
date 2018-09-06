package com.animal.scale.hodoo.activity.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.temp.TempFragment;
import com.animal.scale.hodoo.activity.home.weight.WeightFragment;
import com.animal.scale.hodoo.activity.setting.list.SettingListActivity;
import com.animal.scale.hodoo.adapter.AdapterSpinner;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.databinding.ActivityHomeBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class HomeActivity extends BaseActivity<HomeActivity> implements NavigationView.OnNavigationItemSelectedListener {

    private final long FINISH_INTERVAL_TIME = 2000;

    private long backPressedTime = 0;

    ActivityHomeBinding binding;
    //Spinner Adapter
    AdapterSpinner adapterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_title)));
        super.setToolbarColor();
        /*프레그먼트*/
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        changeBottomNavigationSize(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //navigation.setItemIconTintList(null);

        fragmentTransaction.add(R.id.fragment_container, WeightFragment.newInstance()).commit();
        /* 프레그먼트 END */
        /* 스피너 생성 */
        this.setSpinner();
        binding.appBarNavigation.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSharedPrefManager.removeAllPreferences();
                Intent intent = new Intent(getApplicationContext(), SettingListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
    }

    private void setSpinner() {
        Call<List<PetBasicInfo>> call = NetRetrofit.getInstance().getPetBasicInfoService().getMyPetList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID));
        new AbstractAsyncTaskOfList<PetBasicInfo>() {
            @Override
            protected void doPostExecute(List<PetBasicInfo> data) {
                if(data.size() > 0){
                    adapterSpinner = new AdapterSpinner(getApplicationContext(), data);
                    binding.appBarNavigation.userSelect.setAdapter(adapterSpinner);
                    binding.appBarNavigation.userSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @SuppressLint("ResourceAsColor")
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            PetBasicInfo info = (PetBasicInfo) parent.getItemAtPosition(position);
                            showToast("info : " + info.getPetName());
                            WeightFragment tf = (WeightFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                            tf.setBcsMessage(info.getId());
                        }
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }
            @Override
            protected void doPreExecute() {
            }
        }.execute(call);
    }


    @Override
    protected BaseActivity<HomeActivity> getActivityClass() {
        return HomeActivity.this;
    }

    private void onClickSettingBtn(View view){
        Intent intent = new Intent(getApplicationContext(), SettingListActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
        finish();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_weight:
                    replaceFragment(WeightFragment.newInstance());
                    binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_title)));
                    return true;
                case R.id.navigation_temp:
                    binding.setActivityInfo(new ActivityInfo(getString(R.string.temp_title)));
                    replaceFragment(TempFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    // Fragment 변환을 해주기 위한 부분, Fragment의 Instance를 받아서 변경
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), R.string.press_back_message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeBottomNavigationSize(BottomNavigationView bottomNavigationView){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }
}
