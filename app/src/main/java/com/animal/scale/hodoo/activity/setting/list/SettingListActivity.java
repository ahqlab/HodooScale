package com.animal.scale.hodoo.activity.setting.list;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.MainActivity;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.account.MyAccountActivity;
import com.animal.scale.hodoo.activity.setting.account.info.ChangeUserInfoActivity;
import com.animal.scale.hodoo.activity.setting.device.bowelplate.list.BowelPlateListActivity;
import com.animal.scale.hodoo.activity.setting.device.feeder.FeederOrderActivity;
import com.animal.scale.hodoo.activity.setting.device.list.DeviceListActivity;
import com.animal.scale.hodoo.activity.setting.alarmManager.AlarmItemListActivity;
import com.animal.scale.hodoo.activity.setting.notice.NoticeActivity;
import com.animal.scale.hodoo.activity.setting.pet.accounts.PetAccountsActivity;
import com.animal.scale.hodoo.activity.setting.user.account.UserAccountActivity;
import com.animal.scale.hodoo.activity.setting.user.group.list.UserGroupListActivity;
import com.animal.scale.hodoo.activity.setting.user.group.manager.UserGroupManagerActivity;
import com.animal.scale.hodoo.activity.setting.version.VersionActivity;
import com.animal.scale.hodoo.adapter.AdapterOfExpandable;
import com.animal.scale.hodoo.adapter.AdapterOfSetting;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.databinding.ActivitySettingListBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.SettingMenu;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * 셋팅 메뉴 리스트 Activity
 */
public class SettingListActivity extends BaseActivity<SettingListActivity> implements SettingList.View{


    public static SettingListActivity settingListActivity;

    ActivitySettingListBinding binding;

    AdapterOfSetting oldAdapter;

    AdapterOfExpandable adapter;

    SettingList.Presenter presenter;

    public final static int MY_CAAOUNT = 0;


    public final static int GENERAL = 0;
    public final static int USER = 1;
/*    public final static int DEVICE= 2;*/
    public final static int HODOO_LINK = 2;
    public final static int PET = 3;

    public final static int DEVICE_SETTING = 4;
    public final static int USER_MANAGEMENT = 5;
    public final static int PET_MANAGEMENT = 6;
    /* public final static int SUPPORT = 4; */

    /* 참조 1-1 (s) */
    // general
    public final static int NOTICE = 0;
   /* public final static int HOMEPAGE = 1;*/
    public final static int APP_VERSION = 1;

    // user
    public final static int ACCOUNT = 0;
    public final static int NOTIFICATION = 1;
    public final static int UNIT = 2;
    public final static int LOGOUT = 3;

    //device
    public final static int POTTY = 0;
    public final static int FEEDER = 1;
    public final static int BED = 2;
    public final static int CUSHION = 3;
    public final static int HARNESS = 4;
    public final static int TAG = 5;
    /* 참조 1-1 (e) */

    //link
    public final static int GROUP_LIST = 0;
    public final static int REQUEST = 1;

    SharedPrefManager sharedPrefManager;
    List<SettingMenu> menus;

