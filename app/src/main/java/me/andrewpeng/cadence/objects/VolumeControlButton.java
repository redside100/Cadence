package me.andrewpeng.cadence.objects;

import android.graphics.Bitmap;

import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.core.ScreenState;
import me.andrewpeng.cadence.music.Conductor;


public class VolumeControlButton extends Button {
    public boolean increase, fx;
    public VolumeControlButton(Bitmap bitmap, int x, int y, int alpha, boolean increase, boolean fx){
        super(bitmap, x, y, alpha);
        this.increase = increase;
        this.fx = fx;
    }
    @Override
    public void trigger(){
        if (increase){
            if (fx){
                Conductor.setFxVolume(Conductor.getFxVolume() + 10);
            }else{
                Conductor.setVolume(Conductor.getVolume() + 10);
            }
        }else{
            if (fx){
                Conductor.setFxVolume(Conductor.getFxVolume() - 10);
            }else{
                Conductor.setVolume(Conductor.getVolume() - 10);
            }
        }
    }
}
