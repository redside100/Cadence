package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.managers.ParticleManager;

/**
 * Creates a particle that will disperse at different speeds
 * Created by isaacleung on 2018-04-26.
 */

public class Particle extends Entity{

    public Bitmap bitmap;
    public int x, y;
    public int ax1, ax2, ay1, ay2;
    public int speed,angle;
    public double directionx;
    public double directiony;

    public int fadeTick = 0;
    public int maxFadeTick = 15;

    public boolean alive = true;

    public boolean isAnimating = false;


    public Particle(Bitmap bitmap, int x, int y, int alpha){
        super(x, y, alpha);
        this.bitmap = bitmap;
        this.x = x - bitmap.getWidth() / 2;
        this.y = y - bitmap.getHeight() / 2;

        ax1 = this.x;
        ay1 = this.y;
        ax2 = ax1 + bitmap.getWidth();
        ay2 = ay1 + bitmap.getHeight();

        this.speed = (int)(Math.random()*4+1);

        angle = (int)(Math.random());
        this.directionx = Math.sin(angle)*speed;
        this.directiony = Math.cos(angle)*speed;

        ParticleManager.particles.add(this);
    }

    public int getBoundX1(){
        return ax1;
    }
    public int getBoundX2(){
        return ax2;
    }
    public int getBoundY1(){
        return ay1;
    }
    public int getBoundY2(){
        return ay2;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public void tick() {
        super.tick();
        if(isAnimating) {
            if (fadeTick < maxFadeTick) {
                super.alpha -= (255 / maxFadeTick);
                fadeTick++;
            } else {
                super.alpha = 0;
                isAnimating = false;
            }
            y += directiony;
            x += directionx;
        }

    }

    public void animate() {
        isAnimating = true;
        alpha = 255;
    }

    public double getDirectionx() {
        return directionx;
    }

    public double getDirectiony() {
        return directiony;
    }

    public boolean isDead() {
        if(getAlpha() <= 0) {
            alive = false;
        }
        return alive;
    }

    public void destroy() {
        ParticleManager.particles.remove(this);
    }

    public void draw(Canvas canvas, Paint paint) {
        if (bitmap != null){
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(alpha);
            canvas.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }
}
