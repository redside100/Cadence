package me.andrewpeng.cadence.util;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.HashMap;

import me.andrewpeng.cadence.music.FX;

public class AssetLoader {
    private static AssetManager assets;
    private static HashMap<ImageAsset, Bitmap> images = new HashMap<>();
    private static HashMap<FX.SoundEffect, AssetFileDescriptor> sounds = new HashMap<>();
    private static int height, width;
    private static double originalWidth = 1080;
    private static double originalHeight = 1920;
    public AssetLoader(Context context, int width, int height){
        assets = context.getAssets();
        this.height = height;
        this.width = width;
        load();
    }

    /**
     * Loads the images so it can be accessed whenever we need to render
     */
    private void load() {
        // Backgrounds
        images.put(ImageAsset.HOME_BACKGROUND, getImageAsset("backgrounds/main_background.png"));
        images.put(ImageAsset.PLAY_BACKGROUND, getImageAsset("backgrounds/play_background.png"));
        images.put(ImageAsset.SPINNER_BORDER, getImageAsset("backgrounds/spinnerBorder.png"));
        images.put(ImageAsset.BLACK_BACKDROP, getImageAsset("backgrounds/blackBackdrop.png"));
        images.put(ImageAsset.BLACK_TINT, getImageAsset("backgrounds/blackTint.png"));

        // Buttons
        images.put(ImageAsset.TEST_BUTTON, getImageAsset("buttons/testButton.png"));
        images.put(ImageAsset.SONG_SELECTION_BUTTON, getImageAsset("buttons/songSelection.png"));
        images.put(ImageAsset.CREDITS_BUTTON, getImageAsset("buttons/credits.png"));
        images.put(ImageAsset.SETTINGS_BUTTON, getImageAsset("buttons/settings.png"));
        images.put(ImageAsset.OK_BUTTON, getImageAsset("buttons/ok.png"));
        images.put(ImageAsset.LEFT_ARROW_BUTTON, getImageAsset("buttons/leftArrow.png"));
        images.put(ImageAsset.RIGHT_ARROW_BUTTON, getImageAsset("buttons/rightArrow.png"));
        images.put(ImageAsset.PAUSE_BUTTON, getImageAsset("buttons/pause.png"));
        images.put(ImageAsset.RESUME_BUTTON, getImageAsset("buttons/resume.png"));
        images.put(ImageAsset.EXIT_BUTTON, getImageAsset("buttons/exit.png"));
        images.put(ImageAsset.BLUE_BUTTON, getImageAsset("buttons/blueButton.png"));
        images.put(ImageAsset.ERASE_BUTTON, getImageAsset("buttons/eraseButton.png"));
        images.put(ImageAsset.SELECT_BUTTON, getImageAsset("buttons/select.png"));
        images.put(ImageAsset.BACK_BUTTON, getImageAsset("buttons/back.png"));

        // Icons
        images.put(ImageAsset.MUSIC_NOTE_ICON, getImageAsset("icons/musicNote.png"));

        //Particle
        images.put(ImageAsset.PARTICLE, getImageAsset("particles/star_pink.png"));

        //Gradient
        images.put(ImageAsset.GRADIENT, getImageAsset("particles/Gradient.png"));

        //Score Messages
        images.put(ImageAsset.SCORE0, getImageAsset("scores/hit0.png"));
        images.put(ImageAsset.SCORE100, getImageAsset("scores/hit100.png"));
        images.put(ImageAsset.SCORE200, getImageAsset("scores/hit200.png"));
        images.put(ImageAsset.SCORE300, getImageAsset("scores/hit300.png"));

        //Grades
        images.put(ImageAsset.RANKINGA,getImageAsset("grades/rankingA.png"));
        images.put(ImageAsset.RANKINGB,getImageAsset("grades/rankingB.png"));
        images.put(ImageAsset.RANKINGC,getImageAsset("grades/rankingC.png"));
        images.put(ImageAsset.RANKINGD,getImageAsset("grades/rankingD.png"));
        images.put(ImageAsset.RANKINGS,getImageAsset("grades/rankingS.png"));

        //Note skins
        images.put(ImageAsset.NOTE1,getImageAsset("noteskins/note1.png"));

        //Sound Effects
        sounds.put(FX.SoundEffect.SELECT, getSound("fx/click.wav"));

    }

    /**
     * Creates the initial bitmap so that it can be stored into memory
     * @param url Location where the bitmap is found
     * @return
     */
    public static Bitmap getImageAsset(String url){
        Bitmap bitmap;
        int beats[][] = null;
        try {
            bitmap = BitmapFactory.decodeStream(assets.open(url));
            if (bitmap != null){
                double x = bitmap.getWidth() * (double) width / originalWidth;
                double y = bitmap.getHeight() * (double) height / originalHeight;
                return Bitmap.createScaledBitmap(bitmap, (int) Math.round(x), (int) Math.round(y), true);
            }
        }catch(IOException e){
            System.out.println("Error opening bitmap: " + url);
        }
        return null;
    }

    /**
     * Gets the bitmap that you want to use from memory (from the ImageAsset class)
     * @param imageAsset Image from the ImageAsset class
     * @return
     */
    public static Bitmap getImageAssetFromMemory(ImageAsset imageAsset){
        return images.containsKey(imageAsset) ? images.get(imageAsset) : null;
    }

    /**
     * Gets a sound file.
     * @param filePath The path of the sound file
     * @return
     */
    public static AssetFileDescriptor getSound(String filePath){
        try{
            AssetFileDescriptor afd = assets.openFd(filePath);
            return afd;
        }catch(IOException e){
            return null;
        }
    }

    /**
     * Gets the sound that you want to use form memory
     * @param soundEffect Sound from the SoundEffect enum
     * @return
     */
    public static AssetFileDescriptor getSoundAssetFromMemory(FX.SoundEffect soundEffect){
        return sounds.containsKey(soundEffect) ? sounds.get(soundEffect) : null;
    }

}
