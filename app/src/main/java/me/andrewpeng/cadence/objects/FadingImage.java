package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.managers.AnimatedTextManager;
import me.andrewpeng.cadence.managers.FadingImageManager;

/**
 * An image that fades in an out.
 */
public class FadingImage extends Entity{
    public Bitmap bitmap;
    public boolean fadingIn = false;
    private int counter = 0;
    private int duration;
    private int fadeTicks;
    // Constructor for automated fading image (in and out)
    public FadingImage(Bitmap bitmap, int x, int y, int startingAlpha, int duration, int fadeTicks){
        super(x, y, startingAlpha);
        this.bitmap = bitmap;
        this.duration = duration;
        this.fadeTicks = fadeTicks;
        FadingImageManager.fadingImages.add(this);
    }
    // Constructor for manually triggered fading image
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
        // If it's fading in, increase the counter until it hits its duration
        if (fadingIn){
            if (super.alpha == super.maxAlpha){
                counter++;
                if (counter == duration){
                    // Start fade out process, set flag and reset counter
                    super.fadeOut(fadeTicks);
                    counter = 0;
                    fadingIn = false;
                }
            }
        }else{
            // If it's fading out, increase counter until it reaches fade ticks
            counter++;
            if (counter == fadeTicks){
                // Remove finally
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
