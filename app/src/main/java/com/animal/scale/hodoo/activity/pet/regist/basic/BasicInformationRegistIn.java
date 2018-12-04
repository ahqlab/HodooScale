package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.content.Context;
import android.widget.EditText;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public interface BasicInformationRegistIn {

    interface View{

        void setNavigation();

        void openCamera();

        void setProgress(Boolean play);

        void setBasicInfo(PetBasicInfo basicInfo);

        void showErrorToast();

        void setView(PetBasicInfo basicInfo);

        void goNextPage(Pet pet);

        void successUpdate();

    }

    interface Presenter{

        void loadData(Context applicationContext);

        public android.view.View onClickOpenBottomDlg();

        void openCamera();

        void callDatePickerDialog(EditText getDate);

        void setNavigation();

        void registBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile);

        void updateBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile);

        void getPetBasicInformation(int petIdx);

        void setView(PetBasicInfo basicInfo);
    }
}
