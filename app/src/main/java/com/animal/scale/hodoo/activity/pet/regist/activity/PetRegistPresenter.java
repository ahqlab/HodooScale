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

    /**
     * 서버에서 PetBasicInfo를 가져온다.
     *
     * @param location   위치값 -> 언어를 선택하기 위해 사용
     * @param petIdx     가져올 펫의 인덱스
     * @return
    */
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

    /**
     * 펫 타입을 등록한다.
     *
     * @param petType   펫의 타입 1 : 강아지, 2 : 고양이
     * @param editType  수정인지 등록인지 체크하는 상태값
     * @return
    */
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

    /**
     * 펫의 기본정보를 등록한다.
     *
     * @param requestUrl   등록할 서버 URL
     * @param info         펫의 정보가 들어있는 도메인
     * @param profile      펫의 프로필 사진
     * @return
    */
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

    /**
     * 펫의 기본 정보를 업데이트 한다.
     *
     * @param requestUrl   등록할 서버 URL
     * @param info         펫의 정보가 들어있는 도메인
     * @param profile      펫의 프로필 사진
     * @return
    */
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

    /**
     * 펫의 병력 값을 삭제한다. *펫의 병력을 삭제 후 다시 등록시킨 후 인덱스 값을 저장한다.
     *
     * @param petIdx       펫의 인덱스 값
     * @param diseaseIdx   등록할 병력의 이진값 (0, 2, 4, 8, 16 ...)
     * @return
    */
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

    /**
     * 병력을 등록한다.
     *
     * @param petChronicDisease   병력의 정보가 들어있는 도메인
     * @param petIdx              펫의 인덱스 값
     * @return
    */
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

    /**
     * 펫의 피지컬 값을 삭제한다 (펫 병력과 동일한 로직)
     *
     * @param petIdx        펫의 인덱스 값
     * @param physiqueIdx   피지컬의 인덱스 값
     * @return
    */
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
    /**
     * 피지컬 정보를 등록한다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param domain   피지컬의 정보가 들어있는 도메인
     * @return
    */
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
    /**
     * 펫의 몸무게 정보를 삭제 한다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param id       몸무게 정보의 인덱스 값
     * @return
    */
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

    /**
     * 펫의 몸무게 정보를 등록한다.
     *
     * @param petIdx   펫의 인덱스 값
     * @param domain   펫의 몸무게 정보가 있는 도메인
     * @return
    */
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

    /**
     * 펫의 활동 정보를 등록한다.
     *
     * @param petIdx                     펫의 인덱스 값
     * @param petUserSelectionQuestion   펫의 활동 정보가 들어있는 도메인
     * @return
    */
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

    /**
     * 펫의 활동 정보를 삭제한다.
     *
     * @param petIdx        펫의 인덱스 값
     * @param questionIdx   펫의 활동 정보의 인덱스 값
     * @return
    */
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
