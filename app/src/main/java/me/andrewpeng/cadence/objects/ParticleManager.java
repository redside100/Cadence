package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;


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

    }

    public void touch(){
        for(Particle particle: particles) {
            particle.tick();
        }

        }
    }

