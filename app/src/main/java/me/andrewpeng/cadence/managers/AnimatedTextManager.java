package me.andrewpeng.cadence.managers;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import me.andrewpeng.cadence.objects.AnimatedText;

public class AnimatedTextManager {

    public static ArrayList<AnimatedText> texts = new ArrayList<>();
    public static void render(Canvas graphics, Paint paint){
        ArrayList<AnimatedText> temp = new ArrayList<>(texts);
        for (AnimatedText text : temp){
            text.render(graphics, paint);
        }
    }
    public static void tick(){
        ArrayList<AnimatedText> temp = new ArrayList<>(texts);
        for (AnimatedText text : temp){
            text.tick();
        }
    }
}
