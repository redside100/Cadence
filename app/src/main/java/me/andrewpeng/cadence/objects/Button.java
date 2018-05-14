package me.andrewpeng.cadence.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.managers.ButtonManager;

public class Button extends Entity{
    public Bitmap bitmap;
    public int x, y;
    public int ax1, ax2, ay1, ay2;

    /**
     * Creates a button
     * @param bitmap Gets the image of the button
     * @param x x location
     * @param y y location
     * @param alpha Transparency of the button
     */
    public Button(Bitmap bitmap, int x, int y, int alpha){
        super(x, y, alpha);
        this.bitmap = bitmap;
        this.x = x - bitmap.getWidth() / 2;
        this.y = y - bitmap.getHeight() / 2;
        ax1 = this.x;
        ay1 = this.y;
        ax2 = ax1 + bitmap.getWidth();
        ay2 = ay1 + bitmap.getHeight();
        ButtonManager.buttons.add(this);
    }

    /**
     * Renders the button onto the canvas to be seen on the screen
     * @param graphics Canvas
     * @param paint Paint
     */
    public void render(Canvas graphics, Paint paint){
        if (bitmap != null){
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(super.alpha);
            graphics.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }

    //TODO No idea if I have the bounds correct
    /**
     * Getter method for the bitmap
     * @return Bitmap
     */
    public Bitmap getBitmap(){
        return bitmap;
    }

    /**
     * Get the corner of the button
     * @return Right bottom corner
     */
    public int getBoundX1(){
        return ax1;
    }

    /**
     * Get the corner of the button
     * @return Left bottom corner
     */
    public int getBoundX2(){
        return ax2;
    }

    /**
     * Get the corner of the button
     * @return Top right corner
     */
    public int getBoundY1(){
        return ay1;
    }

    /**
     * Get the corner of the bottom
     * @return Top left corner
     */
    public int getBoundY2(){
        return ay2;
    }
    public void tick(){}

    // To be overridden
    public void trigger(){}
}
