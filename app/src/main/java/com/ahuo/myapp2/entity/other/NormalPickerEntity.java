package com.ahuo.myapp2.entity.other;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created on 2016-12-13.
 *
 * @author LiuHuiJie
 */
public class NormalPickerEntity implements Parcelable {

    public String mTitle;

    public List<String> mChooseList;

    public String mSelectItem;

    public String mConfirmItem;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeStringList(this.mChooseList);
        dest.writeString(this.mSelectItem);
        dest.writeString(this.mConfirmItem);
    }

    public NormalPickerEntity(String title) {
        this.mTitle=title;
    }

    protected NormalPickerEntity(Parcel in) {
        this.mTitle = in.readString();
        this.mChooseList = in.createStringArrayList();
        this.mSelectItem = in.readString();
        this.mConfirmItem = in.readString();
    }

    public static final Creator<NormalPickerEntity> CREATOR = new Creator<NormalPickerEntity>() {
        @Override
        public NormalPickerEntity createFromParcel(Parcel source) {
            return new NormalPickerEntity(source);
        }

        @Override
        public NormalPickerEntity[] newArray(int size) {
            return new NormalPickerEntity[size];
        }
    };
}
