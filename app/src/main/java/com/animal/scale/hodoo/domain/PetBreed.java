package com.animal.scale.hodoo.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

@Data
public class PetBreed  implements Serializable, Parcelable {
    private int id;
    private String name;

    public PetBreed(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<PetBreed> CREATOR = new Creator<PetBreed>() {
        @Override
        public PetBreed createFromParcel(Parcel in) {
            return new PetBreed(in);
        }

        @Override
        public PetBreed[] newArray(int size) {
            return new PetBreed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
