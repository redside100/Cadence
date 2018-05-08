package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.music.Conductor;

/**
 * Created by isaacleung on 2018-05-02.
 */

public class GradientManager {
    public static ArrayList<Gradient> gradients = new ArrayList<>();

    public static int index;

    /**
     *
     * @param graphics drawing
     * @param paint colour
     */
    public static void render(Canvas graphics, Paint paint) {
        //Loops through the list of gradients (4) to check whenever a gradient should be displayed
        //Only shows the gradient that the index is set to
        ArrayList<Gradient> temp = new ArrayList<>(gradients);
        for(Gradient gradient: temp) {
            if(gradient.isTouched == true) {
                gradients.get(index).render(graphics,paint);
            }
        }
    }

    /**
     *
     * @param e catches the motion of the finger
     */
    public static void touch(MotionEvent e) {

        //Error Check to make sure that this does not go through when the array is empty
        try {

            //Checks which lane the touch happened, and sets the gradient to be shown relative to the lane touched
            if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1 / 4, Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
                index = 0;
                gradients.get(index).animate();
            } else if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1 / 2, Renderer.scoreX2 / 2, Renderer.scoreY1, Renderer.scoreY2)) {
                index = 1;
                gradients.get(index).animate();
            } else if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), 3 * Renderer.scoreX1 / 4, 3 * Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
                index = 2;
                gradients.get(index).animate();
            } else {
                if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1, Renderer.scoreY2)) {
                    index = 3;
                    gradients.get(index).animate();
                }
            }
        }catch (IndexOutOfBoundsException e1) {

        }

    }

    /**
     * Ticks the gradient and completes the animation if possible
     */
    public static void tick() {
        ArrayList<Gradient> temp = new ArrayList<>(gradients);
        for(Gradient gradient: temp) {
            gradient.tick();
        }
    }
}
