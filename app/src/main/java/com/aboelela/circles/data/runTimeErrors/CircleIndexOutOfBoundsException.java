package com.aboelela.circles.data.runTimeErrors;

/**
 * Created by aboelela on 12/08/17.
 * Exception to throw in case given circle index is out of circles list bounds
 */

public class CircleIndexOutOfBoundsException extends RuntimeException
{
    @Override
    public String getMessage() {
        return "Circle Index out of bounds";
    }
}
