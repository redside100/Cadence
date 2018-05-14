package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import me.andrewpeng.cadence.core.Renderer;

/**
 * Keeps track of the current combo number that the player is currently at
 * Created by isaacleung on 2018-05-09.
 */

public class Combo {
    public static int combo;
    public int x,y;
    public int alpha;
    public int textSize;
    public Combo(int combo, int x, int y, int textSize, int alpha) {
        this.combo = combo;
        this.x = x;
        this.y = y;
        this.textSize = textSize;
        this.alpha = alpha;
    }
    public void render(Canvas canvas, Paint paint) {
        Renderer.writeText(combo + "",canvas,x,y,paint,textSize, Color.WHITE);
    }
    public void tick() {

    }

    public static void addCombo() {
        combo++;
    }

 }
