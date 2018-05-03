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
    public static ArrayList<Gradient> activeGradient = new ArrayList<>();

    public static int index;

    public static void render(Canvas graphics, Paint paint) {
        ArrayList<Gradient> temp = new ArrayList<>(gradients);
        for(Gradient gradient: temp) {
            if(gradient.isTouched == true) {
                gradients.get(index).render(graphics,paint);
            }
        }
    }
    public static void touch(MotionEvent e) {
        try {
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
    public static void tick() {
        ArrayList<Gradient> temp = new ArrayList<>(gradients);
        for(Gradient gradient: temp) {
            gradient.tick();
        }
    }
}
