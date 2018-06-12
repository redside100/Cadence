package me.andrewpeng.cadence.managers;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

import me.andrewpeng.cadence.objects.FadingImage;
import me.andrewpeng.cadence.objects.Pulse;

public class PulseManager {

    public static ArrayList<Pulse> pulses = new ArrayList<>();
    public static void render(Canvas graphics, Paint paint){
        ArrayList<Pulse> temp = new ArrayList<>(pulses);
        for (Pulse pulse : temp){
            pulse.render(graphics, paint);
        }
    }
    public static void tick(){
        ArrayList<Pulse> temp = new ArrayList<>(pulses);
        for (Pulse pulse : temp){
            pulse.tick();
        }
    }
}
