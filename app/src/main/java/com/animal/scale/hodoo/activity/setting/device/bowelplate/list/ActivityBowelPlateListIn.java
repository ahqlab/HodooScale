package com.animal.scale.hodoo.activity.setting.device.bowelplate.list;

import android.content.Context;

import com.animal.scale.hodoo.domain.Device;

import java.util.List;

public interface ActivityBowelPlateListIn {

    interface View{

        void MyBowlPlateList(List<Device> d);

        void setChangeSwithStatusResult(Integer integer);
    }

    interface Presenter{

        void getMyBowlPlateList();

        void loadData(Context context);

        void setChangeSwithStatus(int deviceIdx, boolean b);
    }
}
