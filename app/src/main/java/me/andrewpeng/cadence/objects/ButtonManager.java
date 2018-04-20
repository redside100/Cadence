package me.andrewpeng.cadence.objects;


import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class ButtonManager {

    public static ArrayList<Button> buttons = new ArrayList<>();
    public static void render(Canvas graphics, Paint paint){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            button.render(graphics, paint);
        }
    }
    public static void tick(){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            button.tick();
        }
    }
}
