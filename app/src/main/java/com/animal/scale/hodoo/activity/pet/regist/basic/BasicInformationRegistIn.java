package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.EditText;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;

import java.io.File;
import java.util.List;

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

        void setSaveImageFile ( Bitmap image );

        void getAllPetBreed(List<PetBreed> breeds);

    }

    interface Presenter{

        void loadData(Context applicationContext);

        public android.view.View onClickOpenBottomDlg();

        void openCamera();

        void callDatePickerDialog(EditText getDate);

        void setNavigation();

        void registBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile);

        void updateBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile);

        void getPetBasicInformation(String location, int petIdx);

        void setView(PetBasicInfo basicInfo);

        File setSaveImageFile(Context context );

        void rotationImage ( String imageFilePath );

        void setGalleryImage (Context context, Uri img);

        void getAllPetBreed( String location );
    }
}
