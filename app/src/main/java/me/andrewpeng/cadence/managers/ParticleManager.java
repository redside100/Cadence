package me.andrewpeng.cadence.managers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.objects.Particle;


/**
 * Disperses a set amonunt of particles stored in an array list
 * Created by isaacleung on 2018-04-26.
 */

public class ParticleManager {
    public static ArrayList<Particle> particles = new ArrayList<>();

    public static void render(Canvas canvas, Paint paint) {
        for(Particle particle: particles) {
            particle.draw(canvas,paint);
        }
    }

    public static void tick() {
        ArrayList<Particle> temp = new ArrayList<>(particles);
        for(Particle particle: temp) {
            particle.tick();
        }
    }

    public static void touch(MotionEvent e, int pointerIndex){
        try {
            if(MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex), Renderer.scoreX1 / 4, Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
                for(Particle particle: particles) {
                    System.out.println(e.getX(pointerIndex) + ":" + e.getY(pointerIndex));
                    particle.setPosition((int)e.getX(),(int)e.getY());
                    particle.animate();
                }
            }
            else if(MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex), Renderer.scoreX1 / 2, Renderer.scoreX2 / 2, Renderer.scoreY1, Renderer.scoreY2)) {
                for(Particle particle: particles) {
                    particle.setPosition((int)e.getX(pointerIndex),(int)e.getY(pointerIndex));
                    particle.animate();
                }
            }
            else if(MainView.inBounds((int) e.getX(pointerIndex), (int) e.getX(pointerIndex), (int) e.getY(pointerIndex), (int) e.getY(pointerIndex), 3 * Renderer.scoreX1 / 4, 3 * Renderer.scoreX2 / 4, Renderer.scoreY1, Renderer.scoreY2)) {
                for(Particle particle:particles) {
                    particle.setPosition((int)e.getX(pointerIndex),(int)e.getY(pointerIndex));
                    particle.animate();
                }
            }
            else if(MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(), Renderer.scoreX1, Renderer.scoreX2, Renderer.scoreY1, Renderer.scoreY2)){
                for(Particle particle: particles) {
                    particle.setPosition((int)e.getX(pointerIndex),(int)e.getY(pointerIndex));
                    particle.animate();
                }
            }
        }catch (IndexOutOfBoundsException e1) {

        }
    }
    }

