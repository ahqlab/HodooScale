package com.animal.scale.hodoo.activity.home.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.fragment.activity.ActivityFragment;
import com.animal.scale.hodoo.activity.home.fragment.dashboard.DashBoardFragment;
import com.animal.scale.hodoo.activity.home.fragment.meal.MealFragment;
import com.animal.scale.hodoo.activity.home.fragment.temp.TempFragment;
import com.animal.scale.hodoo.activity.home.fragment.weight.WeightFragment;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.setting.list.SettingListActivity;
import com.animal.scale.hodoo.adapter.AbsractCommonAdapter;
import com.animal.scale.hodoo.adapter.AdapterOfPets;
import com.animal.scale.hodoo.adapter.AdapterSpinner;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.base.FragmentTip;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivityHomeBinding;
import com.animal.scale.hodoo.databinding.PetsListviewItemBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.animal.scale.hodoo.domain.WeightTip;
import com.animal.scale.hodoo.domain.single.ActivityStack;
import com.animal.scale.hodoo.domain.single.PetAllInfo;
import com.animal.scale.hodoo.helper.BottomNavigationViewHelper;
import com.animal.scale.hodoo.util.BadgeUtils;
import com.animal.scale.hodoo.util.DateUtil;
import com.animal.scale.hodoo.util.VIewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.animal.scale.hodoo.constant.HodooConstant.DEBUG;

public class HomeActivity extends BaseActivity<HomeActivity> implements NavigationView.OnNavigationItemSelectedListener, HomeActivityIn.View {

    public static HomeActivity hActivity;

    private final long FINISH_INTERVAL_TIME = 2000;

    private final int ADD_PET = 0;

    private long backPressedTime = 0;

    public ActivityHomeBinding binding;
    //Spinner adapter
    AdapterSpinner adapterSpinner;

    HomeActivityIn.Presenter presenter;

    AdapterOfPets adapter;

    public boolean isDropdonw = false;

    AlertDialog.Builder alertDialog;

    AlertDialog cumtomPetListDialog;

    private List<PetAllInfos> data;

    private BottomNavigationView navigation;

    public static String mCalendarDate = "";

    AbsractCommonAdapter<PetAllInfos> apaterOfPetList;

    public int sharedPetIdx = 0;

    public int localListViewHeight = 0;

    public static PetAllInfos selectPet;

    public static WeightTip mWeightTip;

    public static FragmentTip[] fragmentTips = new FragmentTip[2];

    private HodooApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hActivity = HomeActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setActivity(this);
        presenter = new HomeActivityPresenter(this);
        presenter.loadData(HomeActivity.this);

        app = (HodooApplication) getApplication();
        app.setExperienceState(getIntent().getBooleanExtra(SharedPrefVariable.EXPERIENCE_KEY, false));


