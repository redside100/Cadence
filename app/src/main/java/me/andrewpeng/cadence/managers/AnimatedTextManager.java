package me.andrewpeng.cadence.managers;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import me.andrewpeng.cadence.objects.AnimatedText;

public class AnimatedTextManager {

    public static ArrayList<AnimatedText> texts = new ArrayList<>();

    /**
     * Render the text through canvas to be able to see on the screen
     * @param graphics Canvas
     * @param paint Paint
     */
    public static void render(Canvas graphics, Paint paint){
        ArrayList<AnimatedText> temp = new ArrayList<>(texts);
        for (AnimatedText text : temp){
            text.render(graphics, paint);
        }
    }

    /**
     * Animate the text based on the tick function in AnimatedText
     */
    public static void tick(){
        ArrayList<AnimatedText> temp = new ArrayList<>(texts);
        for (AnimatedText text : temp){
            text.tick();
        }
    }
}
