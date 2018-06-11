package me.andrewpeng.cadence.buttons;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.ArrayList;

import me.andrewpeng.cadence.core.ScreenState;
import me.andrewpeng.cadence.music.Conductor;
import me.andrewpeng.cadence.objects.Button;
import me.andrewpeng.cadence.objects.FadingImage;
import me.andrewpeng.cadence.objects.FloatingText;
import me.andrewpeng.cadence.util.AssetLoader;
import me.andrewpeng.cadence.util.ImageAsset;

import static me.andrewpeng.cadence.core.Renderer.height;
import static me.andrewpeng.cadence.core.Renderer.width;

/**
 * Created by isaacleung on 2018-05-28.
 */

public class PauseButton extends Button{

    public PauseButton(Bitmap bitmap, int x, int y, int alpha) {
        super(bitmap,x,y,alpha);
    }

    public void trigger() {

        // Set pause state
        if (!Conductor.paused){

            Conductor.pause();

            // Create tint background, and buttons
            new FadingImage(AssetLoader.getImageAssetFromMemory(ImageAsset.BLACK_TINT), width / 2, height / 2, 0).fadeIn(25);
            new PlayButton(AssetLoader.getImageAssetFromMemory(ImageAsset.RESUME_BUTTON),width / 2,height / 2, 255);
            new StateChangeButton(AssetLoader.getImageAssetFromMemory(ImageAsset.EXIT_BUTTON),width / 2,(int) (height * 0.65), 255, ScreenState.MENU);

            // Floating title
            new FloatingText("Paused", width / 2, (int) (height * 0.3), 25, Color.WHITE, 240, (int) (height * 0.01), 255);
        }
    }
}