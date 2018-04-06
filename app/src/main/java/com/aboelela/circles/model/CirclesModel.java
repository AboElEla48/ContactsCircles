package com.aboelela.circles.model;

import com.aboelela.circles.model.data.Circle;
import com.foureg.baseframework.model.BaseDataModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created by aboelela on 06/04/18.
 * Data model for retrieving/saving circles
 */

public class CirclesModel extends BaseDataModel
{
    public List<Circle> loadCircles() {
        return Arrays.asList(new Circle(1, "Family", 0, 0),
                new Circle(2, "Friends", 0, 0),
                new Circle(3, "Work", 0, 0),
                new Circle(4, "Enemies", 0, 0));
    }
}
