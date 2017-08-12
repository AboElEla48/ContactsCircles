package com.aboelela.circles.data.runTimeErrors;

/**
 * Created by aboelela on 12/08/17.
 * Exception to throw in case given message type is unknown
 */

public class CircleMessageUnknownException extends RuntimeException
{
    @Override
    public String getMessage() {
        return "Message Type should be one of constants values defined in Constants.MessageFragmentType";
    }
}
