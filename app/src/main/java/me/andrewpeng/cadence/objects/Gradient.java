package me.andrewpeng.cadence.objects;

import android.view.MotionEvent;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.music.Conductor;

/**
 * Created by isaacleung on 2018-05-01.
 */

public class Gradient {
    public boolean isTouched = false;
    public static Conductor conductor;
    public void touch(MotionEvent e, int pointerIndex) {
        if(conductor.playing) {
            isTouched = false;
            if (MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1, Renderer.scoreY2)) {
                isTouched = true;
            } else {
                isTouched = false;
            }
        }
    }
    public void tick() {
    }
}
