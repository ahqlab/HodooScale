package com.animal.scale.hodoo.activity.pet.regist.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.PetWeightInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public interface PetRegistIn {


    interface View {
        void setBasicInfo( PetBasicInfo basicInfo );

        /* BasicInfomation (s) */
        void getAllPetBreed(PetBasicInfo basicInfo, List<PetBreed> breeds);
        /* BasicInfomation (e) */

        void nextStep( int type );
        void setPetIdx( int petIdx );

        void registBasicInfo( int result );

        /* DiseaseInfomation (s) */
        void registDiseaseInfo ();
        /* DiseaseInfomation (e) */

        void registPhysiqueInfo();
        void registWeightInfo();

        void registFinish();
    }
    interface Presenter {
        void loadData (Context context);

        /* BasicInfomation (s) */
        void getPetBasicInformation(String location, int petIdx);
//        void getAllPetBreed( PetBasicInfo basicInfo, String location, int typeIdx );

        void registPetType( int petType, boolean editType );

        void registBasicInfo ( String requestUrl, PetBasicInfo info, CircleImageView profile );
        /* BasicInfomation (e) */

        /* DiseaseInfomation (s) */
//        void registDiseaseInfo ();
        void deleteDiseaseInformation( int petIdx, int diseaseIdx );
        void registDiseaseInformation(PetChronicDisease petChronicDisease, int petIdx);

        void deletePhysiqueInformation( int petIdx, int physiqueIdx );
        void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain);


        void deleteWeightInfo(int petIdx, int id);
        void registWeightInfo(int petIdx, PetWeightInfo domain);

        /* DiseaseInfomation (e) */
    }
}
