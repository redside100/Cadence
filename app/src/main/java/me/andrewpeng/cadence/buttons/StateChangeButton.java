package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.core.ScreenState;
import me.andrewpeng.cadence.objects.Button;


public class StateChangeButton extends Button {
    public ScreenState newState;

    /**
     * Creates a button that will change the state of the screen
     * @param bitmap The image that will get the screen change property
     * @param x x coordinate
     * @param y y coordinate
     * @param alpha Transparency of the image
     * @param newState The screen state you want the object to assume when touched
     */
    public StateChangeButton(Bitmap bitmap, int x, int y, int alpha, ScreenState newState){
        super(bitmap, x, y, alpha);
        this.newState = newState;
    }
    @Override
    public void trigger(){
        Renderer.changeState(newState);
    }
}