        if ( !app.isExperienceState() )
            presenter.loginCheck();
        else
            setFragment();
    }

    public void onPetImageClick(View view) {
        cumtomPetListDialog.show();
    }

    /* 셋팅버튼 이벤트 처리 */
    public void onSettingBtnClick(View view) {
        Intent intent = new Intent(getApplicationContext(), SettingListActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected BaseActivity<HomeActivity> getActivityClass() {
        return HomeActivity.this;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
//                case R.id.navigation_home :
//                    binding.setActivityInfo(new ActivityInfo("Home"));
//                    bundle.putSerializable("selectPet", selectPet);
//                    DashBoardFragment df = DashBoardFragment.newInstance();
//                    df.setArguments(bundle);
//                    replaceFragment(df);
//                    return true;
                case R.id.navigation_weight:
                    binding.setActivityInfo(new ActivityInfo(getString(R.string.weight_title)));
                    bundle.putSerializable("selectPet", selectPet);
                    WeightFragment wf = WeightFragment.newInstance();
                    wf.setArguments(bundle);
                    replaceFragment(wf);
                    return true;
                case R.id.navigation_settings :
                    Intent intent = new Intent(HomeActivity.this, SettingListActivity.class);
                    startActivity(intent);
                    return false;
//                case R.id.navigation_temp:
//                    binding.setActivityInfo(new ActivityInfo(getString(R.string.temp_title)));
//                    replaceFragment(TempFragment.newInstance());
//                    mViewPager.setCurrentItem(1);
//                    presenter.loadCustomDropdownView();
//                    return true;
//                case R.id.navigation_meal:
//                    binding.setActivityInfo(new ActivityInfo(getString(R.string.meal_title)));
//                    bundle.putSerializable("selectPet", selectPet);
//                    MealFragment mf = MealFragment.newInstance();
//                    mf.setArguments(bundle);
//                    replaceFragment(mf);
//                    return true;
//                case R.id.navigation_activity:
//                    replaceFragment(ActivityFragment.newInstance());
//                    binding.setActivityInfo(new ActivityInfo(getString(R.string.activity)));
//                    return true;
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finishAffinity();
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), R.string.press_back_message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void changeBottomNavigationSize(BottomNavigationView bottomNavigationView) {
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

    @Override
    public void setCustomPetListDialog(final List<PetAllInfos> petAllInfos) {

        saveDefaultPetIdx(petAllInfos);

        /* 상단 우측 펫 리스트 정렬 관련 (s) */
        List<PetAllInfos> tempList = new ArrayList<>();
        Collections.addAll(petAllInfos);

        Iterator<PetAllInfos> iterator = petAllInfos.iterator();
        while (iterator.hasNext()) {
            PetAllInfos infos = iterator.next();
            if ( infos == selectPet ) {
                tempList.add(infos);
                iterator.remove();
                break;
            }
        }
        tempList.addAll(petAllInfos);
        petAllInfos.clear();
        petAllInfos.addAll(tempList);
        /* 상단 우측 펫 리스트 정렬 관련 (e) */


        /**
         * 상단 펫 다이얼로그 아답터
         * 데이타 바인딩으로 추가적으로 파일생성을 안함.
         */
        apaterOfPetList = new AbsractCommonAdapter<PetAllInfos>(HomeActivity.this, petAllInfos) {

            PetsListviewItemBinding binding;

            public int listViewHeight = 0;

            @Override
            protected View getUserEditView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = apaterOfPetList.inflater.inflate(R.layout.pets_listview_item, null);
                    binding = DataBindingUtil.bind(convertView);
                    binding.setDomain(data.get(position));
                    binding.setCurrentPetIdx(sharedPetIdx);
                    convertView.setTag(binding);
                } else {
                    binding = (PetsListviewItemBinding) convertView.getTag();
                    binding.setDomain(data.get(position));
                    binding.setCurrentPetIdx(sharedPetIdx);
                }
                convertView.setOnClickListener(new View.OnClickListener() {
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    }
                    public void onClick(View v) {

                        selectPet = apaterOfPetList.data.get(position);
                        Iterator<PetAllInfos> iterator = apaterOfPetList.data.iterator();
                        while (iterator.hasNext()) {
                            if (iterator.next() == selectPet)
                                iterator.remove();
                        }
                        apaterOfPetList.data.add(0, selectPet);
                        mSharedPrefManager.putIntExtra(SharedPrefVariable.CURRENT_PET_IDX, selectPet.getPet().getPetIdx());
                        apaterOfPetList.notifyDataSetChanged();
                        presenter.chageCurcleImageOfSelectPet(selectPet);
                        //presenter.getTipOfCountry(new WeightTip("ko", 3));
                        setFragmentContent();
                    }
                });
                return binding.getRoot();
            }

            /**
             * 생성자 로직에 추가되는 함수
             *
             */
            @Override
            protected void setUsetEditConstructor() {
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.pets_listview_item, null);
                view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                if (data.size() >= 3) {
                    listViewHeight = view.getMeasuredHeight() * 3;
                    localListViewHeight = listViewHeight;
                }
                //Log.e(TAG, String.format("height : %d", view.getMeasuredHeight()));
            }
        };

        alertDialog = super.showBasicOneBtnPopup(null, null);
        View customView = getLayoutInflater().inflate(R.layout.pet_list_dialog, null);
        ListView listview = (ListView) customView.findViewById(R.id.pet_listview);
        listview.setAdapter(apaterOfPetList);
        if (localListViewHeight > 0) {
            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = localListViewHeight;
            listview.setLayoutParams(params);
        }
        alertDialog.setView(customView);
        Button addBtn = (Button) customView.findViewById(R.id.add_pet);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PetRegistActivity.class);
                intent.putExtra("petIdx", ADD_PET);
                startActivity(intent);
                //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
            }
        });
        cumtomPetListDialog = alertDialog.create();
        cumtomPetListDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    /**
     * 현제 프레그먼트에 따라 각 프레그먼트의 함수를 호출한다.
     */
    public void setFragmentContent() {
        android.support.v4.app.Fragment tf = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if ( tf instanceof DashBoardFragment ) {
            DashBoardFragment dashBoardFragment = (DashBoardFragment) tf;
            dashBoardFragment.setSelectPet(selectPet);
        }
        else if (tf instanceof WeightFragment) {
            WeightFragment weightFragment = (WeightFragment) tf;
            weightFragment.setBcsOrBscDescAndTip(selectPet);
            //일일 데이터 삭제
            //weightFragment.serChartOfDay();
            weightFragment.setKg();
            weightFragment.refrashChart();
            weightFragment.getTipMessageOfCountry(selectPet);
        } else if (tf instanceof MealFragment) {
            MealFragment mealFragment = (MealFragment) tf;
            mealFragment.initRaderChart(DateUtil.getCurrentDatetime());
            mealFragment.setPetAllInfo(selectPet);
        } else if (tf instanceof TempFragment) {
            TempFragment tempFragment = (TempFragment) tf;
            tempFragment.drawChart();
        }
    }
    /**
     * 처음 앱에 진입시 CURRENT_PET_IDX 를 List 첫번째 id로 정한다.
     *
     * @param data
     */
    private void saveDefaultPetIdx(List<PetAllInfos> data) {
//        Log.e(TAG, String.format("pet idx : %d", mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX)));

        /* 체험하기 버튼 클릭시 임의의 데이터 설정 (s) */
        if ( app.isExperienceState() ) {
            selectPet = null;
//            data.clear();
//            PetAllInfos experienceData = new PetAllInfos();
//            Pet experiencePet = new Pet();
//            experiencePet.setPetIdx(0);
//            experienceData.setPet( experiencePet );
//            data.add(experienceData);
        } else {
            if (mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX) == 0) {
                mSharedPrefManager.putIntExtra(SharedPrefVariable.CURRENT_PET_IDX, data.get(0).getPet().getPetIdx());
                selectPet = data.get(0);
            } else {
                for (int i = 0; i < data.size(); i++) {
                    if( data.get(i).getPet().getPetIdx() == mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX) ) {
                        selectPet = data.get(i);
                        break;
                    }
                }
            }
        }
        /* 체험하기 버튼 클릭시 임의의 데이터 설정 (e) */

        setFragmentContent();
    }

    /**
     * 펫의 프로필 사진을 셋팅한다.
     * lib picasso
     * @param info
     */
    @Override
    public void setCurcleImage(PetAllInfos info) {
        Picasso.with(this)
                .load(SharedPrefVariable.SERVER_ROOT + info.getPetBasicInfo().getProfileFilePath())
                .error(R.drawable.icon_pet_profile)
                .into(binding.appBarNavigation.petImage);
    }

    /**
     * bedge 관련
     */
    @Override
    public void refreshBadge() {
        this.setBadge();
    }

    /**
     * bedge 관련
     */
    @Override
    public void setPushCount(int count) {
        if (count <= 0) {
            BadgeUtils.clearBadge(this);
        } else {
            BadgeUtils.setBadge(this, Math.min(count, 99));
        }
    }

    /**
     * 메인페이지로 이동한다.
     */
    @Override
    public void moveLoginActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, true);
        startActivity(intent);
        finish();
    }

    @Override
    public void setFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        WeightFragment fragment = WeightFragment.newInstance();
        fragmentTransaction.add(R.id.fragment_container, fragment).commit();
        binding.setActivityInfo(new ActivityInfo("Home"));
        VIewUtil vIewUtil = new VIewUtil(HomeActivity.this);
        VIewUtil.getLocationCode(HomeActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadCustomPetListDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityStack<HomeActivity> stack =  ActivityStack.getInstance();
        Log.e("HJLEE", " >>>>> " + stack.getClasses().size());
        //stack.getClasses().get(0).finish();
        sharedPetIdx = mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX);

        presenter.getInvitationToServer();
        setBadge();
        int notitype = getIntent().getIntExtra(HodooConstant.NOTI_TYPE_KEY, -1);
        setCalendarDate("");//푸시가 왔을때 초기 캘린더를 보여주기 위한 캘린더 초기화
        if (notitype >= 0) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("push", true);
            switch (notitype) {
                case HodooConstant.FIREBASE_WEIGHT_TYPE :
                    binding.setActivityInfo(new ActivityInfo("Home"));
                    WeightFragment wf = WeightFragment.newInstance();
                    wf.setArguments(bundle);
                    replaceFragment(wf);
                    navigation.setSelectedItemId(R.id.navigation_weight);
                    presenter.loadCustomPetListDialog();
                    break;
//                case HodooConstant.FIREBASE_FEED_TYPE :
//                    MealFragment mf = MealFragment.newInstance();
//                    mf.setArguments(bundle);
//                    replaceFragment(mf);
//                    navigation.setSelectedItemId(R.id.navigation_meal);
//                    binding.setActivityInfo(new ActivityInfo(getString(R.string.meal_title)));
//                    break;
            }
        }
        Log.e(TAG, String.format(" shared pet idx : %d ", mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX)));
        if ( sharedPetIdx != mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX) ) {
            sharedPetIdx = mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX);


            Log.e(TAG, "sharedPetIdx 다름");
            Log.e(TAG, String.format("null check : %b, sharedPetIdx : %d, id : %d", apaterOfPetList != null, sharedPetIdx, mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX)));
        }
//        Log.e(TAG, String.format("null check : %b, id : %d", apaterOfPetList != null, mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX)));
    }
    public static void setCalendarDate ( String calendarDate ) {
        mCalendarDate = calendarDate;
    }

    public static String getCalendarDate () {
        return mCalendarDate;
    }

    public static void setWeightTip ( WeightTip weightTip ) {
        mWeightTip = weightTip;
    }

    @Override
    public void setTipOfCountry(WeightTip weightTips) {
        Log.e("HJLEE", "weightTips  ) : " + weightTips);
    }
}
