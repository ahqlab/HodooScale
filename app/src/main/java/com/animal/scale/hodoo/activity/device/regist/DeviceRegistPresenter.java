package com.animal.scale.hodoo.activity.device.regist;

import android.content.Context;

public class DeviceRegistPresenter implements DeviceRegistIn.Presenter {

    DeviceRegistIn.View view;
    DeviceRegistModel model;

    public DeviceRegistPresenter(DeviceRegistIn.View view) {
        this.view = view;
        this.model = new DeviceRegistModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void tempRegist() {
        /*model.tempRegist(new DeviceRegistModel.TempRegistListener() {
            @Override
            public void doPostExecute(Integer result) {
                view.setProgress(false);
            }

            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }
        });*/
        view.moveWIFISetting();
    }

    @Override
    public void checkInvitation() {
        if ( model.checkInvitation() ) {
            String invitationUser = model.getInvitationUserEmail();
            if ( invitationUser != null && !invitationUser.equals("") )
                view.moveInvitationFinish( invitationUser );
        }
    }
}
