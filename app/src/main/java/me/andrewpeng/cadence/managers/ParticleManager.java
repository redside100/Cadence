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

    }

