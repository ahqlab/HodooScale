package com.animal.scale.hodoo.activity.pet.regist.fragment.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.custom.view.BottomDialog;
import com.animal.scale.hodoo.databinding.FragmentPetProfileBinding;
import com.animal.scale.hodoo.domain.IosStyleBottomAlert;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-05-13..
 */
public class PetProfileFragment extends PetRegistFragment implements PetProfileIn.View {

    private FragmentPetProfileBinding binding;
    private PetProfileIn.Presenter presenter;

    private BottomDialog dialog;

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;

    private Uri photoUri;
    private String imageFilePath;

    private PetBasicInfo petBasicInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_profile, container, false);
        presenter = new PetProfilePresenter(this);
//        binding.setActivity(this);
        binding.setActivity(this);
        binding.setChecked(true);
        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickOpenBottomDlg(view);
            }
        });

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PetRegistActivity) getActivity()).setPetBasicInfoData( PetRegistActivity.PET_PROFILE_TYPE, binding.profile );
                ((PetRegistActivity) getActivity()).nextFragment();
//                callback.onDataCallback(2, 1);
            }
        });
        return binding.getRoot();
    }
    public static PetRegistFragment newInstance() {
        return new PetProfileFragment();
    }

    /**
     * 하단 다이얼로그를 오픈한다.
     *
     * @param
     * @return
    */
    public void onClickOpenBottomDlg ( View v ) {
        dialog = BottomDialog.getInstance();
        List<IosStyleBottomAlert> btns = new ArrayList<>();
        btns.add(IosStyleBottomAlert.builder().btnName(getString(R.string.camera)).id(R.id.camera).build());
        btns.add(IosStyleBottomAlert.builder().btnName(getString(R.string.gallery)).id(R.id.gallery).build());
        dialog.setButton(btns);
        dialog.setOnclick(new BottomDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.camera:
                        int permissionCamera = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
                        if (permissionCamera == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        } else {
//                            Toast.makeText(getApplicationContext(), "CAMERA permission authorized", Toast.LENGTH_SHORT).show();
                            openCamera();
                        }
                        break;
                    case R.id.gallery:
                        int permissionStorage = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (permissionStorage == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        } else {
                            openGallery();
                            dialog.dismiss();
                        }
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
            }
        });
        dialog.show(getActivity().getSupportFragmentManager(), "");
    }
    /**
     * 갤러리에서 사진을 가져올 수 있도록 갤러리를 오픈한다.
     *
     * @param
     * @return
    */
    public void openGallery () {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }
    /**
     * 카메라에서 사진 촬영 후 사진을 가져올 수 있도록 카메라를 오픈한다.
     *
     * @param
     * @return
    */
    public void openCamera() {
//        builder.dismiss();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if ( cameraIntent.resolveActivity(getActivity().getPackageManager()) != null ) {
            File photo = saveImageFile(getContext());
            if ( photo != null ) {
                imageFilePath = photo.getAbsolutePath();
                photoUri = FileProvider.getUriForFile(getContext(), getActivity().getPackageName(), photo);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                dialog.dismiss();
            }
        }
    }

    public File saveImageFile (Context context) {
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
    public void setSaveImageFile(Bitmap image) {
        binding.profile.setImageBitmap(image);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            presenter.rotationImage(imageFilePath);
        } else if ( requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK ) {
            presenter.setGalleryImage(getContext(), data.getData());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ( !hidden ) {
            petBasicInfo = getPetBasicInfo();
            if ( petBasicInfo != null ) {
                Picasso picasso = Picasso.with(getContext());
                if ( petBasicInfo.getPetType() == PetRegistActivity.CAT_TYPE ) {
                    picasso.load(SharedPrefVariable.SERVER_ROOT + petBasicInfo.getProfileFilePath())
                            .error(R.drawable.cat)
                            .into(binding.profile);
                } else {
                    picasso.load(SharedPrefVariable.SERVER_ROOT + petBasicInfo.getProfileFilePath())
                            .error(R.drawable.dog)
                            .into(binding.profile);
                }

            }

        }
    }
}
