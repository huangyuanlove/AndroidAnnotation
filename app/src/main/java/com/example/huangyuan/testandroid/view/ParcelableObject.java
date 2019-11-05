package com.example.huangyuan.testandroid.view;

import android.os.Parcel;
import android.os.Parcelable;


public class ParcelableObject implements Parcelable {

    String name;
    int id;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    public ParcelableObject() {
    }

    protected ParcelableObject(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<ParcelableObject> CREATOR = new Creator<ParcelableObject>() {
        @Override
        public ParcelableObject createFromParcel(Parcel source) {
            return new ParcelableObject(source);
        }

        @Override
        public ParcelableObject[] newArray(int size) {
            return new ParcelableObject[size];
        }
    };
}
