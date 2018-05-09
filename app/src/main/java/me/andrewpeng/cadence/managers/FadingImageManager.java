package me.andrewpeng.cadence.managers;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import me.andrewpeng.cadence.objects.AnimatedText;
import me.andrewpeng.cadence.objects.FadingImage;

public class FadingImageManager {

    public static ArrayList<FadingImage> fadingImages = new ArrayList<>();
    public static void render(Canvas graphics, Paint paint){
        ArrayList<FadingImage> temp = new ArrayList<>(fadingImages);
        for (FadingImage image : temp){
            image.render(graphics, paint);
        }
    }
    public static void tick(){
        ArrayList<FadingImage> temp = new ArrayList<>(fadingImages);
        for (FadingImage image : temp){
            image.tick();
        }
    }
}
