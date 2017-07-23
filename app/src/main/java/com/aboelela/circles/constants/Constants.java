package com.aboelela.circles.constants;

/**
 * Created by aboelela on 29/06/17.
 * <p>
 * Hold all application constants
 */

public final class Constants
{
    private Constants() {
    }

    public static class App
    {
        static int Result_SUCCESS = 0;
        static int Result_Error = -1;

        public static int ViewMode_Grid = 1;
        public static int ViewMode_List = 2;
    }

    public static class Splash
    {
        public static int SPLASH_DURATION = 1 * 1000;
    }
}
