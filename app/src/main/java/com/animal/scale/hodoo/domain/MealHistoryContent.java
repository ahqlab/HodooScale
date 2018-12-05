package com.animal.scale.hodoo.domain;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;

@Data
public class MealHistoryContent implements Serializable  {

    private User user;

    private PetAllInfos petAllInfos;

    private Feed feed;

    private MealHistory mealHistory;

    /*public MealHistoryContent(Parcel in) {
        readFromParcel(in);
    }*/


    /*@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(user);
        parcel.writeSerializable(petAllInfos);
        parcel.writeSerializable(feed);
        parcel.writeSerializable(mealHistory);

    }

    private void readFromParcel(Parcel in){
        user = (User) in.readSerializable();
        petAllInfos = (PetAllInfos) in.readSerializable();
        feed = (Feed) in.readSerializable();
        feed = (Feed) in.readSerializable();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MealHistoryContent createFromParcel(Parcel in) {
            return new MealHistoryContent(in);
        }

        public MealHistoryContent[] newArray(int size) {
            return new MealHistoryContent[size];
        }
    };*/
}
