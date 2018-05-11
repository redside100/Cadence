package me.andrewpeng.cadence.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {

    private static AssetManager assets;

    public Reader(Context context){
        assets = context.getAssets();
    }

    /**
     * Opens the music file that will correspond to the beatmap
     * @param url The location where the wav file is found
     * @return
     */
    public static AssetFileDescriptor getSoundFile(String url){
        try{
            return assets.openFd(url);
        }catch(IOException e){
            System.out.println("Error opening sound file: " + url);
        }
        return null;
    }

    /**
     * Will read the description of the song (ex. name.ini)
     * @param url The link where the file is found
     * @return The info about the song
     */
    public static ArrayList<String> getTextContents(String url){
        ArrayList<String> lines = new ArrayList<>();
        try{
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(assets.open(url)));
            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
            reader.close();
        }catch(IOException e){
            System.out.println("Error opening text file: " + url);
        }
        return lines;
    }

    /**
     * Will load the beatmap based on the colour of the pixels displayed in the png file
     * @param url Gets the bitmap based on the location
     * @return A new bitmap from the png file where the blanks and notes are assigned
     */
    public static int[][] getBeatConfiguration(String url){
            Bitmap bitmap;
            int beats[][] = null;
            try {
                bitmap = BitmapFactory.decodeStream(assets.open(url));
                if (bitmap != null){
                    beats = new int[bitmap.getHeight()][bitmap.getWidth()];
                    for (int i = 0; i < bitmap.getHeight(); i++){
                        for (int j = 0; j < bitmap.getWidth(); j++){
                            int pixel = bitmap.getPixel(j, i);
                            int r = Color.red(pixel);
                            int g = Color.green(pixel);
                            int b = Color.blue(pixel);
                            if (r == 255 && g == 0 && b == 0){
                                beats[i][j] = 1;
                            }else{
                                beats[i][j] = 0;
                            }
                        }
                    }
                }
            }catch(IOException e){
                System.out.println("Error opening beatmap file: " + url);
            }
            return beats;
    }

}
