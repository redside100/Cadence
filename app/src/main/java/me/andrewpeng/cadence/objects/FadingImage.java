package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.managers.AnimatedTextManager;
import me.andrewpeng.cadence.managers.FadingImageManager;

public class FadingImage extends Entity{
    public Bitmap bitmap;
    public FadingImage(Bitmap bitmap, int x, int y, int startingAlpha){
        super(x, y, startingAlpha);
        this.bitmap = bitmap;
        FadingImageManager.fadingImages.add(this);
    }

    public void render(Canvas graphics, Paint paint){
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(super.alpha);
        Renderer.centerBitmap(bitmap, graphics, x, y, paint);
        paint.setAlpha(oldAlpha);
    }
}
