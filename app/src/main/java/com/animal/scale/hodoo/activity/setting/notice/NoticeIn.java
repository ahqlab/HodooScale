package com.animal.scale.hodoo.activity.setting.notice;

import android.content.Context;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Notice;

import java.util.List;

public interface NoticeIn {

    interface View {

        void setNoticeListview(List<Notice> d);
    }

    interface Presenter {

        void loadModel(Context context);

        void getNoticeList(int startRow, int pageSize);
    }
}
