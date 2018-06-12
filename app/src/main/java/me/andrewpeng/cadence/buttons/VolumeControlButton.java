package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import me.andrewpeng.cadence.core.PlayerData;
import me.andrewpeng.cadence.core.Renderer;
import me.andrewpeng.cadence.core.ScreenState;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.Button;


public class VolumeControlButton extends Button {
    public boolean increase, fx;

    /**
     * Creates a button that will manage the volume (fX and Music)
     * @param bitmap The image that will have the properties to change the sound
     * @param x x location
     * @param y y location
     * @param alpha Transparency of the image
     * @param increase Whether it is possible to increase the volume or has it reached its max
     * @param fx Whether it is an fx button or a music volume button
     */
    public VolumeControlButton(Bitmap bitmap, int x, int y, int alpha, boolean increase, boolean fx){
        super(bitmap, x, y, alpha);
        this.increase = increase;
        this.fx = fx;
    }
    @Override
    public void trigger(){
        super.trigger();
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
        PlayerData.saveAll();

    }
}
