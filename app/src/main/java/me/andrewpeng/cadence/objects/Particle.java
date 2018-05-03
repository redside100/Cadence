package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by isaacleung on 2018-04-26.
 */

public class Particle extends Entity{

    public Bitmap bitmap;
    public int x, y;
    public int ax1, ax2, ay1, ay2;
    public int speed = 1;
    public int UP = 1;
    public int DOWN = 2;
    public int RIGHT = 3;
    public int LEFT = 4;
    public int direction;

    public int fadeTick = 0;
    public int maxFadeTick = 15;

    public boolean alive = true;

    public Particle(Bitmap bitmap, int x, int y, int alpha, int choice){
        super(x, y, alpha);
        this.bitmap = bitmap;
        this.x = x - bitmap.getWidth() / 2;
        this.y = y - bitmap.getHeight() / 2;
        ax1 = this.x;
        ay1 = this.y;
        ax2 = ax1 + bitmap.getWidth();
        ay2 = ay1 + bitmap.getHeight();
        setDirection(choice);
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
        if(fadeTick < maxFadeTick) {
            alpha -= (255/maxFadeTick);
            fadeTick++;
        }
        else {
            destroy();
        }
        switch (getDirection()) {
            case 1:
               y += speed;
               break;
            case 2:
                y-=speed;
                break;
            case 3:
                x += speed;
                break;
            case 4:
                x -= speed;
                break;
        }

    }

    public void trigger() {}

    public void setDirection(int choice) {
        switch (choice) {
            case 1:
                direction = UP;
                break;
            case 2:
                direction = DOWN;
                break;
            case 3:
                direction = RIGHT;
                break;
            case 4:
                direction = LEFT;
                break;
        }
    }

    public int getDirection() {
        return direction;
    }

    public boolean isDead() {
        return !alive;
    }

    public void destroy() {
        ParticleManager.particles.remove(this);
    }

    public void draw(Canvas canvas, Paint paint) {
        if (bitmap != null){
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(super.alpha);
            canvas.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }
}
