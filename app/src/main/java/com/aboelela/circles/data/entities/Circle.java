package com.aboelela.circles.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.aboelela.circles.data.runTimeErrors.UnsupportedStringFormatException;
import com.mvvm.framework.utils.ContactsUtil;
import com.mvvm.framework.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aboelela on 01/07/17.
 * Define circle entity
 */

public class Circle implements Parcelable
{
    private static String TAG = "Circle";
    private static String separator = "@##@$$|#";


    private int ID = 0;
    private String name = "";
    private ArrayList<ContactsUtil.ContactModel> circleContacts = new ArrayList<>();

    public Circle() {}

    public Circle(int ID, String name) {
        setID(ID);
        setName(name);
    }

    public Circle(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        circleContacts = in.readArrayList(ContactsUtil.ContactModel.class.getClassLoader());
    }

    public static final Creator<Circle> CREATOR = new Creator<Circle>()
    {
        @Override
        public Circle createFromParcel(Parcel in) {
            return new Circle(in);
        }

        @Override
        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ContactsUtil.ContactModel> getCircleContacts() {
        return circleContacts;
    }

    public void addCircleContact(ContactsUtil.ContactModel contact) {
        circleContacts.add(contact);
    }

    @Override
    public String toString() {
        return "" + ID + separator + name;
    }

    public static Circle fromString(String str) throws UnsupportedStringFormatException {
        Circle circle = new Circle();

        int index = str.indexOf(separator);
        if(index == -1) {
            throw new UnsupportedStringFormatException();
        }

        try {
            circle.ID = Integer.parseInt(str.substring(0, index));
            str = str.substring(separator.length() + index);
            circle.name = str;

            return circle;
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, ex);
            throw new UnsupportedStringFormatException();
        }

    }

    @Override
    public int describeContents() {
        return getID();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getID());
        parcel.writeString(getName());
        parcel.writeTypedList(circleContacts);
    }



}
