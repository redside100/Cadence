package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import java.util.ArrayList;

import me.andrewpeng.cadence.objects.Button;

/**
 * Created by isaacleung on 2018-05-28.
 */

public class PauseButton extends Button{

    public static boolean paused;

    public PauseButton(Bitmap bitmap, int x, int y, int alpha, boolean paused) {
        super(bitmap,x,y,alpha);
        this.paused = paused;
    }

    public void trigger() {
        paused = true;
    }
}