package com.animal.scale.hodoo.activity.pet.regist.basic;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.animal.scale.hodoo.domain.Pet;
import com.animal.scale.hodoo.domain.PetBasicInfo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
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
