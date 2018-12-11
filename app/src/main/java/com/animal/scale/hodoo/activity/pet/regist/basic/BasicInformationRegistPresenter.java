package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class BasicInformationRegistPresenter implements BasicInformationRegistIn.Presenter{

    BasicInformationRegistIn.View view;

    BasicInformationRegistModel model;

    public BasicInformationRegistPresenter(BasicInformationRegistIn.View view) {
        this.view = view;
        this.model = new BasicInformationRegistModel();
    }

    @Override
    public void loadData(Context context) {
        model.loadData(context);
    }

    @Override
    public View onClickOpenBottomDlg() {
        return model.onClickOpenBottomDlg();
    }

    @Override
    public void openCamera() {
        view.openCamera();
    }

    @Override
    public void callDatePickerDialog(EditText getDate) {
        model.callDatePickerDialog(getDate);
    }

    @Override
    public void setNavigation() {
        view.setNavigation();
    }

    @Override
    public void registBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile) {
        model.registBasicInfo(requestUrl, info, profile, new BasicInformationRegistModel.BasicInfoRegistListner() {
            @Override
            public void doPostExecute(Pet pet) {
                Log.e("HJLEE", pet.toString());
                view.setProgress(false);
                view.goNextPage(pet);
            }

            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }
        });
    }

    @Override
    public void updateBasicInfo(String requestUrl, PetBasicInfo info, CircleImageView profile) {
        model.updateBasicInfo(requestUrl, info, profile, new BasicInformationRegistModel.BasicInfoUpdateListner() {
            @Override
            public void doPostExecute() {
                view.setProgress(false);
                view.successUpdate();
            }

            @Override
            public void doPreExecute() {
                view.setProgress(true);
            }
        });
    }

    @Override
    public void getPetBasicInformation(int petIdx) {
        model.getPetBasicInformation(petIdx, new BasicInformationRegistModel.PetBasicInformationResultListner() {
            @Override
            public void doPostExecute(PetBasicInfo basicInfo) {
                view.setBasicInfo(basicInfo);
            }

            @Override
            public void doPreExecute() {
                //view.showErrorToast();
            }
        });
    }

    @Override
    public void setView(PetBasicInfo basicInfo) {
        view.setView(basicInfo);
    }
}
