package com.aboelela.circles.data.runTimeErrors;

/**
 * Created by aboelela on 28/07/17.
 * Exception to tell that you didn't load circles before using it
 */

public class CirclesNotLoadedException extends RuntimeException
{
    @Override
    public String getMessage() {
        return "You didn't call loadCircles() before getting circles";
    }
}
