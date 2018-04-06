package com.aboelela.circles.model.data;

/**
 * Created by aboelela on 06/04/18.
 * POJO class for circle
 */

public class Circle
{
    private int id;
    private String name;
    private int color;
    private int icon;

    public Circle() {}

    public Circle(int id, String name, int color, int icon) {
        setId(id);
        setName(name);
        setColor(color);
        setIcon(icon);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
