package me.andrewpeng.cadence.managers;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.MainView;
import me.andrewpeng.cadence.objects.Button;

public class ButtonManager {

    public static ArrayList<Button> buttons = new ArrayList<>();
    public static Button preTouchButton = null;

    /**
     * Render the button onto the canvas where you can see the button
     * @param graphics Canvas
     * @param paint Paint
     */
    public static void render(Canvas graphics, Paint paint){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            button.render(graphics, paint);
        }
    }

    /**
     * Uses the tick function of the button
     */
    public static void tick(){
        ArrayList<Button> temp = new ArrayList<>(buttons);
        for (Button button : temp){
            button.tick();
        }
    }

    /**
     * Determines whether the button should assume its proper function based on bounds
     * @param e Motion event triggered by touch
     */
    public static void touch(MotionEvent e){
        // Loop through all active buttons
        ArrayList<Button> activeButtons = new ArrayList<>(buttons);
        for (Button button : activeButtons){
            // Check if the current button is being touched
            if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(),
                    button.getBoundX1(), button.getBoundX2(), button.getBoundY1(), button.getBoundY2())){
                // Trigger, if the button previously touched is the same one that the finger is lifted off of
                if (button.equals(preTouchButton)){
                    button.trigger();
                }
            }

            // Reset alpha in case button was touched beforehand
            button.setAlpha(255);
        }
        preTouchButton = null;
    }

    public static void preTouch(MotionEvent e){
        // Loop through all active buttons
        ArrayList<Button> activeButtons = new ArrayList<>(buttons);
        for (Button button : activeButtons){
            // Check if the current button is being touched (pre touched)
            if (MainView.inBounds((int) e.getX(), (int) e.getX(), (int) e.getY(), (int) e.getY(),
                    button.getBoundX1(), button.getBoundX2(), button.getBoundY1(), button.getBoundY2())){
                // This isn't when we want to trigger the button, we just want some visual feedback of the button being pressed
                // Set the alpha slightly lower to allow transparency
                button.setAlpha(200);
                preTouchButton = button;
            }
        }
    }
}
