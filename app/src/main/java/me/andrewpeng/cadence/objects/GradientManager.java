package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;

/**
 * Created by zacchaeusleung on 2018-05-02.
 */

public class GradientManager {
    public static ArrayList<Gradient> gradients = new ArrayList<>();
    public static ArrayList<Gradient> activeGradient = new ArrayList<>();

    public static void render(Canvas graphics, Paint paint) {
        ArrayList<Gradient> temp = new ArrayList<>(gradients);
        for(Gradient gradient: temp) {
            if(gradient.isTouched == true) {
                gradient.render(graphics,paint);
                //gradient.render(graphics, paint);
            }
            else {
            }
        }
    }
    public static void touch(MotionEvent e) {
        for(Gradient gradient: gradients) {
            if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1 / 4, Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
            } else if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1 / 2, Renderer.scoreX2 / 2, Renderer.scoreY1, Renderer.scoreY2)) {
            } else if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), 3 * Renderer.scoreX1 / 4, 3 * Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
            } else {
                if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1, Renderer.scoreY2)) {
                }
            }
        }

    }
    public static void tick() {
        ArrayList<Gradient> temp = new ArrayList<>(gradients);
        for(Gradient gradient: temp) {
            gradient.tick();
        }
    }
}
