package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import me.andrewpeng.cadence.objects.Button;

/**
 * Created by isaacleung on 2018-05-28.
 */

public class PlayButton extends Button{

    public static boolean paused;

    public PlayButton(Bitmap bitmap, int x, int y, int alpha, boolean paused) {
        super(bitmap,x,y,alpha);
        this.paused = paused;
    }

    public void trigger() {
        paused = false;
    }

    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }
}