package com.aboelela.circles.data.entities;

import com.aboelela.circles.data.runTimeErrors.UnsupportedStringFormat;
import com.mvvm.framework.utils.LogUtil;

/**
 * Created by aboelela on 01/07/17.
 * Define circle entity
 */

public class  Circle
{
    private static String TAG = "Circle";
    private static String separator = "@##@$$|#";


    int ID;
    String name;

    public Circle() {}

    public Circle(int ID, String name) {
        setID(ID);
        setName(name);
    }

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

    @Override
    public String toString() {
        return "" + ID + separator + name;
    }

    public static Circle fromString(String str) throws UnsupportedStringFormat{
        Circle circle = new Circle();

        int index = str.indexOf(separator);
        if(index == -1) {
            throw new UnsupportedStringFormat();
        }

        try {
            circle.ID = Integer.parseInt(str.substring(0, index));
            str = str.substring(separator.length() + index);
            circle.name = str;

            return circle;
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(TAG, ex);
            throw new UnsupportedStringFormat();
        }

    }
}
