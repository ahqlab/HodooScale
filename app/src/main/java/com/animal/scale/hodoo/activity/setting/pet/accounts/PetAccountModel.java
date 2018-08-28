package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Context;

import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.service.NetRetrofit;

import java.util.List;

import retrofit2.Call;

public class PetAccountModel {

    Context context;

    public SharedPrefManager mSharedPrefManager;

    public void initUserData(Context context) {
        this.context = context;
        mSharedPrefManager = SharedPrefManager.getInstance(context);
    }

    public void getPetData(final asyncTaskListner asyncTaskListner) {
        Call<List<PetBasicInfo>> call = NetRetrofit.getInstance().getPetBasicInfoService().getMyRegisteredPetList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GEOUP_ID));
        new AbstractAsyncTaskOfList<PetBasicInfo>() {
            @Override
            protected void doPostExecute(List<PetBasicInfo> data) {
                if(data.size() > 0){
                    asyncTaskListner.doPostExecute(data);
                }
            }
            @Override
            protected void doPreExecute() {
                asyncTaskListner.doPreExecute();
            }
        }.execute(call);
    }

    public void addRegistBtn(List<PetBasicInfo> data) {
        PetBasicInfo info = new PetBasicInfo();
        info.setPetName("펫 추가");
        info.setProfileFilePath("add");
        data.add(0, info);
    }


    public interface asyncTaskListner {
        void doPostExecute(List<PetBasicInfo> data);
        void doPreExecute();
    }
}
