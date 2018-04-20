package me.andrewpeng.cadence.objects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Button extends Entity{
    public Bitmap bitmap;
    public int x, y;
    public Button(Bitmap bitmap, int x, int y, int alpha){
        super(x, y, alpha);
        this.bitmap = bitmap;
        this.x = x - bitmap.getWidth() / 2;
        this.y = y - bitmap.getHeight() / 2;
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
    public void tick(){}

    // To be overridden
    public void trigger(){}
}
