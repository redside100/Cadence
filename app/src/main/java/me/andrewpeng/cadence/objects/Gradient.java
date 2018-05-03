package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by isaacleung on 2018-05-01.
 */

public class Gradient {

    public Bitmap bitmap;
    public int x, y;
    public int speed;
    public int alpha;

    public boolean isTouched = false;
    public boolean fading;

    public int fadeTick = 0;
    public int maxFadeTick = 25;

    public Gradient(Bitmap bitmap, int x, int y, int speed, int alpha, boolean fading) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alpha = alpha;
        this.fading = fading;
        GradientManager.gradients.add(this);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAlpha() {
        return alpha;
    }

    public void render(Canvas graphics, Paint paint) {
        if (bitmap != null) {
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(alpha);
            graphics.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }

    public void animate() {
        isTouched = true;
        fading = false;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void tick() {
        if (isTouched) {
            fadeTick++;
            if(fadeTick < maxFadeTick) {
                if (!fading) {
                    alpha += speed;
                    if (getAlpha() >= 255 && fadeTick == 12) {
                        fading = true;
                    }
                } else {
                    alpha -= speed;
                }
            }
            else {
                alpha = 0;
                fadeTick = 0;
                maxFadeTick = 25;
                isTouched = false;
            }

        }
    }
}
