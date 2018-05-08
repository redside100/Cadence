package me.andrewpeng.cadence.util;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.HashMap;

public class AssetLoader {
    private static AssetManager assets;
    private static HashMap<ImageAsset, Bitmap> images = new HashMap<>();
    private int height, width;
    private double originalWidth = 1080;
    private double originalHeight = 1920;
    public AssetLoader(Context context, int width, int height){
        assets = context.getAssets();
        this.height = height;
        this.width = width;
        load();
    }

    private void load(){
        // Backgrounds
        images.put(ImageAsset.HOME_BACKGROUND, getImageAsset("backgrounds/main_background.png"));
        images.put(ImageAsset.SPINNER_BORDER, getImageAsset("backgrounds/spinnerBorder.png"));
        images.put(ImageAsset.BLACK_BACKDROP, getImageAsset("backgrounds/blackBackdrop.png"));

        // Buttons
        images.put(ImageAsset.TEST_BUTTON, getImageAsset("buttons/testButton.png"));
        images.put(ImageAsset.SONG_SELECTION_BUTTON, getImageAsset("buttons/songSelection.png"));
        images.put(ImageAsset.CREDITS_BUTTON, getImageAsset("buttons/credits.png"));
        images.put(ImageAsset.SETTINGS_BUTTON, getImageAsset("buttons/settings.png"));
        images.put(ImageAsset.OK_BUTTON, getImageAsset("buttons/ok.png"));
        images.put(ImageAsset.LEFT_ARROW_BUTTON, getImageAsset("buttons/leftArrow.png"));
        images.put(ImageAsset.RIGHT_ARROW_BUTTON, getImageAsset("buttons/rightArrow.png"));

        // Icons
        images.put(ImageAsset.MUSIC_NOTE_ICON, getImageAsset("icons/musicNote.png"));

        //Particle
        images.put(ImageAsset.PARTICLE,getImageAsset("particles/star_pink.png"));

        //Gradient
        images.put(ImageAsset.GRADIENT, getImageAsset("particles/Gradient.png"));


    }

    private Bitmap getImageAsset(String url){
        Bitmap bitmap;
        int beats[][] = null;
        try {
            bitmap = BitmapFactory.decodeStream(assets.open(url));
            if (bitmap != null){
                double x = bitmap.getWidth() * (double) width / originalWidth;
                double y = bitmap.getHeight() * (double) height / originalHeight;
                return Bitmap.createScaledBitmap(bitmap, (int) x, (int) y, true);
            }
        }catch(IOException e){
            System.out.println("Error opening bitmap: " + url);
        }
        return null;
    }

    public static Bitmap getImageAssetFromMemory(ImageAsset imageAsset){
        return images.containsKey(imageAsset) ? images.get(imageAsset) : null;
    }


}
