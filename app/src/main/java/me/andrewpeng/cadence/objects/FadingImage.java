package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.managers.AnimatedTextManager;
import me.andrewpeng.cadence.managers.FadingImageManager;

public class FadingImage extends Entity{
    public Bitmap bitmap;
    public boolean fadingIn = false;
    private int counter = 0;
    private int duration;
    private int fadeTicks;
    public FadingImage(Bitmap bitmap, int x, int y, int startingAlpha, int duration, int fadeTicks){
        super(x, y, startingAlpha);
        this.bitmap = bitmap;
        this.duration = duration;
        this.fadeTicks = fadeTicks;
        FadingImageManager.fadingImages.add(this);
    }
    public FadingImage(Bitmap bitmap, int x, int y, int startingAlpha){
        super(x, y, startingAlpha);
        this.bitmap = bitmap;
        this.duration = 0;
        this.fadeTicks = 0;
        FadingImageManager.fadingImages.add(this);
    }

    public void render(Canvas graphics, Paint paint){
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(super.alpha);
        Renderer.centerBitmap(bitmap, graphics, x, y, paint);
        paint.setAlpha(oldAlpha);
    }
    @Override
    public void tick(){
        super.tick();
        if (fadingIn){
            if (super.alpha == super.maxAlpha){
                counter++;
                if (counter == duration){
                    super.fadeOut(fadeTicks);
                    counter = 0;
                    fadingIn = false;
                }
            }
        }else{
            counter++;
            if (counter == fadeTicks){
                FadingImageManager.fadingImages.remove(this);
            }
        }

    }
    public void destroy(){
        FadingImageManager.fadingImages.remove(this);
    }
    public void automate(){
        fadingIn = true;
        super.fadeIn(fadeTicks);
    }
}
