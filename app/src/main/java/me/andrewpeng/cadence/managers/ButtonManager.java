package me.andrewpeng.cadence.managers;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.objects.Button;

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
    public static void touch(MotionEvent e){
        // Loop through all active buttons
        for (Button button : buttons){
            // Check if the current button is being touched
            if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(),
                    button.getBoundX1(), button.getBoundX2(), button.getBoundY1(), button.getBoundY2())){
                // Trigger
                button.trigger();
            }
        }
    }
}