    ArrayList<List<SettingMenu>> settingMenus;
    //Bedge count를 위한 push broadcast
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getInvitationCount();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingListActivity = SettingListActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting_list);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.istyle_setting)));
        super.setToolbarColor();
        sharedPrefManager = SharedPrefManager.getInstance(this);
        presenter = new SettingListPresenter(this);
        presenter.loadData(SettingListActivity.this);
        //셋팅 메뉴 리스트를 요청한다. (셋팅메뉴는 xml 로 존재함 array.xml)
        presenter.getStringSettingList(this);
    }

    @Override
    protected BaseActivity<SettingListActivity> getActivityClass() {
        return SettingListActivity.this;
    }

    @Override
    public void setListviewAdapter(int badgeCount, List<SettingMenu> menus) {
        this.menus = menus;
        for (int i = 0; i < menus.size(); i++) {
            if ( i == 3 ) {
                menus.get(i).setBadgeCount(badgeCount);
            } else {
                menus.get(i).setBadgeCount(0);
            }
        }
        oldAdapter = new AdapterOfSetting(SettingListActivity.this, menus);
        binding.settingListview.setAdapter(oldAdapter);
        binding.settingListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == MY_CAAOUNT){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, MyAccountActivity.class, 0,0, false);
                }else if(position == DEVICE_SETTING){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, DeviceListActivity.class, 0,0, false);
                }else if(position == PET_MANAGEMENT){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, PetAccountsActivity.class, 0,0, false);
                }
                /*else if(position == USER_MANAGEMENT){
                    SettingListActivity.super.moveIntent(SettingListActivity.this, UserGroupManagerActivity.class, 0,0, false);
                }*/
            }
        });
    }

    /**
     * 셋팅에서 보여지는 리스트를 셋팅한다.
     *
     * @param title   셋팅에서 사용하는 그룹 타이틀 리스트
     * @param content 셋팅에서 사용하는 차일드 리스트
     * @return
     * @description   확장 리스트 뷰 사용으로 그룹과 차일드 두개의 리스트가 필요하다.
     *                  차일드 리스트 터치시 각각 다른 일을 해야하므로 인덱스를 가지고 조작한다. 인덱스는 상단 변수 선언을 참조(1-1)
    */
    @Override
    public void setExpandableListAdapter(final ArrayList<String> title, final ArrayList<List<SettingMenu>> content) {
        settingMenus = content;
        adapter = new AdapterOfExpandable(this, title, content);

        binding.settingList.setAdapter( adapter );
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            binding.settingList.expandGroup(i);
        }
        binding.settingList.setGroupIndicator(null);
        binding.settingList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
        binding.settingList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int titlePosition, int contentPosition, long l) {
                if(titlePosition == GENERAL){
                    if(contentPosition == NOTICE){
                        SettingListActivity.super.moveIntent(SettingListActivity.this, NoticeActivity.class, 0,0, false);
                   /* }else if(contentPosition == HOMEPAGE){*/

                    }else if(contentPosition == APP_VERSION){
                        SettingListActivity.super.moveIntent(SettingListActivity.this, VersionActivity.class, 0,0, false);
                    }
                }else if ( titlePosition == USER ) {
                    if ( contentPosition == ACCOUNT) {
                        if ( !((HodooApplication) getApplication()).isSnsLoginState() )
                            SettingListActivity.super.moveIntent(SettingListActivity.this, MyAccountActivity.class, 0,0, false);
                        else  //ChangeUserInfoActivity
                            SettingListActivity.super.moveIntent(SettingListActivity.this, ChangeUserInfoActivity.class, 0,0, false);
//                        SettingListActivity.super.moveIntent(SettingListActivity.this, MyAccountActivity.class, 0,0, false);
                    } else if ( contentPosition == NOTIFICATION ) {
                        SettingListActivity.super.moveIntent(SettingListActivity.this, AlarmItemListActivity.class, 0,0, false);
                    } else if ( contentPosition == UNIT ) {
                        final String[] unitStrArr = getResources().getStringArray(R.array.unit_str_arr);
                        final ArrayAdapter<String> alertAdapter = new ArrayAdapter<>(SettingListActivity.this, android.R.layout.simple_list_item_1);
                        alertAdapter.addAll(unitStrArr);
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingListActivity.this)
                                .setAdapter(alertAdapter, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        presenter.saveUnit(i);
                                        content.get(USER).get(UNIT).setSettingStr( unitStrArr[i] );
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                        builder.create().show();

                    }
                    else if ( contentPosition == LOGOUT ) {
                        if ( !((HodooApplication) getApplication()).isSnsLoginState() )
                            presenter.logout();
                        else
                            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onSessionClosed(ErrorResult errorResult) {
                                   // Log.d(TAG, "sessionClosed!!\n" + errorResult.toString());
                                }
                                @Override
                                public void onNotSignedUp() {
                                    //Log.d(TAG, "NotSignedUp!!");
                                }
                                @Override
                                public void onSuccess(Long result) {
                                    ((HodooApplication) getApplication()).setSnsLoginState(false);
                                    presenter.logout();
//                        Toast.makeText(KakaoLoginActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCompleteLogout() {
//                        Toast.makeText(KakaoLoginActivity.this, "Logout!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        return false;
                    }
             /*   } else if ( titlePosition == DEVICE ) {
                    if ( contentPosition == POTTY )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, BowelPlateListActivity.class, 0,0, false);
                    else if ( contentPosition == FEEDER )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, FeederOrderActivity.class, 0,0, false);*/
                } else if ( titlePosition == HODOO_LINK ) {
                    if ( contentPosition == GROUP_LIST )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, UserAccountActivity.class, 0,0, false);
                    else if ( contentPosition == REQUEST )
                        SettingListActivity.super.moveIntent(SettingListActivity.this, UserGroupListActivity.class, 0,0, false);
                } else if ( titlePosition == PET ) {
                    SettingListActivity.super.moveIntent(SettingListActivity.this, PetAccountsActivity.class, 0,0, false);
                }
                return true;
            }
        });
    }

    /**
     * 로그아웃 시 사용하는 로그인페이지로 보내는 메서드
     *
     * @param
     * @return
     * @description
    */
    @Override
    public void goLoginPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(SharedPrefVariable.LOGIN_PAGE_INTENT, true);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);

        finishAffinity();
        finish();
    }
    /**
     * 뱃지 셋팅에 사용하는 메서드
     *
     * @param count   뱃지의 값
     * @return
     * @description
    */
    @Override
    public void updateBadgeCount( int count ) {
        //null 처리
        adapter.setBadge(SettingListActivity.HODOO_LINK, SettingListActivity.REQUEST, count);
//        settingMenus.get(SettingListActivity.HODOO_LINK).get(SettingListActivity.REQUEST).setBadgeCount(count);
//        menus.get(5).setBadgeCount(count);
//        oldAdapter.setData(menus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(HodooConstant.FCM_RECEIVER_NAME));
        presenter.getInvitationCount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    public void logout ( View v ) {
        presenter.logout();
    }
}
