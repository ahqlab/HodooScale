package com.animal.scale.hodoo.activity.setting.notice;

import android.content.Context;

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


}
