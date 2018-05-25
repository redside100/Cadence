
package me.andrewpeng.cadence.objects;

import android.os.Handler;
import me.andrewpeng.cadence.core.GameValues;

/**
 * Tracks the score that the user has in the song
 * Created by isaacleung on 2018-05-04.
 */

public class Score {
    public static int score = 0;
    public static int addedScore;

    public static int perfcount = 0;
    public static int greatcount = 0;
    public static int goodcount = 0;
    public static int misscount = 0;

    public Runnable runnable;
    public Handler handler;

    public static boolean run = false;

    public static boolean setScore() {
        //TODO This is only default, implement more than one score finish pls
        run = true;
        return run;
    }

    public static int getScore() {
        return score;
    }

    public static void addScore(int index) {
        switch (index) {
            case 0: addedScore = GameValues.getMiss();
            case 1: addedScore = GameValues.getGood();
            case 2: addedScore = GameValues.getGreat();
            case 3: addedScore = GameValues.getPerfect();

        }
    }

    public static int getAddedScore() {
        return addedScore;
    }

    public void tick() {
        //TODO tick method to tick the addedscore to score
        handler = new Handler();
        if(run) {
            for (int i = 0; i < addedScore; i++) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        score++;
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        }
        run = false;
    }

}
