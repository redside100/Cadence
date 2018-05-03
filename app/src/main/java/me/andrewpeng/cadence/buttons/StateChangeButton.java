package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.core.ScreenState;
import me.andrewpeng.cadence.objects.Button;


public class StateChangeButton extends Button {
    public ScreenState newState;
    public StateChangeButton(Bitmap bitmap, int x, int y, int alpha, ScreenState newState){
        super(bitmap, x, y, alpha);
        this.newState = newState;
    }
    @Override
    public void trigger(){
        Renderer.changeState(newState);
    }
}
