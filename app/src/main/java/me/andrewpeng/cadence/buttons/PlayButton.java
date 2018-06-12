package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;

import java.util.ArrayList;

import me.andrewpeng.cadence.managers.AnimatedTextManager;
import me.andrewpeng.cadence.managers.ButtonManager;
import me.andrewpeng.cadence.managers.FadingImageManager;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.AnimatedText;
import me.andrewpeng.cadence.objects.Button;
import me.andrewpeng.cadence.objects.FadingImage;
import me.andrewpeng.cadence.objects.FloatingText;

/**
 * Created by isaacleung on 2018-05-28.
 */

public class PlayButton extends Button{

    public PlayButton(Bitmap bitmap, int x, int y, int alpha) {
        super(bitmap,x,y,alpha);
    }

    public void trigger() {
        super.trigger();

        Conductor.resume();
        ArrayList<Button> activeButtons = new ArrayList<>(ButtonManager.buttons);
        ArrayList<FadingImage> activeImages = new ArrayList<>(FadingImageManager.fadingImages);
        ArrayList<AnimatedText> activeTexts = new ArrayList<>(AnimatedTextManager.texts);
        for (Button button : activeButtons){
            if (button instanceof PlayButton || button instanceof StateChangeButton){
                ButtonManager.buttons.remove(button);
            }
        }
        for (FadingImage image : activeImages){
            image.fadeOut(25);
        }
        for (AnimatedText text : activeTexts){
            if (text instanceof FloatingText){
                AnimatedTextManager.texts.remove(text);
            }
        }

    }

    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
    }
}