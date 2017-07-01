package com.aboelela.circles.data.preferences;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.data.entities.Circle;
import com.mvvm.framework.utils.PreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by aboelela on 29/06/17.
 * This is the manager for getting/setting all data in preferences
 */

public class PreferencesManager
{
    private static class Prefs {

        // Key for accessing circles
        final static String PREF_CIRCLES_KEY = "PREF_CIRCLES_KEY";
    }

    /**
     * Load list of saved circles from preferences
     * @return
     */
    public static List<Circle> loadCirclesList() {
        final ArrayList<Circle> circles = new ArrayList<>();

        // load set from preferences
        List<String> circlesStrList = PreferencesUtil.getSet(CirclesApplication.getInstance(),
                Prefs.PREF_CIRCLES_KEY);
        Observable.fromIterable(circlesStrList)
                .map(new Function<String, Circle>()
                {
                    @Override
                    public Circle apply(@NonNull String s) throws Exception {
                        return Circle.fromString(s);
                    }
                })
                .blockingSubscribe(new Consumer<Circle>()
                {
                    @Override
                    public void accept(@NonNull Circle circle) throws Exception {
                        circles.add(circle);
                    }
                });

        return circles;
    }

    /**
     * Save circles list in preferences
     * @param circlesList
     */
    public static void saveCirclesList(List<Circle> circlesList) {
        final ArrayList<String> circlesStrList = new ArrayList<>();

        Observable.fromIterable(circlesList)
                .map(new Function<Circle, String>()
                {
                    @Override
                    public String apply(@NonNull Circle circle) throws Exception {
                        return circle.toString();
                    }
                })
                .blockingSubscribe(new Consumer<String>()
                {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        circlesStrList.add(s);
                    }
                });

        PreferencesUtil.saveSet(CirclesApplication.getInstance(), Prefs.PREF_CIRCLES_KEY,
                circlesStrList);
    }

}
