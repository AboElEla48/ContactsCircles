package com.aboelela.circles.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.aboelela.circles.constants.Constants;
import com.mvvm.framework.base.models.BaseModel;

/**
 * Created by aboelela on 12/08/17.
 * This model for message shown in home
 */

public class HomeMessageModel extends BaseModel implements Parcelable
{
    private int messageType = Constants.MessageFragmentType.Message_Type_Info;
    private String messageStr = "";

    HomeMessageModel(Parcel in) {
        setMessageType(in.readInt());
        setMessageStr(in.readString());
    }

    public HomeMessageModel(int type, String msg) {
        setMessageType(type);
        setMessageStr(msg);
    }

    public static final Creator<HomeMessageModel> CREATOR = new Creator<HomeMessageModel>()
    {
        @Override
        public HomeMessageModel createFromParcel(Parcel in) {
            return new HomeMessageModel(in);
        }

        @Override
        public HomeMessageModel[] newArray(int size) {
            return new HomeMessageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(messageType);
        parcel.writeString(messageStr);
    }

    public int getMessageType() {
        return messageType;
    }

    void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    void setMessageStr(String messageStr) {
        this.messageStr = messageStr;
    }

    public String getMessageStr() {
        return messageStr;
    }
}
