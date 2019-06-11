package com.animal.scale.hodoo.activity.pet.regist.fragment.basic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.io.File;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public interface BasicInfomationIn {
    interface View {
        void setNavigation();
        void setView(PetBasicInfo basicInfo);
        void getAllPetBreed(CommonResponce<List<PetBreed>> d);
        void openCamera();
        void setSaveImageFile ( Bitmap image );
        void setBasicInfo(PetBasicInfo basicInfo);
        void setDiseaseInfo(PetChronicDisease petChronicDisease);
    }
    interface Presenter {
        void getPetBasicInformation(String location, int petIdx);
        void loadData (Context context);
        void setView(PetBasicInfo basicInfo);
        void getAllPetBreed( String location, int typeIdx );
        void openCamera();
        File setSaveImageFile(Context context );
        void rotationImage ( String imageFilePath );
        void setGalleryImage (Context context, Uri img);
        void getDiseaseInformation(int petIdx);
    }
}
