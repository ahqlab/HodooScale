package com.animal.scale.hodoo.activity.pet.regist.activity;

import android.content.Context;

import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;
import com.animal.scale.hodoo.domain.PetWeightInfo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public class PetRegistPresenter implements PetRegistIn.Presenter {
    PetRegistModel model;
    PetRegistActivity view;

    PetRegistPresenter ( PetRegistActivity view ) {
        this.view = view;
        model = new PetRegistModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public void getPetBasicInformation(String location, int petIdx) {
        model.getPetBasicInformation(location, petIdx, new CommonModel.DomainCallBackListner<PetBasicInfo>() {
            @Override
            public void doPostExecute(PetBasicInfo basicInfo) {
                view.setBasicInfo(basicInfo);
            }

            @Override
            public void doPreExecute() {
                //view.showErrorToast();
            }
            @Override
            public void doCancelled() {

            }
        });
    }
//
//    @Override
//    public void getAllPetBreed(final PetBasicInfo basicInfo, String location, int typeIdx) {
//        model.getAllPetBreed(location, typeIdx, new CommonModel.ObjectCallBackListner<CommonResponce<List<PetBreed>>>() {
//            @Override
//            public void doPostExecute(CommonResponce<List<PetBreed>> d) {
//                view.getAllPetBreed(basicInfo, d);
//            }
//
//            @Override
//            public void doPreExecute() {
//
//            }
//
//            @Override
//            public void doCancelled() {
//
//            }
//        });
//    }

    @Override
    public void registPetType(int petType, boolean editType) {
        String path = "";
        if ( editType )
            path = "update";
        else
            path = "set";
        model.registPetType(path, petType, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                view.registBasicInfo(result);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void registBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile) {
        model.registBasicInfo(requestUrl, info, profile, new CommonModel.DomainCallBackListner<Pet>() {
            @Override
            public void doPostExecute(Pet pet) {
                if ( pet != null )
                    view.setPetIdx(pet.getPetIdx());
                else
                    view.nextStep(PetRegistActivity.DISEASE_TYPE);
            }

            @Override
            public void doPreExecute() {
//                view.setProgress(true);
            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void updateBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile) {
        model.updateBasicInfo(requestUrl, info, profile, new BasicInformationRegistModel.BasicInfoUpdateListner() {
            @Override
            public void doPostExecute() {
                view.nextStep(PetRegistActivity.DISEASE_TYPE);
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void deleteDiseaseInformation(int petIdx, int diseaseIdx) {
        model.deleteDiseaseformation(petIdx, diseaseIdx, new CommonModel.DomainCallBackListner<Integer>()  {
            @Override
            public void doPostExecute(Integer result) {
                view.registDiseaseInfo();
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void registDiseaseInformation(PetChronicDisease petChronicDisease, int petIdx) {
        model.registDiseaseformation(petChronicDisease, petIdx, new CommonModel.DomainCallBackListner<Integer>()  {
            @Override
            public void doPostExecute(Integer result) {
                view.nextStep(PetRegistActivity.PHYSIQUE_TYPE);
            }
            @Override
            public void doPreExecute() {

            }
            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void deletePhysiqueInformation(int petIdx, int physiqueIdx) {
        model.deletePhysiqueInformation(petIdx, physiqueIdx, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                view.registPhysiqueInfo();
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
    @Override
    public void registPhysiqueInformation(int petIdx, PetPhysicalInfo domain) {
        model.registPhysiqueInformation(petIdx, domain, new CommonModel.CommonDomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(CommonResponce<Integer> d) {
                view.nextStep(PetRegistActivity.WEIGHT_TYPE);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
    @Override
    public void deleteWeightInfo(int petIdx, int id) {
        model.deleteWeightInformation(petIdx, id, new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer integer) {
                view.registWeightInfo();
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void registWeightInfo(int petIdx, PetWeightInfo domain) {
        model.registWeightInformation(petIdx, domain, new CommonModel.CommonDomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(CommonResponce<Integer> d) {
                view.nextStep(PetRegistActivity.PET_USER_SELECT_QUESTION_TYPE);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void registPetUserSelectQuestion(int petIdx, PetUserSelectionQuestion petUserSelectionQuestion) {
        model.registPetUserSelectQuestion(petIdx, petUserSelectionQuestion, new CommonModel.ObjectCallBackListner<CommonResponce<Integer>>() {
            @Override
            public void doPostExecute(CommonResponce<Integer> integerCommonResponce) {
                if ( integerCommonResponce.domain > 0 ) {
                    view.registFinish();
                }
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    @Override
    public void deletePetUserSelectQuestion(int petIdx, int questionIdx) {
        model.deletePetUserSelectQuestion(petIdx, questionIdx, new CommonModel.ObjectCallBackListner<CommonResponce<Integer>>() {
            @Override
            public void doPostExecute(CommonResponce<Integer> integerCommonResponce) {
                if ( integerCommonResponce != null ) {
                    if ( integerCommonResponce.domain > 0 )
                        view.registPetUserSelectQuestion();
                }
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }
}
