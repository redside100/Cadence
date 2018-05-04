package me.andrewpeng.cadence.objects;

import me.andrewpeng.cadence.core.GameValues;
import me.andrewpeng.cadence.music.Conductor;

/**
 * Created by isaacleung on 2018-05-04.
 */

public class Score {
    public static int score = 0;
    public static int addedScore;

    public static int perfcount = 0;
    public static int greatcount = 0;
    public static int goodcount = 0;
    public static int misscount = 0;

    public static void setScore(int newScore) {
        //TODO This is only default, implement more than one score finish pls
        
        int delay = 30;
        int currentdelay = 0;
        for(int i = 0;i < getAddedScore();i++) {
            if(currentdelay == delay) {
                int addScore = 1;
                score = score + addScore;
                currentdelay = 0;
            }
            currentdelay++;

        }

        perfcount++;

    }
    public static int getScore() {
        return score;
    }

    public static int addScore(int pad) {
        //TODO Judge scoring based on the padding
        switch (pad) {
            case 1: addedScore = GameValues.getPerfect();

        }
        return addedScore;
    }

    public static int getAddedScore() {
        return addedScore;
    }

    public void tick() {

    }

}
