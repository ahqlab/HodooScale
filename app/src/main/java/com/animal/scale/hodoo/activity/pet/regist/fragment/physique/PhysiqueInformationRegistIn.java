package com.animal.scale.hodoo.activity.pet.regist.fragment.physique;

import android.content.Context;
import android.widget.EditText;

import com.animal.scale.hodoo.domain.PetPhysicalInfo;

public interface PhysiqueInformationRegistIn {

    interface View {

        void setNavigation();

        void setDiseaseInfo(PetPhysicalInfo petPhysicalInfo);

        void showRulerBottomDlg(final EditText editText, String value);

        void registPhysiqueInformation();

        void registResult(Integer result);
    }

    interface Presenter {

        void setNavigation();

        void getPhysiqueInformation(int petIdx);

        void loadData(Context context);

        void showRulerBottomDlg(EditText editText, String value);

        void deletePhysiqueInformation(int petIdx, int id);

        void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain);
    }
}
