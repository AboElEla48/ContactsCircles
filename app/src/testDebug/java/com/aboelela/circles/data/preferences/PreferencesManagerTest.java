package com.aboelela.circles.data.preferences;

import com.aboelela.circles.CirclesApplication;
import com.aboelela.circles.data.entities.Circle;
import com.mvvm.framework.utils.PreferencesUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aboelela on 01/07/17.
 * Test class for preferences
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PreferencesUtil.class, CirclesApplication.class})
public class PreferencesManagerTest
{
    @Test
    public void loadCirclesList_ReturnsCircles() throws Exception {
        ArrayList<String> circlesStr = new ArrayList<>();
        circlesStr.add("10@##@$$|#C1");
        circlesStr.add("11@##@$$|#C2");
        circlesStr.add("14@##@$$|#C-15");

        CirclesApplication circlesApplication = PowerMockito.mock(CirclesApplication.class);
        PowerMockito.mockStatic(PreferencesUtil.class);
        PowerMockito.mockStatic(CirclesApplication.class);

        BDDMockito.given(PreferencesUtil.getSet(CirclesApplication.getInstance(),
                PreferencesManager.Prefs.PREF_CIRCLES_KEY))
                .willReturn(circlesStr);

        List<Circle> circles = PreferencesManager.loadCirclesList();
        Assert.assertTrue(circles.size() == circlesStr.size());

    }
}