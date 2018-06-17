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
    /**
     * Creates a button that will resume the game
     * @param bitmap The image of the button
     * @param x x coordinate
     * @param y y coordinate
     * @param alpha Transparency of the image
     */
    public PlayButton(Bitmap bitmap, int x, int y, int alpha) {
        super(bitmap,x,y,alpha);
    }

    public void trigger() {

        super.trigger();

        // Resume the conductor
        Conductor.resume();
        // Get all active buttons, images, and texts
        ArrayList<Button> activeButtons = new ArrayList<>(ButtonManager.buttons);
        ArrayList<FadingImage> activeImages = new ArrayList<>(FadingImageManager.fadingImages);
        ArrayList<AnimatedText> activeTexts = new ArrayList<>(AnimatedTextManager.texts);

        // Remove PlayButtons, or StateChange buttons
        for (Button button : activeButtons){
            if (button instanceof PlayButton || button instanceof StateChangeButton){
                ButtonManager.buttons.remove(button);
            }
        }

        // Fade out fading images
        for (FadingImage image : activeImages){
            image.fadeOut(25);
        }
        // Remove floating texts
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