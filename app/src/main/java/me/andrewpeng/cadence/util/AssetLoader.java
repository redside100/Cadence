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
        this.assets = context.getAssets();
        this.height = height;
        this.width = width;
        load();
    }

    private void load(){
        images.put(ImageAsset.HOME_BACKGROUND, getImageAsset("backgrounds/main_background.png"));
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
