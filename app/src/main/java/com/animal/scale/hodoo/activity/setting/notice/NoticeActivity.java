package com.animal.scale.hodoo.activity.setting.notice;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CompoundButton;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.adapter.AbsractCommonAdapter;
import com.animal.scale.hodoo.adapter.BaseExpandableAdapter;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityNoticeBinding;
import com.animal.scale.hodoo.databinding.NoticeListviewItemBinding;
import com.animal.scale.hodoo.databinding.NotificationListviewItemBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.Notice;
import com.animal.scale.hodoo.domain.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * 공지사항 Activity
 */
public class NoticeActivity extends BaseActivity<NoticeActivity> implements NoticeIn.View {

    ActivityNoticeBinding binding;

    NoticeIn.Presenter presenter;

    AbsractCommonAdapter<Notice> adapterOfNotice;

    public int startRow = 0;

    public int pageSize = 20;

    private LayoutInflater inflater;

    private boolean mLockListview;

    private ArrayList<Notice> mGroupList = null;

    private ArrayList<ArrayList<Notice>> mChildList = null;

    private ArrayList<Notice> mChildListContent = null;

    BaseExpandableAdapter adapter;

    View footerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notice);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.activity_title_notice)));
        super.setToolbarColor();
        presenter = new NoticePresenter(NoticeActivity.this);
        presenter.loadModel(NoticeActivity.this);
        mLockListview = true;
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initNoticeAdapter();
        addItem(startRow, pageSize);
    }


    /**
     * 제목과 내용을 분리하기 위해 Expenderble 리스트뷰를 사용함.
     */
    private void initNoticeAdapter() {

        List<Notice> list = new ArrayList<Notice>();
        List<Notice> list2 = new ArrayList<Notice>();

        ArrayList<Notice> mGroupList = new ArrayList<Notice>();
        ArrayList<ArrayList<Notice>> mChildList = new ArrayList<ArrayList<Notice>>();
        ArrayList<Notice> mChildListContent = new ArrayList<Notice>();

        for (int i = 0; i < list.size(); i++) {
            mGroupList.add(list.get(i));
            mChildListContent = new ArrayList<Notice>();
            for (int j = 0; j < list2.size(); j++) {
                if (list.get(i).getNoticeIdx() == list2.get(j).getNoticeIdx()) {
                    mChildListContent.add(list2.get(j));
                }
            }
            mChildList.add(mChildListContent);
        }

        adapter = new BaseExpandableAdapter(this, mGroupList, mChildList);
        binding.noticeListview.setAdapter(adapter);

        footerView = inflater.inflate(R.layout.notice_listview_footer, null);
        binding.noticeListview.addFooterView( footerView );
        binding.noticeListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            /**
             * 하단 스크롤 이벤트
             * @param absListView
             * @param firstVisibleItem
             * @param visibleItemCount
             * @param totalItemCount
             */
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = totalItemCount - visibleItemCount;

                if (firstVisibleItem >= count && totalItemCount != 0 && mLockListview == false) {
                    startRow += pageSize;
                    addItem(startRow, pageSize);
                }
            }
        });
    }

    @Override
    protected BaseActivity<NoticeActivity> getActivityClass() {
        return NoticeActivity.this;
    }


    /**
     * 아이템 추가시 딜레이를 준 후 프로그래스를 보여준다.
     * @param startRow
     * @param pageSize
     */
    private void addItem(final int startRow, final int pageSize) {
        mLockListview = true;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                presenter.getNoticeList(startRow, pageSize);
                mLockListview = false;
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(run, 1500);
    }

    @Override
    public void setNoticeListview(List<Notice> noticeList) {
        if ( noticeList.size() == 0 ) {
            binding.noticeListview.removeFooterView( footerView );
            return;
        }

        List<Notice> list = noticeList;
        List<Notice> list2 = noticeList;

        ArrayList<Notice> mGroupList = new ArrayList<Notice>();
        ArrayList<ArrayList<Notice>> mChildList = new ArrayList<ArrayList<Notice>>();
        ArrayList<Notice> mChildListContent = new ArrayList<Notice>();

        for (int i = 0; i < list.size(); i++) {
            mGroupList.add(list.get(i));
            mChildListContent = new ArrayList<Notice>();
            for (int j = 0; j < list2.size(); j++) {
                if (list.get(i).getNoticeIdx() == list2.get(j).getNoticeIdx()) {
                    mChildListContent.add(list2.get(j));
                }
            }
            mChildList.add(mChildListContent);
        }
        adapter.groupList.addAll(mGroupList);
        adapter.childList.addAll(mChildList);
        adapter.notifyDataSetChanged();
    }
}
