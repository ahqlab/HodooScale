package com.animal.scale.hodoo.activity.setting.notice;

import android.content.Context;

import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.constant.HodooConstant;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Notice;

import java.util.List;

public class NoticePresenter implements NoticeIn.Presenter {

    private NoticeIn.View view;

    private NoticeModel model;


    NoticePresenter(NoticeIn.View view ) {
        this.view = view;
        this.model = new NoticeModel();
    }

    @Override
    public void loadModel(Context context) {
        model.loadData(context);
    }

    @Override
    public void getNoticeList(int startRow, int pageSize) {
        model.getNoticeList(startRow, pageSize, new CommonModel.CommonDomainListCallBackListner<Notice>() {
            @Override
            public void doPostExecute(CommonResponce<List<Notice>> d) {
                if(d.getStatus() == HodooConstant.OK_RESPONSE|| d.getStatus() == HodooConstant.NO_CONTENT_RESPONSE)
                 view.setNoticeListview(d.getDomain());
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }


}
