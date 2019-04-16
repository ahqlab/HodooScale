package com.animal.scale.hodoo.activity.pet.regist.fragment.basic;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistModel;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetBreed;
import com.animal.scale.hodoo.domain.PetChronicDisease;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-02..
 */
public class BasicInfomationPresenter implements BasicInfomationIn.Presenter {
    private BasicInfomationFragment view;
    private Context context;
    private BasicInfomationModel model;

    BasicInfomationPresenter ( BasicInfomationFragment view ) {
        this.view = view;
        model = new BasicInfomationModel();
    }

    @Override
    public void loadData(Context context) {
        this.context = context;
        model.loadData(context);
        view.setNavigation();

    }

    @Override
    public void setView(PetBasicInfo basicInfo) {
        view.setView(basicInfo);
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

    @Override
    public void getAllPetBreed(String location, int typeIdx) {
        model.getAllPetBreed(location, typeIdx, new CommonModel.ObjectCallBackListner<CommonResponce<List<PetBreed>>>() {
            @Override
            public void doPostExecute(CommonResponce<List<PetBreed>> d) {
                view.getAllPetBreed(d);
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
    public void openCamera() {
        view.openCamera();
    }
    @Override
    public File setSaveImageFile(Context context ) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "hodoo_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,      /* prefix */
                    ".jpg",         /* suffix */
                    storageDir          /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    @Override
    public void rotationImage( String imageFilePath ) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }
        bitmap = rotate(bitmap, exifDegree);
        bitmap = resizeBitmap(bitmap);
        view.setSaveImageFile(bitmap);
    }
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    public Bitmap resizeBitmap(Bitmap original) {

        int resizeWidth = 500;

        double aspectRatio = (double) original.getHeight() / (double) original.getWidth();
        int targetHeight = (int) (resizeWidth * aspectRatio);
        Bitmap result = Bitmap.createScaledBitmap(original, resizeWidth, targetHeight, false);
        if (result != original) {
            original.recycle();
        }
        return result;
    }
    @Override
    public void setGalleryImage(Context context, Uri img) {
        String imagePath = getRealPathFromURI(context, img); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        bitmap = resizeBitmap(bitmap);
        view.setSaveImageFile(bitmap);
    }

    @Override
    public void getDiseaseInformation(int petIdx) {
        model.getDiseaseformation(petIdx, new CommonModel.DomainCallBackListner<PetChronicDisease>() {

            @Override
            public void doPostExecute(PetChronicDisease petChronicDisease) {
                view.setDiseaseInfo(petChronicDisease);
            }

            @Override
            public void doPreExecute() {

            }

            @Override
            public void doCancelled() {

            }
        });
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }
}
