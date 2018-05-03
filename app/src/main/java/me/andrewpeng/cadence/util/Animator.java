package me.andrewpeng.cadence.util;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
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

    public Animator(ArrayList<Bitmap> frames) {
        this.frames = frames;
    }

    public int getCurrentframe (){
        return currentframe;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public void play() {
        isRunning = true;
        currentframe = 0;
        frameAtPause = 0;
        prevTime = 0;
    }

    public void stop() {
        isRunning = false;
        prevTime = 0;
        frameAtPause = 0;
        currentframe = 0;
    }

    public void pause() {
        isRunning = false;
        frameAtPause = currentframe;
    }

    public void resume() {
        currentframe = frameAtPause;
        isRunning = true;
    }

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

    public void newAnimation(ArrayList<Bitmap> frames) {
        stop();
        this.frames = frames;
        play();
    }

    public void reset() {
        currentframe = 0;
    }

    public boolean isFinished() {
        return frames.size() == currentframe;
    }
}

