package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import me.andrewpeng.cadence.core.PlayerData;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.Button;


public class JudgeDifficultyButton extends Button {
    /**
     * Creates a button that will manage the judge difficulty
     * @param bitmap The image that will have the properties to change the difficulty
     * @param x x location
     * @param y y location
     * @param alpha Transparency of the image
     */
    public JudgeDifficultyButton(Bitmap bitmap, int x, int y, int alpha){
        super(bitmap, x, y, alpha);
    }
    @Override
    public void trigger(){
        super.trigger();
        switch(Conductor.judgeDifficulty){
            case "Easy":
                Conductor.judgeDifficulty = "Normal";
                break;
            case "Normal":
                Conductor.judgeDifficulty = "Hard";
                break;
            case "Hard":
                Conductor.judgeDifficulty = "Easy";
                break;
        }
        PlayerData.saveAll();

    }
}
