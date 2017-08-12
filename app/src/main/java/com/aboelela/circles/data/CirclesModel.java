package com.aboelela.circles.data;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.data.entities.Circle;
import com.aboelela.circles.data.preferences.PreferencesManager;
import com.aboelela.circles.data.runTimeErrors.CirclesNotLoadedException;
import com.mvvm.framework.base.models.BaseModel;
import com.mvvm.framework.utils.ContactsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aboelela on 01/07/17.
 * This is the model for circles
 */

public class CirclesModel extends BaseModel
{
    /**
     * Load circles from local storage
     *
     * @return list of loaded circles
     */
    public Observable<List<Circle>> loadCircles() {
        circles = new ArrayList<>();
        circles = (ArrayList<Circle>) PreferencesManager.loadCirclesList();

        // TODO: Load circles contacts images
        return Observable.fromCallable(
                new Callable<List<Circle>>()
                {
                    @Override
                    public List<Circle> call() throws Exception {
                        return fillInCircleContactsPhotos();
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * load images of contacts inside circle
     *
     * @return : fill photos in contacts
     */
    private List<Circle> fillInCircleContactsPhotos() {
        for (int i = 0; i < circles.size(); i++) {
            for (int j = 0; j < circles.get(i).getCircleContacts().size(); j++) {
                ContactsUtil.ContactModel contactModel = circles.get(i).getCircleContacts().get(j);
                contactModel.setBitmap(ContactsUtil.getPhoto(CirclesApplication.getInstance(), contactModel));
            }
        }

        return circles;
    }

    /**
     * Get the circles list
     *
     * @return list of loaded circles
     */
    public List<Circle> getCircles() throws CirclesNotLoadedException {
        if (circles == null) {
            throw new CirclesNotLoadedException();
        }
        return circles;
    }

    /**
     * Add new circle and generate its ID
     *
     * @param circleName circle name
     */
    public void addCircle(String circleName) throws CirclesNotLoadedException {
        if (circles == null) {
            throw new CirclesNotLoadedException();
        }

        int ID = 0;
        if (circles.size() > 0) {
            ID = circles.get(circles.size() - 1).getID();
        }

        addCircle(new Circle(++ID, circleName));
    }

    /**
     * save new circle
     *
     * @param circle circle object
     */
    public void addCircle(Circle circle) throws CirclesNotLoadedException {
        if (circles == null) {
            throw new CirclesNotLoadedException();
        }

        circles.add(circle);

        // save to persistent data store
        PreferencesManager.saveCirclesList(circles);
    }

    /**
     * Edit circle name
     *
     * @param circleID :  the ID of circle to edit
     * @param newName     : the new name of the circle
     * @throws CirclesNotLoadedException       : throw exception in case circles were not loaded
     */
    public void editCircleName(int circleID, final String newName) throws CirclesNotLoadedException {
        if (circles == null) {
            throw new CirclesNotLoadedException();
        }

        searchForCircle(circleID, new Consumer<Circle>()
        {
            @Override
            public void accept(@NonNull Circle circle) throws Exception {
                circle.setName(newName);

                // save to persistent data store
                PreferencesManager.saveCirclesList(circles);
            }
        });

    }

    /**
     * Delete circle
     *
     * @param circleID : the id of the circle
     * @throws CirclesNotLoadedException : throw exception in case circles were not loaded
     */
    public void removeCircle(final int circleID) throws CirclesNotLoadedException {
        if (circles == null) {
            throw new CirclesNotLoadedException();
        }

        // delete circle
        searchForCircle(circleID, new Consumer<Circle>()
        {
            @Override
            public void accept(@NonNull Circle circle) throws Exception {
                circles.remove(circle);

                // save to persistent data store
                PreferencesManager.saveCirclesList(circles);
            }
        });

    }

    /**
     * search for circle
     * @param circleID : The ID of the circle
     * @param circleConsumer : the receiver for circle if found
     */
    public void searchForCircle(final int circleID, Consumer<Circle> circleConsumer) {
        Observable.fromIterable(circles)
                .filter(new Predicate<Circle>()
                {
                    @Override
                    public boolean test(@NonNull Circle circle) throws Exception {
                        return circle.getID() == circleID;
                    }
                })
                .subscribe(circleConsumer);
    }

    private ArrayList<Circle> circles;
}
