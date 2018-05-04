package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;


/**
 * Created by isaacleung on 2018-04-26.
 */

public class ParticleManager {
    public static ArrayList<Particle> particles = new ArrayList<>();

    public static void render(Canvas canvas, Paint paint) {
        for(Particle particle: particles) {
            particle.draw(canvas,paint);
            particle.tick();
        }
    }

    public void tick() {
        ArrayList<Particle> temp = new ArrayList<>(particles);
        for(Particle particle: temp) {
            particle.tick();
        }
    }

    public void touch(MotionEvent e){
        try {
            if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1 / 4, Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
            } else if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1 / 2, Renderer.scoreX2 / 2, Renderer.scoreY1, Renderer.scoreY2)) {
            } else if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), 3 * Renderer.scoreX1 / 4, 3 * Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
            } else {
                if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1, Renderer.scoreY2)) {
                }
            }
        }catch (IndexOutOfBoundsException e1) {

        }
    }
    }

