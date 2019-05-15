package com.animal.scale.hodoo.activity.pet.regist.fragment.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by SongSeokwoo on 2019-05-14.
 */
public interface PetProfileIn {
    interface View {
        void setSaveImageFile(Bitmap image);
    }
    interface Presenter {
        void rotationImage ( String imageFilePath );
        void setGalleryImage(Context context, Uri img);
    }
}
