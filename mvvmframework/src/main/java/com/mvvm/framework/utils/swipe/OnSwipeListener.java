package com.mvvm.framework.utils.swipe;

/**
 * Created by aboelela on 05/08/17.
 * Swipe events to be implemented by swipe listener
 */

public interface OnSwipeListener
{
    /**
     * Action down
     * @param actionX : the x position of action
     * @param actionY : the y position of action
     */
    void onActionDown(float actionX, float actionY);

    /**
     * Action up
     * @param actionX : the x position of action
     * @param actionY : the y position of action
     */
    void onActionUp(float actionX, float actionY);

    /**
     * Swipe event started
     *
     * @param initialX : the initial x position of view before swipe
     * @param initialY : the initial y position of view before swipe
     */
    void onSwipeStarted(float initialX, float initialY);

    /**
     * Swipe down event
     *
     * @param initialX : the initial x position of view before swipe
     * @param initialY : the initial y position of view before swipe
     * @param currentX : the current x position of view in swipe
     * @param currentY : the current y position of view in swipe
     * @param delta    : the delta value of swipe in y direction
     */
    void onSwipeDown(float initialX, float initialY, float currentX, float currentY, float delta);

    /**
     * Swipe up event
     *
     * @param initialX : the initial x position of view before swipe
     * @param initialY : the initial y position of view before swipe
     * @param currentX : the current x position of view in swipe
     * @param currentY : the current y position of view in swipe
     * @param delta    : the delta value of swipe in y direction
     */
    void onSwipeUp(float initialX, float initialY, float currentX, float currentY, float delta);

    /**
     * Swipe left event
     *
     * @param initialX : the initial x position of view before swipe
     * @param initialY : the initial y position of view before swipe
     * @param currentX : the current x position of view in swipe
     * @param currentY : the current y position of view in swipe
     * @param delta    : the delta value of swipe in x direction
     */
    void onSwipeLeft(float initialX, float initialY, float currentX, float currentY, float delta);

    /**
     * Swipe right event
     *
     * @param initialX : the initial x position of view before swipe
     * @param initialY : the initial y position of view before swipe
     * @param currentX : the current x position of view in swipe
     * @param currentY : the current y position of view in swipe
     * @param delta    : the delta value of swipe in x direction
     */
    void onSwipeRight(float initialX, float initialY, float currentX, float currentY, float delta);

    /**
     * Swipe event finished
     *
     * @param initialX : the initial x position of view before swipe
     * @param initialY : the initial y position of view before swipe
     */
    void onSwipeFinished(float initialX, float initialY);
}
