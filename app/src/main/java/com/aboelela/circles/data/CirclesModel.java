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
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aboelela on 01/07/17.
 * This is the model for circles
 */

public class CirclesModel extends BaseModel
{
    /**
     * Load circles from local storage
     * @return list of loaded circles
     */
    public Observable<List<Circle>> loadCircles() {
        circles = new ArrayList<>();
        circles = (ArrayList<Circle>) PreferencesManager.loadCirclesList();

        // TODO: Load circles contacts images
        return Observable.fromCallable(new Callable<List<Circle>>()
        {
            @Override
            public List<Circle> call() throws Exception {
                return fillInCircleContactsPhotos();
            }
        })
        .subscribeOn(Schedulers.newThread());
//        Observable.fromIterable(circles)
//                .subscribe(new Consumer<Circle>()
//                {
//                    @Override
//                    public void accept(@NonNull Circle circle) throws Exception {
//                        Observable.fromIterable(circle.getCircleContacts())
//                                .subscribe(new Consumer<ContactsUtil.ContactModel>()
//                                {
//                                    @Override
//                                    public void accept(@NonNull ContactsUtil.ContactModel contactModel) throws Exception {
//
//                                    }
//                                });
//                    }
//                });
    }

    /**
     * load images of contacts inside circle
     * @return
     */
    private List<Circle> fillInCircleContactsPhotos() {
        for(int i = 0; i < circles.size(); i++) {
            for(int j = 0; j < circles.get(i).getCircleContacts().size(); j++) {
                ContactsUtil.ContactModel contactModel = circles.get(i).getCircleContacts().get(j);
                contactModel.setBitmap(ContactsUtil.getPhoto(CirclesApplication.getInstance(), contactModel));
            }
        }

        return circles;
    }

    /**
     * Get the circles list
     * @return list of loaded circles
     */
    public List<Circle> getCircles() throws CirclesNotLoadedException{
        if(circles == null) {
            throw new CirclesNotLoadedException();
        }
        return circles;
    }

    /**
     * Add new circle and generate its ID
     * @param circleName circle name
     */
    public void addCircle(String circleName) throws CirclesNotLoadedException{
        if(circles == null) {
            throw new CirclesNotLoadedException();
        }

        int ID = 0;
        if(circles.size() > 0) {
            ID = circles.get(circles.size() - 1).getID();
        }

        addCircle(new Circle(++ID, circleName));
    }

    /**
     * save new circle
     * @param circle circle object
     */
    public void addCircle(Circle circle) throws CirclesNotLoadedException{
        if(circles == null) {
            throw new CirclesNotLoadedException();
        }

        circles.add(circle);

        // save to persistent data store
        PreferencesManager.saveCirclesList(circles);
    }

    private ArrayList<Circle> circles;
}
