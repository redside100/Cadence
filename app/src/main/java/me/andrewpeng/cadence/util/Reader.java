package me.andrewpeng.cadence.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Reader {

    private static AssetManager assets;
    private static Context context;

    public Reader(Context context){
        assets = context.getAssets();
        this.context = context;
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
     * Saves information into a file.
     * @param info The list of info to be stored
     * @param fileName The name of the file to be written to
     */
    public static void save(ArrayList<String> info, final String fileName){
        try{
            // Create temp file
            File temp = new File(context.getFilesDir(), "temp.ini");
            if (!temp.exists()){
                temp.createNewFile();
            }else{
                System.out.println("Error saving! Temp file already exists... weird.");
                return;
            }

            // Open output stream, writer, and buffered writer
            FileOutputStream outputStream = context.openFileOutput("temp.ini", Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);

            for (String line : info){
                bufferedWriter.write(line + "\n");
            }

            // Close writers and streams
            bufferedWriter.close();
            outputWriter.close();
            outputStream.close();

            // Delete old file, then rename the temp file to the new file
            File save = new File(context.getFilesDir(), fileName);
            if (save.exists()){
                save.delete();
                temp.renameTo(save);
                System.out.println("File saved, named " + fileName);
            }else{
                System.out.println("Error saving, file doesn't exist!");
                return;
            }

        }catch(IOException e){}
    }

    /**
     * Loads a save file into game values.
     * @param fileName The file name of the save file
     * @return
     */
    public static ArrayList<String> getSave(String fileName){
        try {

            FileInputStream inputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            ArrayList<String> info = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                info.add(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            return info;
        } catch (Exception e){
            return null;
        }
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
