package com.animal.scale.hodoo.activity.setting.pet.accounts;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.AbstractAsyncTaskOfList;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.service.NetRetrofit;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
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
        Call<List<PetAllInfos>> call = NetRetrofit.getInstance().getPetBasicInfoService().aboutMyPetList(mSharedPrefManager.getStringExtra(SharedPrefVariable.GROUP_CODE));
        new AbstractAsyncTaskOfList<PetAllInfos>() {
            @Override
            protected void doPostExecute(List<PetAllInfos> data) {
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

    public void addRegistBtn(List<PetAllInfos> data) {
        PetAllInfos  infos = new PetAllInfos();
        PetBasicInfo info = new PetBasicInfo();
        info.setPetName(context.getString(R.string.basin_info_regist_title));
        info.setProfileFilePath("add");
        infos.setPetBasicInfo(info);
        data.add(0, infos);
    }
    public int getSelectedPetIdx() {
        return mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX);
    }
    public void saveCurrentIdx(int idx) {
        mSharedPrefManager.putIntExtra(SharedPrefVariable.CURRENT_PET_IDX, idx);
    }

    public interface asyncTaskListner {
        void doPostExecute(List<PetAllInfos> data);
        void doPreExecute();
    }
}
