package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.managers.GradientManager;

/**
 * A Gradient that appears only if your finger has touched the score area
 * Created by isaacleung on 2018-05-01.
 */


public class Gradient extends Entity{

    public Bitmap bitmap;
    public int speed;


    public boolean fading;
    public int fadeTick = 0;

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
        super(x, y, alpha);
        this.bitmap = bitmap;
        this.speed = speed;
        this.fading = fading;

        super.setMaxAlpha(195);
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
        return super.alpha;
    }

    /**
     *
     * @param graphics drawing
     * @param paint colour
     */
    public void render(Canvas graphics, Paint paint) {
        if (bitmap != null) {
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(super.alpha);
            graphics.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }

    /**
     * Lets the gradient proceed in a fade in/out animation
     */
    public void animate() {
        if (!fading){
            fadeIn(8);
            fading = true;
        }
    }

    /**
     * The gradient fades in/out in
     */
    public void tick() {
        super.tick();
        if (fading){
            fadeTick++;
            if (fadeTick == 8){
                fadeOut(8);
                fadeTick = 0;
                fading = false;
            }
        }
    }
}
