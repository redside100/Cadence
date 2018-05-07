package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by isaacleung on 2018-05-07.
 */

public class ScoreMessage extends Entity{
    public Bitmap bitmap;
    public int x,y;
    public int alpha;

    public boolean isTouched = false;

    public ScoreMessage(Bitmap bitmap, int x, int y, int alpha) {
        super(x,y,alpha);
        this.bitmap = bitmap;
        ScoreMessageManager.scoreMessages.add(this);
    }

    public void tick() {
        if(isTouched) {
            fadeIn(9);
            fadeOut(9);
            isTouched = false;
        }
    }

    public void animate() {
        isTouched = true;
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

    public void render(Canvas graphics, Paint paint) {
        if (bitmap != null) {
            int oldAlpha = paint.getAlpha();
            paint.setAlpha(alpha);
            graphics.drawBitmap(bitmap, x, y, paint);
            paint.setAlpha(oldAlpha);
        }
    }
}
