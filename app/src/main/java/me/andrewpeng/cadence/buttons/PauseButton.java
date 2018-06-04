package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import java.util.ArrayList;

import me.andrewpeng.cadence.objects.Button;

/**
 * Created by isaacleung on 2018-05-28.
 */

public class PauseButton extends Button{

    public static boolean paused = false;

    public PauseButton(Bitmap bitmap, int x, int y, int alpha) {
        super(bitmap,x,y,alpha);
    }

    public void trigger() {
        paused = true;
    }
}
