package com.aboelela.circles.data;

import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.data.preferences.PreferencesManager;
import com.mvvm.framework.base.models.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aboelela on 01/07/17.
 * This is the model for circles
 */

public class CirclesModel extends BaseModel
{
    private ArrayList<Circle> circles;

    /**
     * Get the circles list
     * @return
     */
    public List<Circle> getCircles() {
        if(circles == null) {
            // load from preferences
            circles = new ArrayList<>();
            circles = (ArrayList<Circle>) PreferencesManager.loadCirclesList();
        }
        return circles;
    }

    /**
     * Add new circle and generate its ID
     * @param circleName
     */
    public void addCircle(String circleName) {
        if(circles == null) {
            getCircles();
        }

        int ID = 0;
        if(circles.size() > 0) {
            ID = circles.get(circles.size() - 1).getID();
        }

        addCircle(new Circle(++ID, circleName));
    }

    /**
     * save new circle
     * @param circle
     */
    public void addCircle(Circle circle) {
        if(circles == null) {
            getCircles();
        }

        circles.add(circle);

        // save to persistent data store
        PreferencesManager.saveCirclesList(circles);
    }
}
