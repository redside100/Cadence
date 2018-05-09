package me.andrewpeng.cadence.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.managers.ButtonManager;

public class Button extends Entity{
    public Bitmap bitmap;
    public int x, y;
    public int ax1, ax2, ay1, ay2;
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
    public void render(Canvas graphics, Paint paint){
        if (bitmap != null){
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(super.alpha);
            graphics.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }
    public Bitmap getBitmap(){
        return bitmap;
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
    public void tick(){}

    // To be overridden
    public void trigger(){}
}
