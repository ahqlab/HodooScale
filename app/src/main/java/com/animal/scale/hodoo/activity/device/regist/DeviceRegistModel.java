package com.animal.scale.hodoo.activity.device.regist;

import android.content.Context;
import android.os.AsyncTask;

import com.animal.scale.hodoo.common.AbstractAsyncTask;
import com.animal.scale.hodoo.common.AsyncTaskCancelTimerTask;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.Device;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.animal.scale.hodoo.util.MathUtil;

import retrofit2.Call;

public class DeviceRegistModel extends CommonModel {

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void loadData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void tempRegist(final TempRegistListener tempRegistListener) {

        Device device = new Device();
        device.setGroupCode(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        device.setSerialNumber(MathUtil.getGroupId());
        Call<Integer> call = NetRetrofit.getInstance().getDeviceService().insertDevice(device);
        new AsyncTaskCancelTimerTask(new AbstractAsyncTask<Integer>() {
            @Override
            protected void doPostExecute(Integer result) {
                tempRegistListener.doPostExecute(result);
            }
            @Override
            protected void doPreExecute() {
                tempRegistListener.doPreExecute();
            }
        }.execute(call), limitedTime, interval, true).start();
    }

    public boolean checkInvitation() {
        return mSharedPrefManager.getBooleanExtra(SharedPrefVariable.INVITATION_STATE);
    }
    public String getInvitationUserEmail() {
        return mSharedPrefManager.getStringExtra(SharedPrefVariable.INVITATION_USER_EMAIL);
    }

    public interface TempRegistListener {
        void doPostExecute(Integer result);
        void doPreExecute();
    }
}
