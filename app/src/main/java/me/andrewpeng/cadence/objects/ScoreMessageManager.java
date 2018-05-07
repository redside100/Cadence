package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by isaacleung on 2018-05-07.
 */

public class ScoreMessageManager {
    public static ArrayList<ScoreMessage> scoreMessages = new ArrayList<>();
    public static void render(Canvas graphics, Paint paint) {
        ArrayList<ScoreMessage> temp = new ArrayList<>(scoreMessages);
        for(ScoreMessage scoreMessage: temp) {
            scoreMessage.tick();
        }
    }

    public static void tick() {

    }

    public static void touch(MotionEvent e) {
        try {

        }catch (ArrayIndexOutOfBoundsException e1) {

        }
    }

}
