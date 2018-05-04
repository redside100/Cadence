package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * A Gradient that appears only if your finger has touched the score area
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
    public int maxFadeTick = 30;

    /**
     *
     * @param bitmap image of the gradient
     * @param x x position of the gradient
     * @param y y position of the gradient
     * @param speed speed the gradient fades in/out
     * @param alpha transparency of the gradient
     * @param fading determines if the gradient is fading
     */
    public Gradient(Bitmap bitmap, int x, int y, int speed, int alpha, boolean fading) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.alpha = alpha;
        this.fading = fading;
        GradientManager.gradients.add(this);
    }

    /**
     *
     * @param x x position of the gradient
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @param y y position of the gradient
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @return x position of the gradient
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y position of the gradient
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return speed of the fading of the gradient
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @return the transparency of the gradient
     */
    public int getAlpha() {
        return alpha;
    }

    /**
     *
     * @param graphics drawing
     * @param paint colour
     */
    public void render(Canvas graphics, Paint paint) {
        if (bitmap != null) {
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(alpha);
            graphics.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }

    /**
     * Lets the gradient proceed in a fade in/out animation
     */
    public void animate() {
        isTouched = true;
        fading = false;
    }

    /**
     * The gradient fades in/out in
     */
    public void tick() {
        if (isTouched) {
            fadeTick+=2;
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
                maxFadeTick = 30;
                isTouched = false;
            }

        }
    }
}
