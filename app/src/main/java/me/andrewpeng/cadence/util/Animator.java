package me.andrewpeng.cadence.util;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Animate a set of frames so that an animation can be seen
 * Created by isaacleung on 2018-04-26.
 */

public class Animator {
    ArrayList<Bitmap> frames;
    int currentframe;
    int frameAtPause;
    long prevTime;
    long speed;
    private Bitmap sprite;
    private boolean isRunning = false;

    /**
     * Accepts an arraylist of frames that will be used for one full cycle of animation
     * @param frames Frames of the animation
     */
    public Animator(ArrayList<Bitmap> frames) {
        this.frames = frames;
    }

    /**
     * Getter method for getting the current frame
     * @return current frame
     */
    public int getCurrentframe (){
        return currentframe;
    }

    /**
     * Sets the speed of the animation
     * @param speed Speed of the animation
     */
    public void setSpeed(long speed) {
        this.speed = speed;
    }

    /**
     * Resets all values of the animation to play the animation from the beginning
     */
    public void play() {
        isRunning = true;
        currentframe = 0;
        frameAtPause = 0;
        prevTime = 0;
    }

    /**
     * Resets all the values of the animation and stops the animation
     */
    public void stop() {
        isRunning = false;
        prevTime = 0;
        frameAtPause = 0;
        currentframe = 0;
    }

    /**
     * Temporarily stop the animation at the current frame
     */
    public void pause() {
        isRunning = false;
        frameAtPause = currentframe;
    }

    /**
     * Resume from the last frame that the animation was at based on pause
     */
    public void resume() {
        currentframe = frameAtPause;
        isRunning = true;
    }

    /**
     * Animate the object that is using this class
     * @param time The time that the animation will last
     */
    public void animate(long time) {
        if(isRunning) {
            if (prevTime >= time) {
                try {
                    sprite = frames.get(currentframe);
                } catch (IndexOutOfBoundsException e) {
                    reset();
                    sprite = frames.get(currentframe);
                }
                currentframe++;
                prevTime = time;
            }
        }
    }

    /**
     * Replaces the current animation with a new one
     * @param frames New animation
     */
    public void newAnimation(ArrayList<Bitmap> frames) {
        stop();
        this.frames = frames;
        play();
    }

    /**
     * Resets the animation
     */
    public void reset() {
        currentframe = 0;
    }

    /**
     * Checks if the animation has finished one cycle
     * @return If the current frame is the same as the last frame of the animation
     */
    public boolean isFinished() {
        return frames.size() == currentframe;
    }
}

