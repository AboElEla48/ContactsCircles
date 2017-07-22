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
    private static String fieldSeparator = "^^#@$$^^";


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

    public void removeCircleContact(ContactsUtil.ContactModel contact) {
        circleContacts.remove(contact);
    }

    @Override
    public String toString() {
        String circleContactsStr = "";
        for (ContactsUtil.ContactModel contact: circleContacts) {
            circleContactsStr += contact.toString() + fieldSeparator;
        }
        return "" + ID + separator + name + separator + circleContactsStr;
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

            index = str.indexOf(separator);
            circle.name = str.substring(0, index);
            str = str.substring(separator.length() + index);

            index = str.indexOf(fieldSeparator);
            while (index != -1) {
                ContactsUtil.ContactModel contact = new ContactsUtil.ContactModel();
                contact.fromString(str.substring(0, index));
                circle.circleContacts.add(contact);

                str = str.substring(fieldSeparator.length() + index);
                index = str.indexOf(fieldSeparator);
            }


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
